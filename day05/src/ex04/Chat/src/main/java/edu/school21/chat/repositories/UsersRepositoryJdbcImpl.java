package edu.school21.chat.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

public class UsersRepositoryJdbcImpl implements UsersRepository {
	
	private final DataSource dataSource;
	private final String SELECT_QUERY_TEMPLATE = "SELECT * FROM chat.users WHERE id=%d";
	private final String FINDALL_QUERY_TEMPLATE = "select chat.users.id, chat.users.login, chat.users.password, array_agg(distinct chat.rooms.owner) as listrooms," +
													" array_agg(distinct chat.messages.chatroom) as listsocial from chat.users full join chat.rooms on" +
													" chat.rooms.owner = chat.users.id full join chat.messages on chat.messages.author = chat.users.id group by chat.users.id limit ? offset ?;";

	public UsersRepositoryJdbcImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public Optional<User> findById(Long id) {
		try {
			Connection connection = dataSource.getConnection();
			ResultSet result = connection.prepareStatement(String.format(SELECT_QUERY_TEMPLATE, id)).executeQuery();
			User user = null;

			if (result.next()) {
				user = new User(result.getLong("id"), result.getString("login"), result.getString("password"), null, null);
			}
			connection.close();
			return (Optional.ofNullable(user));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return (Optional.empty());
	}

	@Override
	public List<User> findAll(int page, int size){
		try {
			List<User> lUsers = new ArrayList<User>();
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(FINDALL_QUERY_TEMPLATE);
			statement.setInt(1, size);
			statement.setLong(2, (long)(size * page));
			ResultSet result = statement.executeQuery();
			List<Chatroom> lChatrooms;
			List<Chatroom> lSocializes;

			while (result.next()) {
				lChatrooms = new ArrayList<Chatroom>();
				for (Object l : (Object[])result.getArray("listrooms").getArray()) {
					if (l != null) {
						lChatrooms.add(new ChatroomsRepositoryJdbcImpl(dataSource).findById(Long.parseLong(String.valueOf(l))).orElse(null));
					}
				}
				lSocializes = new ArrayList<Chatroom>();
				for (Object l : (Object[])result.getArray("listsocial").getArray()) {
					if (l != null) {
						lSocializes.add(new ChatroomsRepositoryJdbcImpl(dataSource).findById(Long.parseLong(String.valueOf(l))).orElse(null));
					}
				}
				lUsers.add(new User(result.getLong("id"), result.getString("login"), result.getString("password"), lChatrooms, lSocializes));
			}
			connection.close();
			return (lUsers);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return (null);
	}
}
