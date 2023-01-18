package school21.spring.service.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import school21.spring.service.models.User;

public class UsersRepositoryJdbcImpl implements UsersRepository {

	private DataSource dataSource;
	private final String SELECT_ALL_QUERY_TEMPLATE = "SELECT * FROM Users";
	private final String SELECT_QUERY_TEMPLATE_ID = "SELECT * FROM Users WHERE id=?";
	private final String SELECT_QUERY_TEMPLATE_EMAIL = "SELECT * FROM Users WHERE email=?";
	private final String UPDATE_QUERY_TEMPLATE = "UPDATE Users SET email=? WHERE id=?";
	private final String INSERT_QUERY_TEMPLATE = "INSERT INTO Users (email) VALUES (?)";
	private final String DELETE_QUERY_TEMPLATE = "DELETE FROM Users WHERE id=?";

	public UsersRepositoryJdbcImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<User> findAll() {
		List<User> lUsers = new ArrayList<User>();
		try (Connection connection = dataSource.getConnection()) {
			ResultSet result = connection.prepareStatement(SELECT_ALL_QUERY_TEMPLATE).executeQuery();

			while (result.next()) {
				lUsers.add(new User(result.getLong("id"), result.getString("email")));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return (lUsers);
	}

	@Override
	public Optional<User> findById(Long id) {
		User user = null;
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(SELECT_QUERY_TEMPLATE_ID);
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				user = new User(result.getLong("id"), result.getString("email"));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return (Optional.ofNullable(user));
	}

	@Override
	public void update(User user) {
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY_TEMPLATE);
			statement.setString(1, user.getEmail());
			statement.setLong(2, user.getId());
			statement.execute();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void save(User user) {
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(INSERT_QUERY_TEMPLATE,
					PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getEmail());
			statement.execute();
			ResultSet result = statement.getGeneratedKeys();
			result.next();
			user.setId(result.getLong("id"));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void delete(Long id) {
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(DELETE_QUERY_TEMPLATE);
			statement.setLong(1, id);
			statement.execute();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public Optional<User> findByEmail(String email) {
		User user = null;
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(SELECT_QUERY_TEMPLATE_EMAIL);
			statement.setString(1, email);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				user = new User(result.getLong("id"), result.getString("email"));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return (Optional.ofNullable(user));
	}
}
