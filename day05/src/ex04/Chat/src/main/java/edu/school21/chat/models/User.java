package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
	private Long id;
	private String login;
	private String password;
	private List<Chatroom> listChatrooms;
	private List<Chatroom> socializesChatrooms;

	public User(Long id, String login, String password, List<Chatroom> listChatrooms, List<Chatroom> socializesChatrooms) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.listChatrooms = listChatrooms;
		this.socializesChatrooms = socializesChatrooms;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", login='" + login + '\'' +
				", password='" + password + '\'' +
				", listChatrooms=" + listChatrooms +
				", socializesChatrooms=" + socializesChatrooms +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, login, password, listChatrooms, socializesChatrooms);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		User user = (User) obj;
		return id == user.id && login.equals(user.login) && password.equals(user.password)
						&& listChatrooms.equals(user.listChatrooms) && socializesChatrooms.equals(user.socializesChatrooms);
	}
	
	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassowrd() {
		return password;
	}

	public List<Chatroom> getListChatrooms() {
		return listChatrooms;
	}

	public List<Chatroom> getSocializesChatrooms() {
		return socializesChatrooms;
	}
}
