package com.dev.marc.fitnesstrackingapplication.model;
// This class provides methods to interact with the database for user-related operations.

// Import necessary SQL classes.
import java.sql.*;
import javax.sql.DataSource;

// UserService uses a DataSource to perform database operations like finding and saving a user.
public class UserService {
	// DataSource is used to obtain database connections.
	private final DataSource dataSource;

	// Constructor to initialize the UserService with a given DataSource.
	public UserService(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * findByGoogleId() looks up a user in the database using their Google ID.
	 * It returns a GoogleUser object if found, otherwise null.
	 *
	 * @param googleId The Google user id used as the lookup key.
	 * @return A GoogleUser object with the user's details or null if not found.
	 */
	public GoogleUser findByGoogleId(String googleId) {
		// SQL query to select user details by google_id.
		String sql = "SELECT id, google_id, email, name, profile_picture_url FROM users WHERE google_id = ?";
		try (Connection conn = dataSource.getConnection();   // Obtain a connection from the DataSource.
			 PreparedStatement stmt = conn.prepareStatement(sql)) {  // Prepare the SQL statement.
			stmt.setString(1, googleId);  // Set the Google ID parameter in the query.
			try (ResultSet rs = stmt.executeQuery()) {  // Execute the query and get the results.
				if (rs.next()) {  // If a row exists in the result set:
					GoogleUser user = new GoogleUser();  // Create a new GoogleUser object.
					// Set the internal database id (convert int to String).
					user.setId(String.valueOf(rs.getInt("id")));
					// Set the Google unique id from the result set.
					user.setId(rs.getString("google_id"));
					// Set the user's email.
					user.setEmail(rs.getString("email"));
					// Set the user's name.
					user.setName(rs.getString("name"));
					// Set the URL/path to the user's profile picture.
					user.setProfilePictureUrl(rs.getString("profile_picture_url"));
					return user;  // Return the populated GoogleUser object.
				}
			}
		} catch (SQLException e) {  // Catch any SQL exceptions.
			e.printStackTrace();  // Print the stack trace.
		}
		return null;  // Return null if no user is found.
	}

	/**
	 * save() inserts a new user record into the database.
	 * It sets the user's details into the INSERT query, and then retrieves the generated key (id).
	 *
	 * @param user The GoogleUser object to be saved in the database.
	 */
	public void save(GoogleUser user) {
		// SQL query to insert a new user.
		String sql = "INSERT INTO users (google_id, email, name, profile_picture_url) VALUES (?, ?, ?, ?)";
		try (Connection conn = dataSource.getConnection();  // Get a connection.
			 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {  // Prepare statement and return generated keys.
			// Set the parameters in the SQL query using the user data.
			stmt.setString(1, user.getId());  // Use GoogleUser.getGoogleId() for the google_id column.
			stmt.setString(2, user.getEmail());       // Set email.
			stmt.setString(3, user.getName());        // Set name.
			stmt.setString(4, user.getProfilePictureUrl());  // Set profile picture URL.
			int affectedRows = stmt.executeUpdate();  // Execute the INSERT and get number of affected rows.
			if (affectedRows == 0) {  // If no rows are affected, throw an exception.
				throw new SQLException("Creating user failed, no rows affected.");
			}
			// Retrieve the auto-generated key (the internal id) from the database.
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {  // If a key is available:
					user.setId(String.valueOf(generatedKeys.getInt(1)));  // Set the generated id in the user object.
				} else {  // If no key was generated, throw an exception.
					throw new SQLException("Creating user failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {  // Catch and print any SQL exceptions.
			e.printStackTrace();
		}
	}

	/**
	 * getImagePathForUser() retrieves the profile picture URL (or image path) for a user from the database.
	 *
	 * @param googleId The Google user id to look up the image path.
	 * @return The profile_picture_url as a String, or null if not found.
	 */
	public String getImagePathForUser(String googleId) {
		// SQL query to select the profile picture URL from the users table.
		String sql = "SELECT profile_picture_url FROM users WHERE google_id = ?";
		try (Connection conn = dataSource.getConnection();  // Obtain a connection.
			 PreparedStatement stmt = conn.prepareStatement(sql)) {  // Prepare the statement.
			stmt.setString(1, googleId);  // Set the Google ID parameter.
			try (ResultSet rs = stmt.executeQuery()) {  // Execute the query.
				if (rs.next()) {  // If a result is found:
					return rs.getString("profile_picture_url");  // Return the image path/URL.
				}
			}
		} catch (SQLException e) {  // Catch any SQL exceptions.
			e.printStackTrace();
		}
		return null;  // Return null if no image path is found.
	}
}
