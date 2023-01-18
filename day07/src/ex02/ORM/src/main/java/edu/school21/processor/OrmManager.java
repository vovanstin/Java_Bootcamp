package edu.school21.processor;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.sql.DataSource;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.google.auto.service.AutoService;

import edu.school21.annotation.OrmColumn;
import edu.school21.annotation.OrmColumnId;
import edu.school21.annotation.OrmEntity;

@SupportedAnnotationTypes("edu.school21.annotation.OrmEntity")
@AutoService(Processor.class)
public class OrmManager extends AbstractProcessor {

	static DataSource dataSource;

	public OrmManager() {
		dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).build();
		try {
			File file = new File("./target/schema.sql");
			if (!file.exists()) {
				System.err.println("schema.sql not found! Rebuild project (mvn clean compile)");
			} else {
				StringBuilder stringBuilder = new StringBuilder();
				Scanner scanner = new Scanner(file);

				while (scanner.hasNextLine()) {
					stringBuilder.append(scanner.nextLine() + "\n");
				}
				System.out.println(stringBuilder);
				dataSource.getConnection().createStatement().execute(stringBuilder.toString());
				scanner.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(OrmEntity.class);
		for (Element annotatedElement : annotatedElements) {
			OrmEntity ormEntity = annotatedElement.getAnnotation(OrmEntity.class);
			try {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(String.format("\nDROP TABLE IF EXISTS %s;\n\n", ormEntity.table()));
				stringBuilder.append(String.format("CREATE TABLE %s (", ormEntity.table()));
				Boolean first = true;
				for (Element e : annotatedElement.getEnclosedElements()) {
					if (!e.toString().contains("(")) {
						if (first) {
							stringBuilder.append("\n");
							first = false;
						} else {
							stringBuilder.append(",\n");
						}
						OrmColumnId ormColumnId = e.getAnnotation(OrmColumnId.class);
						OrmColumn ormColumn = e.getAnnotation(OrmColumn.class);
						if (ormColumnId != null) {
							if (Class.forName(annotatedElement.toString())
									.getDeclaredField(e.getSimpleName().toString()).getType().getSimpleName()
									.toLowerCase().equals("long")) {
								stringBuilder.append("\tid BIGINT IDENTITY PRIMARY KEY");
							} else {
								stringBuilder.append("\tid INT IDENTITY PRIMARY KEY");
							}
						} else {
							if (ormColumn.length() == 0) {
								stringBuilder
										.append("\t" + String.format(
												getEntityFieldType(Class.forName(annotatedElement.toString())
														.getDeclaredField(e.getSimpleName().toString())),
												ormColumn.name()));
							} else {
								stringBuilder.append(
										String.format("\t%s VARCHAR(%d)", ormColumn.name(), ormColumn.length()));
							}
						}
					}
				}
				stringBuilder.append("\n);\n");
				FileWriter fileWriter = new FileWriter("./target/schema.sql");
				fileWriter.append(stringBuilder);
				fileWriter.flush();
				fileWriter.close();

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return false;
	}

	private String getEntityFieldType(Field type) {
		switch (type.getType().getSimpleName().toLowerCase()) {
			case "string":
				return "%s VARCHAR(256)";
			case "int":
			case "integer":
				return "%s INT";
			case "long":
				return "%s BIGINT";
			case "boolean":
				return "%s BOOLEAN";
			case "double":
				return "%s DOUBLE";
			case "float":
				return "%s FLOAT";
		}
		return null;
	}

	public void save(Object entity) {
		try (Connection connection = dataSource.getConnection()) {
			StringBuilder typesBuilder = new StringBuilder();
			StringBuilder valuesBuilder = new StringBuilder();

			for (Field f : entity.getClass().getDeclaredFields()) {
				if (f.getAnnotation(OrmColumn.class) != null) {
					typesBuilder.append(f.getAnnotation(OrmColumn.class).name() + ", ");
					f.setAccessible(true);
					if (f.getType().getSimpleName().equals("String")) {
						valuesBuilder.append("'" + f.get(entity).toString() + "'");
					} else {
						valuesBuilder.append(f.get(entity).toString());
					}
					valuesBuilder.append(", ");
					f.setAccessible(false);
				}
			}
			typesBuilder.delete(typesBuilder.length() - 2, typesBuilder.length());
			valuesBuilder.delete(valuesBuilder.length() - 2, valuesBuilder.length());
			String query = "INSERT INTO " + entity.getClass().getAnnotation(OrmEntity.class).table()
					+ " (" + typesBuilder + ") VALUES (" + valuesBuilder + ")";
			System.out.println(query);
			PreparedStatement preparedStatement = connection.prepareStatement(query,
					PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				Field field = entity.getClass().getDeclaredField("id");
				field.setAccessible(true);
				field.set(entity, resultSet.getObject("id"));
				field.setAccessible(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Object entity) {
		try (Statement statement = dataSource.getConnection().createStatement()) {
			StringBuilder stringBuilder = new StringBuilder();
			String id = "";
			for (Field f : entity.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				if (f.getAnnotation(OrmColumn.class) != null) {
					stringBuilder.append(f.getAnnotation(OrmColumn.class).name());
					stringBuilder.append(" = ");
					if (f.getType().getSimpleName().equals("String")) {
						stringBuilder.append("'" + f.get(entity).toString() + "', ");
					} else {
						stringBuilder.append(f.get(entity).toString() + ", ");
					}
				} else if (f.getAnnotation(OrmColumnId.class) != null) {
					id = f.get(entity).toString();
				}
				f.setAccessible(false);
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String query = "UPDATE " + entity.getClass().getAnnotation(OrmEntity.class).table()
					+ " SET " + stringBuilder + " WHERE id = " + id;
			System.out.println(query);
			statement.execute(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public <T> T findById(Long id, Class<T> aClass) {
		try (Statement statement = dataSource.getConnection().createStatement()) {
			String query = "SELECT * FROM " + aClass.getAnnotation(OrmEntity.class).table()
					+ " WHERE id = " + id;

			System.out.println(query);
			statement.execute(query);
			ResultSet resultSet = statement.getResultSet();
			if (!resultSet.next())
				throw new SQLException("Error! User (" + id + ") Not Found!");
			T result = aClass.newInstance();
			for (Field f : result.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				if (f.getAnnotation(OrmColumnId.class) != null) {
					f.set(result, resultSet.getObject(f.getName()));
				} else if (f.getAnnotation(OrmColumn.class) != null) {
					OrmColumn ormColumn = f.getAnnotation(OrmColumn.class);
					f.set(result, resultSet.getObject(ormColumn.name()));
				}
				f.setAccessible(false);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
