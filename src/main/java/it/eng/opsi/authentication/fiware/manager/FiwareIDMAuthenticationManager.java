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
package it.eng.opsi.authentication.fiware.manager;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import it.eng.opsi.authentication.fiware.configuration.IDMAuthenticationConfiguration;
import it.eng.opsi.authentication.fiware.connectors.FiwareIDMConnector;
import it.eng.opsi.authentication.fiware.connectors.Keyrock6Connector;
import it.eng.opsi.authentication.fiware.connectors.Keyrock7Connector;
import it.eng.opsi.authentication.fiware.model.Token;
import it.eng.opsi.authentication.fiware.model.UserInfo;

public class FiwareIDMAuthenticationManager implements IAuthenticationManager {

	private static FiwareIDMAuthenticationManager instance;
	private static FiwareIDMConnector connector;
	private String logoutCallback;

	private FiwareIDMAuthenticationManager() {
	}

	private FiwareIDMAuthenticationManager(IDMAuthenticationConfiguration configuration) throws Exception {

		logoutCallback = configuration.getLogoutCallback();

		switch (configuration.getVersion()) {

		case FIWARE_IDM_VERSION_6:
			connector = new Keyrock6Connector(configuration.getKeystoneProtocol(), configuration.getKeystoneHost(), -1,
					configuration.getProtocol(), configuration.getHost(), -1, configuration.getClientId(),
					configuration.getClientSecret(), configuration.getRedirectUri());
			break;
		case FIWARE_IDM_VERSION_7:
			connector = new Keyrock7Connector(configuration.getProtocol(), configuration.getHost(), -1,
					configuration.getClientId(), configuration.getClientSecret(), configuration.getRedirectUri());
			break;
		default:
			throw new Exception("Fiware IdM Version is invalid");
		}

	}

	public static FiwareIDMAuthenticationManager getInstance(IDMAuthenticationConfiguration configuration) {

		if (instance == null) {
			try {
				instance = new FiwareIDMAuthenticationManager(configuration);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	public Object login(String username, String password, String code) throws Exception {
		return getToken(null, code);
	}

	public Response logout(HttpServletRequest request) throws Exception {

		System.out.println("Logging out...");

		HttpSession session = request.getSession();
		session.removeAttribute("loggedin");
		session.removeAttribute("refresh_token");
		session.removeAttribute("username");
		session.invalidate();

		return Response.temporaryRedirect(URI.create(logoutCallback)).build();

	}

	@Override
	public Token getToken(String username, String code) throws Exception {
		return connector.getToken(code);
	}

	@Override
	public Boolean validateToken(Object tokenObj) throws Exception {
		Token token = (Token) tokenObj;

		try {
			connector.getUserInfo(token.getAccess_token());
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public UserInfo getUserInfo(String token) throws Exception {
		return connector.getUserInfo(token);
	}

}
