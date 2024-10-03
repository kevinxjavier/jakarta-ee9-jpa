package com.kevinpina.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cart/view")
public class ViewCartServlet extends HttpServlet {

	private static final long serialVersionUID = -1463437349142477210L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("title", "Cart");
		
		// Redirecting but using the same request
		getServletContext().getRequestDispatcher("/cart.jsp").forward(req, resp);
	}

}
