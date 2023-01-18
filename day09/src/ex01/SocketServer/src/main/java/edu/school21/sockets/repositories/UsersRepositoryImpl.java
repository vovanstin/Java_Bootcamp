package edu.school21.sockets.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import edu.school21.sockets.models.User;

@Component("usersRepositoryJdbc")
public class UsersRepositoryImpl implements UsersRepository {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public UsersRepositoryImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<User> findAll() {
		return jdbcTemplate.query("select * from Users", new BeanPropertyRowMapper<>(User.class));
	}

	@Override
	public Optional<User> findById(Long id) {
		User user = null;
		try {
			user = jdbcTemplate.queryForObject("select * from Users where id = ?",
					new BeanPropertyRowMapper<>(User.class), new Object[] { id });
		} catch (DataAccessException dataAccessException) {
			System.err.println("Couldn't find entity of type Users with id " + id);
		}
		return (Optional.ofNullable(user));
	}

	@Override
	public void update(User user) {
		jdbcTemplate.update("UPDATE Users SET username=?, password=? WHERE id = ?", user.getUsername(), user.getPassword(), user.getId());
	}

	@Override
	public void save(User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement("INSERT INTO Users (username, password) VALUES (?, ?)",
						new String[] { "id" });
				ps.setString(1, user.getUsername());
				ps.setString(2, user.getPassword());
				return ps;
			}
		},
				keyHolder);
		user.setId(Long.valueOf(keyHolder.getKey().toString()));
	}

	@Override
	public void delete(Long id) {
		jdbcTemplate.update("delete from Users where id = ?", id);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		User user = null;
		try {
			user = jdbcTemplate.queryForObject("select * from Users where username = ?",
					new BeanPropertyRowMapper<>(User.class), new Object[] { username });
		} catch (DataAccessException dataAccessException) {
			System.err.println("Couldn't find entity of type Users with username " + username);
		}
		return (Optional.ofNullable(user));
	}
}
