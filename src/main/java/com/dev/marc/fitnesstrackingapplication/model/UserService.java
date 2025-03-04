package com.dev.marc.fitnesstrackingapplication.model;

import java.sql.*;
import javax.sql.DataSource;

public class UserService {
	private final DataSource dataSource;

	public UserService(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// Find a user by Google ID.
	public GoogleUser findByGoogleId(String googleId) {
		String sql = "SELECT id, google_id, email, name, profile_picture_url FROM users WHERE google_id = ?";
		try (Connection conn = dataSource.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, googleId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					GoogleUser user = new GoogleUser();
					// Set the internal database id (if you store it as an int, you can convert it to String if needed)
					user.setId(String.valueOf(rs.getInt("id")));
					// Set the Google unique id
					user.setId(rs.getString("google_id"));
					user.setEmail(rs.getString("email"));
					user.setName(rs.getString("name"));
					user.setProfilePictureUrl(rs.getString("profile_picture_url"));
					return user;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; // No user found.
	}

	// Save a new user to the database.
	public void save(GoogleUser user) {
		String sql = "INSERT INTO users (google_id, email, name, profile_picture_url) VALUES (?, ?, ?, ?)";
		try (Connection conn = dataSource.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, user.getId());
			stmt.setString(2, user.getEmail());
			stmt.setString(3, user.getName());
			stmt.setString(4, user.getProfilePictureUrl());
			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Creating user failed, no rows affected.");
			}
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					user.setId(String.valueOf(generatedKeys.getInt(1)));
				} else {
					throw new SQLException("Creating user failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
