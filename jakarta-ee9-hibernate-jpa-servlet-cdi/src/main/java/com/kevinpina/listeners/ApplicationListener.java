package com.kevinpina.listeners;

import com.kevinpina.model.ShoppingCart;
import com.kevinpina.servlet.ShoppingCartServlet;

import jakarta.enterprise.context.SessionScoped;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

/**
 * - ServletContextListener: General Context of the Application; Initialize
 * Global Resources Configuration, Database Common Connections.
 * 
 * - ServletRequestListener: Request Configuration.
 * 
 * - HttpSessionListener: Session Configuration; Only access to the Session
 * Object.
 */
@WebListener
public class ApplicationListener implements ServletContextListener, ServletRequestListener, HttpSessionListener {

	private ServletContext servletContext;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().log("------ Initializing Application");
		servletContext = sce.getServletContext();

//		We can use this Application Attribute in all the Application (Servlets, JSP)
		servletContext.setAttribute("message", "Anonymous Application Message");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		sce.getServletContext().log("------ Destroying Application");
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		sre.getServletContext().log("------ Initializing Request");

//		We can use this Request Attribute in all the Servlets 
		sre.getServletRequest().setAttribute("message", "Anonymous Request Message");
		sre.getServletContext().setAttribute("title", "Home");
	}

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		sre.getServletContext().log("------ Destroying Request");
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
//		 Java Automatically create a Session "JSESSION" a session empty
//		 create a new Session empty after destroy the session.
		servletContext.log("------ Initializing HTTP Session");

//		// Option 1
//		HttpSession session = se.getSession();
//		session.setAttribute(ShoppingCartServlet.SHOPPING_CART, new ShoppingCart());

//		// Option 2
//		se.getSession().setAttribute(ShoppingCartServlet.SHOPPING_CART, new ShoppingCart());

//		// Option 3
		// Now currently its been implemented in: com.kevinpina.model.ShoppingCart wich
		// is @SessionScoped.
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
//		 This method is invoke when we called the method session.invalidate();
//		 or when session is expired by inactivity or when we shutdown the app
//		 or shutdown the server.
		servletContext.log("------ Destroying HTTP Session");
	}

}
