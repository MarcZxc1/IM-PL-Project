package com.dev.marc.fitnesstrackingapplication.utils;

import com.dev.marc.fitnesstrackingapplication.model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authenticator {

	private boolean authenticateUser(String username, String password) {
		String query = "SELECT password FROM users WHERE username = ?";

		try (Connection conn = DatabaseConnection.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				String storedPassword = rs.getString("password");
				username = rs.getString("role");
				return storedPassword.equals(password);
			}
		} catch (SQLException e) {
			System.err.println("Database error: " + e.getMessage());
		}
		return false;
	}

}
