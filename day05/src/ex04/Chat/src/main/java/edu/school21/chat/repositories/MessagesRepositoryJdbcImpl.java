package edu.school21.chat.repositories;

import java.util.Optional;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

public class MessagesRepositoryJdbcImpl implements MessagesRepository{

	private final DataSource dataSource;
	private final String SELECT_QUERY_TEMPLATE = "SELECT * FROM chat.messages WHERE id=%d";
	private final String INSERT_QUERY_TEMPLATE = "INSERT INTO chat.messages (author, chatroom, text, dateTime) VALUES (?, ?, ?, ?) RETURNING id";
	private final String UPDATE_QUERY_TEMPLATE = "UPDATE chat.messages SET author=?, chatroom=?, text=?, dateTime=? WHERE chat.messages.id=?";

	public MessagesRepositoryJdbcImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Optional<Message> findById(Long id) {
		try {
			Connection connection = dataSource.getConnection();
			ResultSet result = connection.prepareStatement(String.format(SELECT_QUERY_TEMPLATE, id)).executeQuery();
			Message message = null;
			User user;
			Chatroom chatroom;

			if (result.next()) {
				user = new UsersRepositoryJdbcImpl(dataSource).findById(result.getLong("author")).orElse(null);
				chatroom = new ChatroomsRepositoryJdbcImpl(dataSource).findById(result.getLong("chatroom")).orElse(null);
				message = new Message(result.getLong("id"), user, chatroom, result.getString("text"), result.getTimestamp("dateTime"));
			}
			connection.close();
			return (Optional.ofNullable(message));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return (Optional.empty());
	}

	@Override
	public void save(Message message) throws NotSavedSubEntityException {
		try {
			if (new UsersRepositoryJdbcImpl(dataSource).findById(message.getAuthor().getId()).isPresent()
					&& new ChatroomsRepositoryJdbcImpl(dataSource).findById(message.getChatroom().getId()).isPresent()) {
				Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT_QUERY_TEMPLATE);
				statement.setLong(1, message.getAuthor().getId());
				statement.setLong(2, message.getChatroom().getId());
				statement.setString(3, message.getText());
				statement.setTimestamp(4, message.getDateTime());
				ResultSet result = statement.executeQuery();
				if (result.next()) {
					message.setId(result.getLong("id"));
				}
				connection.close();
			} else {
				throw new NotSavedSubEntityException();
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} catch (NotSavedSubEntityException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void update(Message message) {
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY_TEMPLATE);
			statement.setLong(1, message.getAuthor().getId());
			statement.setLong(2, message.getChatroom().getId());
			statement.setString(3, message.getText());
			statement.setTimestamp(4, message.getDateTime());
			statement.setLong(5, message.getId());
			statement.execute();
			connection.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
}
