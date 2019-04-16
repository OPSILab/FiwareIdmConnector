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

public enum IDMProperty {
	IDM_VERSION("idm.fiware.version"),
	IDM_PROTOCOL("idm.protocol"),
	IDM_HOST("idm.host"),
	IDM_ADMIN_ROLE_NAME("idm.admin.role.name"),
	//	IDM_PROTOCOL_DEFAULT("idm.protocol.default"),
//	IDM_PORT_DEFAULT("idm.port.default"),
	IDM_CLIENT_ID("idm.client.id"),
	IDM_CLIENT_SECRET("idm.client.secret"),
	IDM_REDIRECT_URI("idm.redirecturi"),
	IDM_LOGOUT_CALLBACK("idm.logout.callback"),
	IDM_PATH_BASE("idm.path.base"),
	IDM_PATH_TOKEN("idm.path.token"),
	IDM_PATH_USER("idm.path.user"),
	IDM_FIWARE_KEYSTONE_HOST("idm.fiware.keystone.host"),
	IDM_FIWARE_KEYSTONE_PORT("idm.fiware.keystone.port"),
	IDM_FIWARE_KEYSTONE_PATH_TOKENS("idm.fiware.keystone.path.tokens");
	

	private final String text;

	private IDMProperty(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
