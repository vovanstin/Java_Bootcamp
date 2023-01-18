package edu.school21.chat.repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.DataSource;

import edu.school21.chat.models.User;

public class UsersRepositoryJdbcImpl implements UsersRepository {
	
	private final DataSource dataSource;
	private final String QUERY_TEMPLATE = "SELECT * FROM chat.users WHERE id=%d";

	public UsersRepositoryJdbcImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public Optional<User> findById(Long id) {
		try {
			Connection connection = dataSource.getConnection();
			ResultSet result = connection.prepareStatement(String.format(QUERY_TEMPLATE, id)).executeQuery();
			User user = null;

			if (result.next()) {
				user = new User(result.getInt("id"), result.getString("login"), result.getString("password"), null, null);
			}
			return (Optional.ofNullable(user));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return (Optional.empty());
	}
}
