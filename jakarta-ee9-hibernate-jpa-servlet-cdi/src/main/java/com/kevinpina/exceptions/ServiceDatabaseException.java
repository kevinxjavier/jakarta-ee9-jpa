package com.kevinpina.exceptions;

public class ServiceDatabaseException extends RuntimeException {

	private static final long serialVersionUID = 3081434657354441945L;

	public ServiceDatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceDatabaseException(String message) {
		super(message);
	}

}
