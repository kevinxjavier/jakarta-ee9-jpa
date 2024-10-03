package com.kevinpina.servlet;

import java.io.IOException;
import java.util.Optional;

import com.kevinpina.service.LoginSessionService;
import com.kevinpina.service.LoginSessionServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = -5298424009018388726L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LoginSessionService login = new LoginSessionServiceImpl();
		Optional<String> username = login.getUsername(req);

		if (username.isPresent()) {
			HttpSession session = req.getSession();
//			session.removeAttribute("username"); // This only remove the attribute username from session
			session.invalidate(); // This will destroy all the session
		}
//		sendRedirect is a new Request"
		resp.sendRedirect(req.getContextPath() + "/login.html");
	}

}
