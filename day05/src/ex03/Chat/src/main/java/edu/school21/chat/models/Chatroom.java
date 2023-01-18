package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class Chatroom {
	private Long id;
	private String name;
	private User owner;
	private List<Message> listMessages;

	public Chatroom(Long id, String name, User owner, List<Message> listMessages) {
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.listMessages = listMessages;
	}

	@Override
	public String toString() {
		return "Chatroom{" +
				"id=" + id +
				", name='" + name + '\'' +
				", owner=" + owner +
				", listMessages=" + listMessages +
				"}";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, owner, listMessages);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Chatroom chatroom = (Chatroom) obj;
		return id == chatroom.id && name.equals(chatroom.name) && owner.equals(chatroom.owner)
						&& listMessages.equals(chatroom.listMessages);
	}
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public User getOwner() {
		return owner;
	}

	public List<Message> getListMessages() {
		return listMessages;
	}
}
