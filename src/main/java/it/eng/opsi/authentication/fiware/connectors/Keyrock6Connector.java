/*******************************************************************************
 * Fiware IdM Connector
 *   Copyright (C) 2019 Engineering Ingegneria Informatica S.p.A.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package it.eng.opsi.authentication.fiware.connectors;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;

import it.eng.opsi.authentication.fiware.configuration.IDMProperty;
import it.eng.opsi.authentication.fiware.model.Auth;
import it.eng.opsi.authentication.fiware.model.Domain;
import it.eng.opsi.authentication.fiware.model.Identity;
import it.eng.opsi.authentication.fiware.model.Password;
import it.eng.opsi.authentication.fiware.model.Token;
import it.eng.opsi.authentication.fiware.model.User;
import it.eng.opsi.authentication.fiware.model.UserInfo;
import it.eng.opsi.authentication.fiware.model.UserTokenBean;
import it.eng.opsi.restclient.RestClient;

/**
 * Integration with Fiware IDM version 6.0.0
 * 
 *
 */

public class Keyrock6Connector extends FiwareIDMConnector {

	private final URI keystone_baseurl;
	private static final String keystone_path_tokens = "/v3/auth/tokens";

	public Keyrock6Connector(String keystoneProtocol, String keystoneHost, int keystonePort, String protocol,
			String host, int port, String client_id, String client_secret, String redirectUri)
			throws URISyntaxException {
		super(protocol, host, port, client_id, client_secret, redirectUri);

		boolean needsPort = !(("http".equalsIgnoreCase(keystoneProtocol) && keystonePort == 80)
				|| ("https".equalsIgnoreCase(keystoneProtocol) && keystonePort == 443));
		URIBuilder builder = new URIBuilder().setScheme(keystoneProtocol).setHost(keystoneHost);
		if (needsPort)
			builder.setPort(keystonePort);
		this.keystone_baseurl = builder.build();
		
	}

	public Token getToken(String code) throws Exception {

		URI url = new URIBuilder(baseUrl).setPath(path_token).build();

		List<Header> headers = new ArrayList<Header>();
		String authHeaderValue = "Basic "
				+ new String(Base64.getEncoder().encode((clientId + ":" + clientSecret).getBytes()));
		headers.add(new BasicHeader(HttpHeaders.AUTHORIZATION, authHeaderValue));

		List<NameValuePair> bodyParams = new ArrayList<NameValuePair>();
		bodyParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
		bodyParams.add(new BasicNameValuePair("code", code));
		bodyParams.add(new BasicNameValuePair("redirect_uri", redirectUri));

		RestClient client = new RestClient();
		HttpResponse response = client.sendPostRequest(url, headers, ContentType.APPLICATION_FORM_URLENCODED,
				new UrlEncodedFormEntity(bodyParams));

		int status = client.getStatus(response);
		if (status != 200 && status != 201 && status != 301)
			throw new Exception(
					"Unable to retrieve token: " + status + ": " + response.getStatusLine().getReasonPhrase());

		String returned_json = client.getResponseBodyAsString(response);
		return new Gson().fromJson(returned_json, Token.class);

	}

	public Token getAdminToken(String adminuser, String adminpassword) throws Exception {

		User user = new User(adminuser, new Domain("default"), adminpassword);
		Password password = new Password(user);
		Set<String> methods = new HashSet<String>(1);
		methods.add("password");

		UserTokenBean userToken = new UserTokenBean(new Auth(new Identity(methods, password)));
		String userTokenJson = new Gson().toJson(userToken);

		URI url = new URIBuilder(keystone_baseurl).setPath(keystone_path_tokens).build();

		RestClient client = new RestClient();
		HttpResponse response = client.sendPostRequest(url, new ArrayList<Header>(), ContentType.APPLICATION_JSON,
				new StringEntity(userTokenJson));

		int status = client.getStatus(response);
		if (status != 200 && status != 201 && status != 301)
			throw new Exception("Unable to retrieve token: " + status);

		Header respHeaders = response.getFirstHeader("X-Subject-Token");
		String token = respHeaders.getValue();

		return new Token(token, null, null, null, null, null);
	}

	public Token refreshToken(String token, String refresh_token, String client_id, String client_secret)
			throws Exception {

		URI url = new URIBuilder(baseUrl).setPath(path_token).build();

		List<Header> headers = new ArrayList<Header>();
		String authHeaderValue = "Basic "
				+ new String(Base64.getEncoder().encode((clientId + ":" + clientSecret).getBytes()));
		headers.add(new BasicHeader(HttpHeaders.AUTHORIZATION, authHeaderValue));

		List<NameValuePair> bodyParams = new ArrayList<NameValuePair>();
		bodyParams.add(new BasicNameValuePair("grant_type", "refresh_token"));
		bodyParams.add(new BasicNameValuePair("client_id", client_id));
		bodyParams.add(new BasicNameValuePair("client_secret", client_secret));
		bodyParams.add(new BasicNameValuePair("refresh_token", refresh_token));

		RestClient client = new RestClient();
		HttpResponse response = client.sendPostRequest(url, headers, ContentType.APPLICATION_FORM_URLENCODED,
				new UrlEncodedFormEntity(bodyParams));

		int status = client.getStatus(response);
		if (status != 200 && status != 201 && status != 301)
			throw new Exception("Unable to refresh token: " + status);

		String returned_json = client.getResponseBodyAsString(response);

		return new Gson().fromJson(returned_json, Token.class);
	}

	public UserInfo getUserInfo(String token) throws Exception {

		URI url = new URIBuilder(baseUrl).setPath(path_user).setParameter("access_token", token).build();

		RestClient client = new RestClient();
		HttpResponse response = client.sendGetRequest(url, new ArrayList<Header>());

		int status = client.getStatus(response);
		if (status != 200 && status != 201 && status != 301)
			throw new Exception("Unable to get user info: " + status);

		String returned_json = client.getResponseBodyAsString(response);

		return new Gson().fromJson(returned_json, UserInfo.class);

	}

	public void logout(String token) {
		return;
	}

}
