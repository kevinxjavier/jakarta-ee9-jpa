package com.kevinpina.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import com.kevinpina.configs.ProductServicePrincipal;
import com.kevinpina.model.entities.Product;
import com.kevinpina.service.LoginSessionService;
import com.kevinpina.service.LoginSessionServiceImpl;
import com.kevinpina.service.ProductServiceImpl;
import com.kevinpina.service.Service;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({ "/products.html", "/products" })
public class ProductServlet extends HttpServlet {

	private static final long serialVersionUID = 7757978577654827122L;
	
	@Inject
	@ProductServicePrincipal
	// If we dont annotate with @ProductServicePrincipal will inject this class ProductServiceIJdbcmpl.java by default
	// otherwise use @Name("beanProductServiceImpl")
	private Service<Product> productService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("------------ Name: [" + req.getAttribute("name") + "]");

//		Connection connection = (Connection) req.getAttribute("connection");
//		Service<Product> productService = new ProductServiceImpl(connection);
		List<Product> products = productService.list();

		LoginSessionService login = new LoginSessionServiceImpl();
		Optional<String> usernameOptional = login.getUsername(req);

//		We can use this Application Attribute in all the Application (Servlets, JSP)
		String messageApplication = (String) getServletContext().getAttribute("message");
//		We can use this Request Attribute in all the Servlets
		String messageRequest = (String) req.getAttribute("message");
		
		req.setAttribute("products", products);
		req.setAttribute("usernameOptional", usernameOptional);
		req.setAttribute("title", "Products");
		
		// same Request will show in url: /products.html instead of /list.jsp 
		getServletContext().getRequestDispatcher("/list.jsp").forward(req, resp);

		if (false) {
			try (PrintWriter out = resp.getWriter()) {
	
				resp.setContentType("text/html; charset=UTF-8;");
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("		<head>");
				out.println("			<meta charset=\"UTF-8\" />");
				out.println("			<title>Request HTTP Header</title>");
				out.println("		</head>");
				out.println("		<body>");
				out.println("			<h1>Product List</h1>");
	
				out.println("			<table border=\'1\'>");
				if (usernameOptional.isPresent()) {
					out.println("				<tr><th colspan=\'4\' bgcolor=\'blue\'>User " + usernameOptional.get()
							+ "</th></tr>");
				}
				out.println("				<tr>");
				out.println("					<th>Id</th>");
				out.println("					<th>Name</th>");
				if (usernameOptional.isPresent()) {
					out.println("					<th>Price</th>");
					out.println("					<th>Add</th>");
				}
				out.println("					<th>Type</th>");
				out.println("				</tr>");
				products.forEach(product -> {
					out.println("			<tr>");
					out.println("				<td>" + product.getId() + "</td>");
					out.println("				<td>" + product.getName() + "</td>");
					if (usernameOptional.isPresent()) {
						out.println("				<td>" + product.getPrice() + "</td>");
						out.println("				<td><a href=\"" + req.getContextPath() + "/cart/add?id="
								+ product.getId() + "\">Add</a></td>");
					}
					out.println("				<td>" + product.getCategory().getName() + "</td>");
					out.println("			</tr>");
				});
				out.println("			</table>");
				out.println("			<a href='" + req.getContextPath() + "/index.html'>Volver</a>");
				
				out.println("			<p><b>Message Application: </b>" + messageApplication + "</p>");
				out.println("			<p><b>Message Request: </b>" + messageRequest + "</p>");
				out.println("		</body>");
				out.println("</html>");
	
			}
		}
	}

}
