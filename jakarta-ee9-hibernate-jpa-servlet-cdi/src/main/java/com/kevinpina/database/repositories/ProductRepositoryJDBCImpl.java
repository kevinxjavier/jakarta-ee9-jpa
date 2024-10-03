package com.kevinpina.database.repositories;

import static com.kevinpina.database.fields.CategoryFieldSQL.CATEGORY;
import static com.kevinpina.database.fields.ProductFieldSQL.CATEGORY_ID;
import static com.kevinpina.database.fields.ProductFieldSQL.DATE;
import static com.kevinpina.database.fields.ProductFieldSQL.NAME;
import static com.kevinpina.database.fields.ProductFieldSQL.PRICE;
import static com.kevinpina.database.fields.ProductFieldSQL.ID;
import static com.kevinpina.database.fields.ProductFieldSQL.SKU;
import static com.kevinpina.database.queries.ProductQuerySQL.DELETE;
import static com.kevinpina.database.queries.ProductQuerySQL.INSERT;
import static com.kevinpina.database.queries.ProductQuerySQL.SELECT_ALL;
import static com.kevinpina.database.queries.ProductQuerySQL.SELECT_BY_ID;
import static com.kevinpina.database.queries.ProductQuerySQL.UPDATE;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.kevinpina.configs.MysqlConnectionPrincipal;
import com.kevinpina.configs.Repositorio;
import com.kevinpina.model.entities.Category;
import com.kevinpina.model.entities.Product;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Repositorio
@RepositorioJDBC
//@ApplicationScoped
public class ProductRepositoryJDBCImpl implements CrudRepository<Product> {

	@Inject
	private Logger log;
	
	@Inject
//	@Named("beanConnection")
	@MysqlConnectionPrincipal // Or use @Named("beanConnection")
	private Connection connection;
	
//	private Connection connection;
//
//	public ProductRepositoryImpl(Connection connection) {
//		this.connection = connection;
//	}

	/**
	 * For a Bean or Component it's recommended to use @PostConstruct instead of a Constructor unles we need to initialize 
	 * the Bean or Component through a Constructor passing arguments to initiatlize some attributes 
	 */
	// Will execute once and when we call ProductRepositoryImpl.java because is @ApplicationScoped 
	// If this were @RequestScope will call everytime we invoke this class
	@PostConstruct
	public void initialize() {
		log.info("Initializing " + this.getClass().getName());
	}

	// Will execute everytime when we redeploy the app; because is @ApplicationScoped
	// If this were @RequestScope will call everytime we invoke this class
	@PreDestroy
	public void destroy() {
		log.info("Destroiyng " + this.getClass().getName());
	}

	@Override
	public List<Product> list() throws SQLException {
		List<Product> products = new ArrayList<>();

		final String SQL = SELECT_ALL.getSql();

		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(SQL)) {

			while (resultSet.next()) {
				Product product = getProduct(resultSet);
				products.add(product);
			}
		}

		return products;
	}

	@Override
	public Product findById(Long id) throws SQLException {
		Product product = null;

		final String SQL = SELECT_BY_ID.getSql();

		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

			preparedStatement.setLong(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					product = getProduct(resultSet);
				}
			}
		}

		return product;
	}

	private Product getProduct(ResultSet resultSet) throws SQLException {
		return Product.builder().id(resultSet.getLong(ID.getField())).name(resultSet.getString(NAME.getField()))
				.price(resultSet.getDouble(PRICE.getField())).date(resultSet.getDate(DATE.getField()).toLocalDate())
				.sku(resultSet.getString(SKU.getField()))
				.category(Category.builder().id(resultSet.getLong(CATEGORY_ID.getField()))
						.name(resultSet.getString(CATEGORY.getField() + "_" + NAME.getField())).build())
				.build();
	}

	@Override
	public void save(Product product) throws SQLException {

		boolean idIsNotNull = product.getId() != null && product.getId() > 0;

		String sql = null;

		if (idIsNotNull) {
			sql = UPDATE.getSql();
		} else {
			sql = INSERT.getSql();
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, product.getName());
			preparedStatement.setDouble(2, product.getPrice());
			preparedStatement.setString(3, product.getSku());
			preparedStatement.setLong(4, product.getCategory().getId());

			if (idIsNotNull) {
				preparedStatement.setLong(5, product.getId());
			} else {
				preparedStatement.setDate(5, Date.valueOf(product.getDate()));
			}

			int result = preparedStatement.executeUpdate();
			if (result == 1) { // 0 is not Success
				System.out.println(sql.substring(0, 6) + " Success. Product = [" + product + "]");
			}
		}

	}

	@Override
	public void delete(Long id) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE.getSql())) {
			preparedStatement.setLong(1, id);
			int result = preparedStatement.executeUpdate();
			if (result == 1) { // 0 is not Success
				System.out.println("DELETE Success. Id = [" + id + "]");
			}
		}
	}

}
