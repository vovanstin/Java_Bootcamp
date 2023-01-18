package edu.school21.chat.repositories;

import java.util.Optional;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

public class MessagesRepositoryJdbcImpl implements MessagesRepository{

	private final DataSource dataSource;
	private final String QUERY_TEMPLATE = "SELECT * FROM chat.messages WHERE id=%d";

	public MessagesRepositoryJdbcImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Optional<Message> findById(Long id) {
		try {
			Connection connection = dataSource.getConnection();
			ResultSet result = connection.prepareStatement(String.format(QUERY_TEMPLATE, id)).executeQuery();
			Message message = null;
			User user;
			Chatroom chatroom;

			if (result.next()) {
				user = new UsersRepositoryJdbcImpl(dataSource).findById(result.getLong("author")).orElse(null);
				chatroom = new ChatroomsRepositoryJdbcImpl(dataSource).findById(result.getLong("chatroom")).orElse(null);
				message = new Message(result.getInt("id"), user, chatroom, result.getString("text"), result.getTimestamp("dateTime"));
			}
			return (Optional.ofNullable(message));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return (Optional.empty());
	}
}
