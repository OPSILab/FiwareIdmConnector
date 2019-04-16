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

import org.apache.http.client.utils.URIBuilder;

import it.eng.opsi.authentication.fiware.model.Token;
import it.eng.opsi.authentication.fiware.model.UserInfo;

public abstract class FiwareIDMConnector {

	protected String clientId;
	protected String clientSecret;
	protected String redirectUri;
	protected URI baseUrl;

	protected static final String path_token = "/oauth2/token";
	protected static final String path_user = "/user";

	public FiwareIDMConnector(String protocol, String host, int port, String clientId, String clientSecret,
			String redirectUri) throws URISyntaxException {
		super();
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectUri = redirectUri;
		boolean needsPort = !(("http".equalsIgnoreCase(protocol) && port == 80)
				|| ("https".equalsIgnoreCase(protocol) && port == 443));

		URIBuilder builder = new URIBuilder().setScheme(protocol).setHost(host);

		if (needsPort)
			builder.setPort(port);

		this.baseUrl = builder.build();
	}

	/**
	 * Get the Token associated to the input Authorization Code (OAuth2 Code grant)
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public abstract Token getToken(String code) throws Exception;

	/**
	 * Get the User Info associated to the input token
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public abstract UserInfo getUserInfo(String token) throws Exception;

}
