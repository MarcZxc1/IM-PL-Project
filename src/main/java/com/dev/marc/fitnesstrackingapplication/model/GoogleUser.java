package com.dev.marc.fitnesstrackingapplication.model;
// This class represents a Google user with properties fetched from the OAuth process.

// No external imports are needed here.

public class GoogleUser {
	// Private fields to store user information.
	private String id;                // This field is used to store a unique identifier (from Google or internal DB).
	private String email;             // The user's email address.
	private String name;              // The user's full name.
	private String profilePictureUrl; // URL for the user's profile picture.

	// Default constructor.
	public GoogleUser() {
	}

	// Parameterized constructor to initialize all fields.
	public GoogleUser(String id, String email, String name, String profilePictureUrl) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.profilePictureUrl = profilePictureUrl;
	}

	// Getter for id.
	public String getId() {
		return id;
	}

	// Setter for id.
	public void setId(String id) {
		this.id = id;
	}

	// Getter for email.
	public String getEmail() {
		return email;
	}

	// Setter for email.
	public void setEmail(String email) {
		this.email = email;
	}

	// Getter for name.
	public String getName() {
		return name;
	}

	// Setter for name.
	public void setName(String name) {
		this.name = name;
	}

	// Getter for profilePictureUrl.
	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	// Setter for profilePictureUrl.
	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}
}
