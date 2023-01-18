package edu.school21.chat.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.*;

public class Program {
	
	private static final String DB_URL = "jdbc:postgresql://localhost/postgres";
	private static final String DB_USER = "postgres";
	private static final String DB_PWD = "postgres";
	private static final String DB_SCHEMA = "/schema.sql";
	private static final String DB_DATA = "/data.sql";

	public static void main(String[] args) throws SQLException {
		HikariDataSource dataSource = HikariConnect();
		
		initData(dataSource);
		MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);
		User creator = new User(1L, "Bob", "123", new ArrayList<Chatroom>(), new ArrayList<Chatroom>());
		User author = creator;
		Chatroom room = new Chatroom(1L, "random", creator, new ArrayList<Message>());
		Message message = new Message(null, author, room, "Hello!", Timestamp.valueOf(LocalDateTime.now()));
		messagesRepository.save(message);
		System.out.println(message.getId());
		System.out.println((Message)messagesRepository.findById(message.getId()).orElse(null));

		dataSource.close();
	}

	private static void initData(DataSource dataSource) {
		runQueriesFromFile(dataSource, DB_SCHEMA);
		runQueriesFromFile(dataSource, DB_DATA);
	}

	private static void runQueriesFromFile(DataSource dataSource, String filename) {
		try {
			Connection connection = dataSource.getConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(Program.class.getResource(filename).openStream()));
			StringBuilder stringBuilder = new StringBuilder();
			String str = reader.readLine();
			while (str != null) {
				stringBuilder.append(str);
				str = reader.readLine();
			}
			connection.createStatement().execute(stringBuilder.toString());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static HikariDataSource HikariConnect() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(DB_URL);
		config.setUsername(DB_USER);
		config.setPassword(DB_PWD);

		return (new HikariDataSource(config));
	}
}
