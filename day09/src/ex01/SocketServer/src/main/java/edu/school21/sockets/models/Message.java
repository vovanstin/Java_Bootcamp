package edu.school21.sockets.models;

import java.sql.Timestamp;
import java.util.Objects;

public class Message {
	private Long id;
	private User author;
	private String text;
	private Timestamp dateTime;

	public Message(Long id, User author, String text, Timestamp dateTime) {
		this.id = id;
		this.author = author;
		this.text = text;
		this.dateTime = dateTime;
	}

	@Override
	public String toString() {
		return "Message{" +
				"id=" + id +
				", author=" + author +
				", text='" + text + '\'' +
				", dateTime=" + dateTime +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, author, text, dateTime);
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
		return id == message.id && author.equals(message.author)
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}
}
