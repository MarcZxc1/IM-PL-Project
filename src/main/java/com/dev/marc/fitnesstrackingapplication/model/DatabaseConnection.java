package com.dev.marc.fitnesstrackingapplication.model;
// This class is part of the model package which contains classes for data management.

// Import necessary SQL classes.
import java.sql.Connection;         // Represents a connection to a specific database.
import java.sql.DriverManager;      // Provides a basic service for managing a set of JDBC drivers.
import java.sql.SQLException;       // Exception thrown when a database access error occurs.

// This class provides a static method to obtain a Connection object to your database.
public class DatabaseConnection {

	// These constants hold the database URL, username, and password.
	// They are read from environment variables for security reasons.
	private static final String URL = System.getenv("dbUrl");       // Database URL (e.g., jdbc:postgresql://host:port/database)
	private static final String USER = System.getenv("dbUser");       // Database username
	private static final String PASSWORD = System.getenv("dbPass");   // Database password

	// This method returns a Connection object to the database.
	public static Connection getConnection() {
		try {
			// Attempt to create a connection using the DriverManager with the provided URL, username, and password.
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Connected");  // Log a message if connected successfully.
			return connection;                // Return the Connection object.
		} catch (SQLException e) {            // Catch any SQL exceptions that occur.
			System.err.println("Connection Disrupted: " + e.getMessage());  // Print error message.
			return null;                    // Return null if connection fails.
		}
	}
}
