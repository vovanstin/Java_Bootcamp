package edu.school21.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.*;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class EmbeddedDataSourceTest {
	
	private DataSource datasource;

	@BeforeEach
	void init() {
		datasource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
		.addScripts("schema.sql", "data.sql")
		.build();
	}

	@Test
	void checkGetConnect() throws SQLException {
		assertNotNull(datasource.getConnection());
	}
}
