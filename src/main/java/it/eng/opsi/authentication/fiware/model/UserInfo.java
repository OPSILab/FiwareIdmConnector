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
package it.eng.opsi.authentication.fiware.model;

import java.util.HashSet;
import java.util.Set;

public class UserInfo {

	private Set<Organization> organizations;
	private String displayName;
	private Set<Role> roles;
	private String app_id;
	private boolean isGravatarEnabled;
	private String email;
	private String id;
	private String authorization_decision;
	private String app_azf_domain;
	private String username;

	public UserInfo() {
		organizations = new HashSet<Organization>();
		displayName = new String();
		roles = new HashSet<Role>();
		app_id = new String();
		isGravatarEnabled = false;
		email = new String();
		id = new String();
		authorization_decision = new String();
		app_azf_domain = new String();
		username = new String();
	}

	public UserInfo(Set<Organization> organizations,String displayName, Set<Role> role, String app_id, boolean isGravatarEnabled, String email, String id ) {
		this.organizations = organizations;
		this.displayName = displayName;
		this.roles = role;
		this.app_id = app_id;
		this.isGravatarEnabled = isGravatarEnabled;
		this.email = email;
		this.id = id;
	}
	
	public Set<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(Set<Organization> organizations) {
		this.organizations = organizations;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public boolean isGravatarEnabled() {
		return isGravatarEnabled;
	}

	public void setGravatarEnabled(boolean isGravatarEnabled) {
		this.isGravatarEnabled = isGravatarEnabled;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> role) {
		this.roles = role;
	}

	public String getAuthorization_decision() {
		return authorization_decision;
	}

	public void setAuthorization_decision(String authorization_decision) {
		this.authorization_decision = authorization_decision;
	}

	public String getApp_azf_domain() {
		return app_azf_domain;
	}

	public void setApp_azf_domain(String app_azf_domain) {
		this.app_azf_domain = app_azf_domain;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
