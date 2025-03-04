package com.dev.marc.fitnesstrackingapplication.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String URL = System.getenv("dbUrl");
	private static final String USER = System.getenv("dbUser");
	private static final String PASSWORD = System.getenv("dbPass");

	public static Connection getConnection() {
		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Connected");
			return connection;
		} catch (SQLException e) {
			System.err.println("Connection Disrupted: " + e.getMessage());
			return null;
		}
	}
}
