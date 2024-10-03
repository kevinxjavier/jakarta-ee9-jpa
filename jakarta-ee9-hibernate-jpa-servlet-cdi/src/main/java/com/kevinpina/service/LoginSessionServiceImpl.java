package com.kevinpina.service;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LoginSessionServiceImpl implements LoginSessionService {

	@Override
	public Optional<String> getUsername(HttpServletRequest req) {
		HttpSession session = req.getSession();

		if (session.getAttribute("username") != null) {
			return Optional.of((String) session.getAttribute("username"));
		}
		return Optional.empty();
	}

}
