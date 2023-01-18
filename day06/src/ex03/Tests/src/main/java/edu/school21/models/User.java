package edu.school21.models;

public class User {
	private Long id;
	private String login;
	private String password;
	private boolean authStatus;

	public User(Long id, String login, String password, boolean authStatus) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.authStatus = authStatus;
	}

	public boolean getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(boolean authStatus) {
		this.authStatus = authStatus;
	}

	public String getPassword() {
		return password;
	}
}
