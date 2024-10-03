package com.kevinpina.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import com.kevinpina.service.LoginSessionService;
import com.kevinpina.service.LoginSessionServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet({ "/login", "/login.html" })
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 4902515493740412080L;

	private static final String USERNAME = "login";
	private static final String PASSWORD = "pass";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		if (USERNAME.equals(username) && PASSWORD.equals(password)) {

			HttpSession session = req.getSession();
			session.setAttribute("username", username);

			/*
			resp.setContentType("text/html; charset=UTF-8;");
			try (PrintWriter out = resp.getWriter()) {
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("		<head>");
				out.println("			<meta charset=\"UTF-8\" />");
				out.println("			<title>Login as &lt;" + username + "&gt;</title>");
				out.println("		</head>");
				out.println("		<body>");
				out.println("			<h1>Login Successful</h1>");
				out.println("			<h3>User &lt;" + username + "&gt; has Logged.</h3>");
				out.println("			<a href='" + req.getContextPath() + "/index.html'>Volver</a>");
				out.println("		</body>");
				out.println("</html>");
			}
			*/
			
			// We comment the above code because if not we can go back to the page and login again
			// After redirect to /login.html we avoid go back to the page and login again "sendRedirect is a new Request"
			resp.sendRedirect(req.getContextPath() + "/login.html");
			
		} else {
//			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"We're so sorry but Kevin say! your are not authorized to see this page!");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				
		LoginSessionService login = new LoginSessionServiceImpl();
		
		Optional<String> sessionOptional = login.getUsername(req);

		if (sessionOptional.isPresent()) {
			try (PrintWriter out = resp.getWriter()) {
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("		<head>");
				out.println("			<meta charset=\"UTF-8\" />");
				out.println("			<title>Logged as &lt;" + sessionOptional.get() +"&gt;</title>");
				out.println("		</head>");
				out.println("		<body>");
				out.println("			<h1>Already Logged</h1>");
				out.println("			<h3>User &lt;" + sessionOptional.get() + "&gt; Logged.</h3>");
				if (sessionOptional.isPresent()) {
					out.println("				<tr><th colspan=\'4\'  bgcolor=\'blue\'>"
							+ "<p>User " + sessionOptional.get() + ". <a href='" + req.getContextPath() + "/index.jsp'>Volver</a></p>"
							+ "<p><a href=\"" + req.getContextPath() + "/logout\">Logout</a></th></tr>");
				}
				out.println("		</body>");
				out.println("</html>");
			}
		} else {
			// This forward is the same Request! So after redirect the URL still will be /login.html and not /login.jsp
			// Otherwise is resp.sendRedirect(req.getContextPath() + "/login.jsp"); whch is a 302 and then a 200 and the URL will be /login.jsp 
			getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
		}
	}

}
