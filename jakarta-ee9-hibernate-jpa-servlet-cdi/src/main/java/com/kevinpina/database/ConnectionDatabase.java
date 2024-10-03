package com.kevinpina.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabase {

	/**
	 * Find MySQL Time Zone
	 * 		SELECT * FROM mysql.time_zone_name WHERE NAME LIKE 'Europe%';
	 */
	private static String url = "jdbc:mysql://localhost:3306/enterprise?serverTimezone=Europe/Madrid";
	private static String username = "root";
	private static String password = "123456";
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
	
}
