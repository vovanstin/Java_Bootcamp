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

import edu.school21.sockets.models.Message;

@Component("messageRepositoryJdbc")
public class MessageRepositoryImpl implements CrudRepository<Message> {
	
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public MessageRepositoryImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Message> findAll() {
		return jdbcTemplate.query("select * from Messages", new BeanPropertyRowMapper<>(Message.class));
	}

	@Override
	public Optional<Message> findById(Long id) {
		Message message = null;
		try {
			message = jdbcTemplate.queryForObject("select * from Messages where id = ?",
					new BeanPropertyRowMapper<>(Message.class), new Object[] { id });
		} catch (DataAccessException dataAccessException) {
			System.err.println("Couldn't find entity of type Messages with id " + id);
		}
		return (Optional.ofNullable(message));
	}

	@Override
	public void update(Message message) {
		jdbcTemplate.update("UPDATE Messages SET author=?, text=?, dateTime=? WHERE id = ?", message.getAuthor().getId(), message.getText(), message.getDateTime(), message.getId());
	}

	@Override
	public void save(Message message) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement("INSERT INTO Messages (author, text, dateTime) VALUES (?, ?, ?)",
						new String[] { "id" });
				ps.setLong(1, message.getAuthor().getId());
				ps.setString(2, message.getText());
				ps.setTimestamp(3, message.getDateTime());
				return ps;
			}
		},
				keyHolder);
		message.setId(Long.valueOf(keyHolder.getKey().toString()));
	}

	@Override
	public void delete(Long id) {
		jdbcTemplate.update("delete from Messages where id = ?", id);
	}
}
