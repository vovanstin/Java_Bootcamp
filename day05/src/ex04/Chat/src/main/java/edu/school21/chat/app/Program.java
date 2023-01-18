package edu.school21.chat.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import edu.school21.chat.models.*;
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
		UsersRepository usersRepository = new UsersRepositoryJdbcImpl(dataSource);
		List<User> list = usersRepository.findAll(1, 2);
		for (User u : list) {
			System.out.println(u);
		}

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
			connection.close();
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
