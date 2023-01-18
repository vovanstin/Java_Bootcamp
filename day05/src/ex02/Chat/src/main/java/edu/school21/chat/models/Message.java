package edu.school21.chat.models;

import java.sql.Timestamp;
import java.util.Objects;

public class Message {
	private Long id;
	private User author;
	private Chatroom chatroom;
	private String text;
	private Timestamp dateTime;

	public Message(Long id, User author, Chatroom chatroom, String text, Timestamp dateTime) {
		this.id = id;
		this.author = author;
		this.chatroom = chatroom;
		this.text = text;
		this.dateTime = dateTime;
	}

	@Override
	public String toString() {
		return "Message{" +
				"id=" + id +
				", author=" + author +
				", chatroom=" + chatroom +
				", text='" + text + '\'' +
				", dateTime=" + dateTime +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, author, chatroom, text, dateTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Message message = (Message) obj;
		return id == message.id && author.equals(message.author) && chatroom.equals(message.chatroom)
						&& text.equals(message.text) && dateTime.equals(message.dateTime);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public Chatroom getChatroom() {
		return chatroom;
	}

	public String getText() {
		return text;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}
}
