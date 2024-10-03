package com.kevinpina.filters;

import java.io.IOException;
import java.util.Optional;

import com.kevinpina.service.LoginSessionService;
import com.kevinpina.service.LoginSessionServiceImpl;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The Filters are oriented to Request init and destroy. Usefuls for transversal
 * tasks, and we can mapped to a specific Servlet the Listeners dont!
 */

// Defining private pages
//@WebFilter({ "/*" })
@WebFilter({ "/cart/*" , "/product/*" })
//@WebFilter(servletNames = "SingleServlet")					// Register a filter for a specific servlet
//@WebFilter(servletNames = {"FirstServlet", "SecondServlet"})	// Register a filter for multiple servlets

//@WebFilter(
//        urlPatterns = "/users/*",
//        filterName = "UserFilter",
//        description = "Describe Filter"       
//)

//@WebFilter({ "/*-cart", "/*-cart" })
//@WebFilter({ "/view-cart", "/add-cart" })
public class LoginFilter implements Filter {

	/**
	 * Do some task before or after the method doGet() or doPost()
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		LoginSessionService loginSessionService = new LoginSessionServiceImpl();
		Optional<String> username = loginSessionService.getUsername((HttpServletRequest) request);

		if (username.isPresent()) {
			chain.doFilter(request, response);
		} else {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"We are sorry Kevin has not Authorized the access!");
		}
	}

}
