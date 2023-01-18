package edu.school21.chat.repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.DataSource;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

public class ChatroomsRepositoryJdbcImpl implements ChatroomsRepository {
	
	private DataSource dataSource;
	private final String QUERY_TEMPLATE = "SELECT * FROM chat.rooms WHERE id=%d";

	public ChatroomsRepositoryJdbcImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public Optional<Chatroom> findById(Long id) {
		try {
			Connection connection = dataSource.getConnection();
			ResultSet result = connection.prepareStatement(String.format(QUERY_TEMPLATE, id)).executeQuery();
			Chatroom chatroom = null;
			User user;

			if (result.next()) {
				user = new UsersRepositoryJdbcImpl(dataSource).findById(result.getLong("owner")).orElse(null);
				chatroom = new Chatroom(result.getLong("id"), result.getString("name"), user, null);
			}
			return (Optional.ofNullable(chatroom));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return (Optional.empty());
	}
}
