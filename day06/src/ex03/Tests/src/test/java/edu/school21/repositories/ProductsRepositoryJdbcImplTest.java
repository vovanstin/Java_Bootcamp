package edu.school21.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.jdbc.datasource.embedded.*;

import edu.school21.models.Product;

public class ProductsRepositoryJdbcImplTest {
	
	private DataSource dataSource;

	final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
		new Product(0L, "Lenovo laptop MSCHSD-81273678", 555888L),
        new Product(1L, "HP Dominator NaGiBaToR", 15000L),
        new Product(2L, "MacBook Pro, 1gb RAM, 1GHZ, 13inches", 999999999L),
        new Product(3L, "Huawei TotallyNotACloneOfIphone", 2000L),
        new Product(4L, "SberPortal", 15999L),
        new Product(5L, "Another Yandex IoT Device", 20999L),
        new Product(6L, "Another Yandex IoT Device ", 10888L)
	);
  	final Product EXPECTED_UPDATED_PRODUCT = new Product(4L, "SberPortal", 10L);
	final Product EXPECTED_SAVED_PRODUCT  = new Product(7L, "test", 777L);
	final List<Product> EXPECTED_DELETE_PRODUCT = Arrays.asList(
		new Product(0L, "Lenovo laptop MSCHSD-81273678", 555888L),
        new Product(1L, "HP Dominator NaGiBaToR", 15000L),
        new Product(2L, "MacBook Pro, 1gb RAM, 1GHZ, 13inches", 999999999L),
        new Product(3L, "Huawei TotallyNotACloneOfIphone", 2000L),
        new Product(5L, "Another Yandex IoT Device", 20999L),
        new Product(6L, "Another Yandex IoT Device ", 10888L)
	);

	public static void assertProducts(Product expected, Product actual) {
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getPrice(), actual.getPrice());
	}

	@BeforeEach
	private void resetDBAndGetConnection() throws SQLException {
		this.dataSource = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.HSQL)
				.addScript("schema.sql")
				.addScript("data.sql")
				.build();
	}

	@Test
	void testFindAll() throws SQLException {
		ProductsRepository productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
		List<Product> actualList = productsRepository.findAll();
		for (int i = 0; i < actualList.size() && i < EXPECTED_FIND_ALL_PRODUCTS.size(); i++) {
			assertProducts(EXPECTED_FIND_ALL_PRODUCTS.get(i), actualList.get(i));
		}
	}

	@ParameterizedTest
	@ValueSource(longs = {0, 6, 3})
	void testFindByIdTrue(Long input) throws SQLException {
		ProductsRepository productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
		assertTrue(productsRepository.findById(input).isPresent());
	}

	@ParameterizedTest
	@ValueSource(longs = {23, 57, 100})
	void testFindByIdFalse(Long input) throws SQLException {
		ProductsRepository productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
		assertFalse(productsRepository.findById(input).isPresent());
	}

	@Test
	void testUpdate() throws SQLException {
		ProductsRepository productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
		Product product = (Product)productsRepository.findById(4L).orElse(null);
		product.setPrice(10L);
		productsRepository.update(product);
		product = (Product)productsRepository.findById(4L).orElse(null);
		assertProducts(EXPECTED_UPDATED_PRODUCT, product);
	}

	@Test
	void testSave() throws SQLException {
		ProductsRepository productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
		Product product = new Product(null, "test", 777L);
		productsRepository.save(product);
		product = (Product)productsRepository.findById(product.getId()).orElse(null);
		assertProducts(EXPECTED_SAVED_PRODUCT, product);
	}

	@Test
	void testDelete() throws SQLException {
		ProductsRepository productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
		productsRepository.delete(4L);
		List<Product> actualList = productsRepository.findAll();
		for (int i = 0; i < actualList.size() && i < EXPECTED_DELETE_PRODUCT.size(); i++) {
			assertProducts(EXPECTED_DELETE_PRODUCT.get(i), actualList.get(i));
		}
	}
}
