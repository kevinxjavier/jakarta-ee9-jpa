package com.kevinpina.filters;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.kevinpina.configs.MysqlConnectionPrincipal;
import com.kevinpina.exceptions.ServiceDatabaseException;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*") // Any route
public class ConnectionFilter implements Filter {
/*
	@Inject
//	@Named("beanConnection")
	@MysqlConnectionPrincipal // Or use @Named("beanConnection")
	private Connection connection;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// try (Connection connection = ConnectionDatabaseDataSource.getConnection()) {
//		try (Connection connection = this.connection) {	// Connection will be close with ProducerResources.close(); automatically. See catalina.out 
		try {

			if (connection.getAutoCommit()) {
				connection.setAutoCommit(false);
			}

			doFilter(request, response, chain, connection);

		} catch (SQLException | ServiceDatabaseException e) {
			e.printStackTrace();

			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Kevin says! " + e.getMessage());
		}

	}

	private void doFilter(ServletRequest request, ServletResponse response, FilterChain chain, Connection connection)
			throws IOException, ServletException, SQLException {
		try {
//			request.setAttribute("connection", connection); // Now is injected
			chain.doFilter(request, response);
			connection.commit();

		} catch (SQLException | ServiceDatabaseException e) {
			connection.rollback();
			throw e;
		}
	}
*/
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {

			// Repositories has an @Interceptor TransactionalInterceptor and may throw ServiceDatabaseException 
			chain.doFilter(request, response);

		} catch (ServiceDatabaseException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Kevin says! " + e.getMessage());
		}

	}

}
