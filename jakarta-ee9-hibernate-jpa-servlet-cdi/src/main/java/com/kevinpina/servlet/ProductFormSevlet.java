package com.kevinpina.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.kevinpina.configs.MysqlConnectionPrincipal;
import com.kevinpina.configs.ProductServicePrincipal;
import com.kevinpina.model.entities.Category;
import com.kevinpina.model.entities.Product;
import com.kevinpina.service.CategoryServiceImpl;
import com.kevinpina.service.ProductServiceImpl;
import com.kevinpina.service.Service;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/product/form")
public class ProductFormSevlet extends HttpServlet {

	private static final long serialVersionUID = 400264055384925024L;

	@Inject
	@ProductServicePrincipal
	// If we dont annotate with @ProductServicePrincipal will inject this class ProductServiceIJdbcmpl.java by default
	// otherwise use @Name("beanProductServiceImpl")
	private Service<Product> serviceProduct;

	@Inject
//	@Named("beanConnection")
	@MysqlConnectionPrincipal // Or use @Named("beanConnection")
	private Connection connection;
	
	@Inject
	private Service<Category> serviceCategory;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//		Retrieving Connection ConnectionFilter
//		Connection connection = (Connection) req.getAttribute("connection");

//		Retrieving Category
		getCategories(req, connection);

//		Retrieving Product
//		Service<Product> serviceProduct = new ProductServiceImpl(connection);
		Long id;
		try {
			id = Long.valueOf(req.getParameter("id"));
		} catch (NumberFormatException e) {
			id = 0L;
		}
		Optional<Product> product = Optional.empty();
		if (id > 0L) {
			product = serviceProduct.findById(id);
		}
		req.setAttribute("product", product.isPresent() ? product.get() : null);
		req.setAttribute("title", "Products Form");

		getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
//		req.getRequestDispatcher("/form.jsp").forward(req, resp);
	}

	private void getCategories(HttpServletRequest req, Connection connection) {
//		Service<Category> serviceCategory = new CategoryServiceImpl(connection);
		req.setAttribute("categories", serviceCategory.list());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		Connection connection = (Connection) req.getAttribute("connection");
//		Service<Product> serviceProduct = new ProductServiceImpl(connection);

//		Getting Paramaters

		Long id;
		try {
			id = Long.valueOf(req.getParameter("id"));
		} catch (NumberFormatException e) {
			id = null;
		}

		String name = req.getParameter("name");

		Double price;
		try {
			price = Double.valueOf(req.getParameter("price"));
		} catch (NumberFormatException e) {
			price = 0D;
		}

		String sku = req.getParameter("sku");

		LocalDate date;
		try {
			date = LocalDate.parse(req.getParameter("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		} catch (DateTimeParseException e) {
			date = null;
		}

		Long categoryId;
		try {
			categoryId = Long.valueOf(req.getParameter("category"));
		} catch (NumberFormatException e) {
			categoryId = 0L;
		}

//		Validating Parameters
		Map<String, String> errors = new HashMap<>();

		if (name == null || name.isBlank()) {
			errors.put("name", "Name is required!");
		}

		if (price.equals(0D)) {
			errors.put("price", "Price is required!");
		}

		if (sku == null || sku.isBlank()) {
			errors.put("sku", "Sku is required!");
		} else if (sku.length() > 10) {
			errors.put("sku", "Sku is more than 10 characters!");
		}

		if (date == null) {
			errors.put("date", "Date is required!");
		}

		if (categoryId.equals(0L)) {
			errors.put("category", "Category is required!");
		}

		Product product = Product.builder().id(id).name(name).sku(sku).price(price).date(date)
				.category(Category.builder().id(categoryId).build()).build();

		if (errors.isEmpty()) {

//			Saving Products
			serviceProduct.save(product);

//			Important always use redirect to avoid "Refresh the Browser or F5" and save many times
			resp.sendRedirect(req.getContextPath() + "/products");
		} else {
			req.setAttribute("errors", errors);
			getCategories(req, connection);
			req.setAttribute("product", product);
//			getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
			req.getRequestDispatcher("/form.jsp").forward(req, resp);
		}

	}

}
