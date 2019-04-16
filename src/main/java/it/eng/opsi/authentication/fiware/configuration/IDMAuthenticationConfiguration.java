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
package it.eng.opsi.authentication.fiware.configuration;

import it.eng.opsi.authentication.fiware.model.FiwareIDMVersion;

public class IDMAuthenticationConfiguration {

	private FiwareIDMVersion version;
	private String protocol;

	private String host;
	private int port;
	private String clientId;
	private String clientSecret;
	private String redirectUri;
	private String logoutCallback;
	private String keystoneProtocol;
	private String keystoneHost;
	private int keystonePort;

	public IDMAuthenticationConfiguration(FiwareIDMVersion version, String protocol, String host, int port,
			String clientId, String clientSecret, String redirectUri) {
		super();
		this.version = version;
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectUri = redirectUri;
	}

	public IDMAuthenticationConfiguration(FiwareIDMVersion version, String protocol, String host, int port,
			String clientId, String clientSecret, String redirectUri, String keystoneProtocol, String keystoneHost,
			int keystonePort) {
		super();
		this.version = version;
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectUri = redirectUri;
		this.keystoneProtocol = keystoneProtocol;
		this.keystoneHost = keystoneHost;
		this.keystonePort = keystonePort;
	}

	public FiwareIDMVersion getVersion() {
		return version;
	}

	public void setVersion(FiwareIDMVersion version) {
		this.version = version;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getLogoutCallback() {
		return logoutCallback;
	}

	public void setLogoutCallback(String logoutCallback) {
		this.logoutCallback = logoutCallback;
	}
	
	public String getKeystoneProtocol() {
		return keystoneProtocol;
	}

	public void setKeystoneProtocol(String keystoneProtocol) {
		this.keystoneProtocol = keystoneProtocol;
	}

	public String getKeystoneHost() {
		return keystoneHost;
	}

	public void setKeystoneHost(String keystoneHost) {
		this.keystoneHost = keystoneHost;
	}

	public int getKeystonePort() {
		return keystonePort;
	}

	public void setKeystonePort(int keystonePort) {
		this.keystonePort = keystonePort;
	}

	@Override
	public String toString() {
		return "IDMAuthenticationConfiguration [version=" + version + ", protocol=" + protocol + ", host=" + host
				+ ", port=" + port + ", clientId=" + clientId + ", clientSecret=" + clientSecret + ", redirectUri="
				+ redirectUri + ", keystoneProtocol=" + keystoneProtocol + ", keystoneHost=" + keystoneHost
				+ ", keystonePort=" + keystonePort + "]";
	}

}
