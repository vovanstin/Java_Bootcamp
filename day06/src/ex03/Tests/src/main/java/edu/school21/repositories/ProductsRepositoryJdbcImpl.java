package edu.school21.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import edu.school21.models.Product;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
	
	private final DataSource dataSource;
	private final String SELECT_ALL_QUERY_TEMPLATE = "SELECT * FROM products";
	private final String SELECT_QUERY_TEMPLATE = "SELECT * FROM products WHERE id=?";
	private final String UPDATE_QUERY_TEMPLATE = "UPDATE products SET name=?, price=? WHERE id=?";
	private final String INSERT_QUERY_TEMPLATE = "INSERT INTO products (name, price) VALUES (?, ?)";
	private final String DELETE_QUERY_TEMPLATE = "DELETE FROM products WHERE id=?";

	public ProductsRepositoryJdbcImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Product> findAll() throws SQLException {
		List<Product> lProducts = new ArrayList<Product>();
		Connection connection = dataSource.getConnection();
		ResultSet result = connection.prepareStatement(SELECT_ALL_QUERY_TEMPLATE).executeQuery();

		while (result.next()) {
			lProducts.add(new Product(result.getLong("id"), result.getString("name"), result.getLong("price")));
		}
		connection.close();
		return (lProducts);
	}

	@Override
	public Optional<Product> findById(Long id) throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(SELECT_QUERY_TEMPLATE);
		statement.setLong(1, id);
		ResultSet result = statement.executeQuery();
		Product product = null;

		if (result.next()) {
			product = new Product(result.getLong("id"), result.getString("name"), result.getLong("price"));
		}
		connection.close();
		return (Optional.ofNullable(product));
	}

	@Override
	public void update(Product product) throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY_TEMPLATE);
		statement.setString(1, product.getName());
		statement.setLong(2, product.getPrice());
		statement.setLong(3, product.getId());
		statement.execute();
		connection.close();
	}

	@Override
	public void save(Product product) throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(INSERT_QUERY_TEMPLATE, PreparedStatement.RETURN_GENERATED_KEYS);
		statement.setString(1, product.getName());
		statement.setLong(2, product.getPrice());
		statement.execute();
		ResultSet result = statement.getGeneratedKeys();
		result.next();
		product.setId(result.getLong("id"));
		connection.close();
	}

	@Override
	public void delete(Long id) throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(DELETE_QUERY_TEMPLATE);
		statement.setLong(1, id);
		statement.execute();
		connection.close();
	}
}
