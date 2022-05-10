package com.restaurant.dao.entity;

import java.io.Serializable;

/**
 * User entity
 * 
 * @author Serhii Yakovenko.
 * 
 */
public class User implements Serializable {

	private static final long serialVersionUID = 5630252521560149867L;

	private int id;
	private String login;
	private String role;
	private String locale;

	public User(int id, String login, String role, String locale) {
		this.id = id;
		this.login = login;
		this.role = role;
		this.locale = locale;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", role=" + role + ", locale=" + locale + "]";
	}

}
