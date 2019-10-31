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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import com.google.gson.Gson;

import it.eng.opsi.authentication.fiware.model.GrantErrorMessage;
import it.eng.opsi.authentication.fiware.model.Token;
import it.eng.opsi.authentication.fiware.model.UserInfo;
import it.eng.opsi.restclient.RestClient;

/**
 * Integration with Fiware IDM version 7.0.0
 * 
 * 
 */
public class Keyrock7Connector extends FiwareIDMConnector {

	public Keyrock7Connector(String protocol, String host, int port, String clientId, String clientSecret,
			String redirectUri) throws URISyntaxException {
		super(protocol, host, port, clientId, clientSecret, redirectUri);
	}

	public Token getToken(String code) throws Exception {

		Optional<Token> token = Optional.empty();

		URI url = new URIBuilder(baseUrl).setPath(path_token).build();
		List<Header> headers = new ArrayList<Header>();
		String authHeaderValue = "Basic "
				+ new String(Base64.getEncoder().encode((clientId + ":" + clientSecret).getBytes()));
		headers.add(new BasicHeader(HttpHeaders.AUTHORIZATION, authHeaderValue));
		headers.add(new BasicHeader("Accept", "*/*"));
		List<NameValuePair> bodyParams = new ArrayList<NameValuePair>();
		bodyParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
		bodyParams.add(new BasicNameValuePair("code", code));
		bodyParams.add(new BasicNameValuePair("redirect_uri", redirectUri));

		RestClient client = new RestClient();
		HttpResponse response = client.sendPostRequest(url, headers,
				ContentType.APPLICATION_FORM_URLENCODED.withCharset("utf-8"), new UrlEncodedFormEntity(bodyParams));
		if (response != null) {
			String returned_json = client.getResponseBodyAsString(response);
			switch (client.getStatus(response)) {
			case 500:
				GrantErrorMessage errorBody = new Gson().fromJson(returned_json, GrantErrorMessage.class);
				throw new RuntimeException("[" + errorBody.getStatus() + "] " + errorBody.getMessage());

			default:
				token = Optional.ofNullable(new Gson().fromJson(returned_json, Token.class));
			}

			return token.get();
			
		} else {
			throw new RuntimeException("There was a connection error while contacting Keyrock");
		}
	}

	public UserInfo getUserInfo(String token) throws Exception {

		Optional<UserInfo> userinfo = Optional.empty();
		URI url = new URIBuilder(baseUrl).setPath(path_user).setParameter("access_token", token).build();

		RestClient client = new RestClient();
		HttpResponse response = client.sendGetRequest(url, new ArrayList<Header>());

		if (response != null) {
			String returned_json = client.getResponseBodyAsString(response);
			switch (client.getStatus(response)) {

			case 500:
				GrantErrorMessage errorBody = new Gson().fromJson(returned_json, GrantErrorMessage.class);
				throw new RuntimeException("[" + errorBody.getStatus() + "] " + errorBody.getMessage());

			default:
				userinfo = Optional.ofNullable(new Gson().fromJson(returned_json, UserInfo.class));
			}

			return userinfo.get();
		} else {
			throw new RuntimeException("There was a connection error while contacting Keyrock");
		}
	}

//	public static class Builder {
//
//		// Instance specific
//		private String protocol = PropertyManager.getProperty(IDMProperty.IDM_PROTOCOL_DEFAULT);
//		private String host;
//		private int port = Integer.parseInt(PropertyManager.getProperty(IDMProperty.IDM_PORT_DEFAULT));
//
//		public Builder setProtocol(String protocol) {
//			this.protocol = protocol;
//			return this;
//		}
//
//		public Builder setHost(String host) {
//			this.host = host;
//			return this;
//		}
//
//		public Builder setPort(int port) {
//			this.port = port;
//			return this;
//		}
//
//		// Application specific
//		private String clientid;
//		private String clientsecret;
//		private String redirecturi;
//
//		public Builder setClientid(String clientid) {
//			this.clientid = clientid;
//			return this;
//		}
//
//		public Builder setClientsecret(String clientsecret) {
//			this.clientsecret = clientsecret;
//			return this;
//		}
//
//		public Builder setRedirecturi(String redirecturi) {
//			this.redirecturi = redirecturi;
//			return this;
//		}
//
//		public Keyrock7Connector build() {
//			return new Keyrock7Connector(protocol, host, port, clientid, clientsecret, redirecturi);
//		}
//
//	}

}
