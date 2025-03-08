package com.dev.marc.fitnesstrackingapplication.model;
// This class handles the Google OAuth2 authentication flow.

// Import necessary Google API client classes.
import com.google.api.client.auth.oauth2.Credential;  // Represents the OAuth 2.0 credentials.
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;  // Helps with user authorization for installed applications.
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;  // Handles the OAuth2 callback using a local HTTP server.
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;  // Manages the OAuth 2.0 flow.
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;  // Loads client secrets from a JSON file.
import com.google.api.client.http.javanet.NetHttpTransport;  // Provides an HTTP transport based on Java's networking.
import com.google.api.client.json.JsonFactory;  // Factory class for creating JSON parsers and generators.
import com.google.api.client.json.jackson2.JacksonFactory;  // Jackson implementation for JSON parsing.
import com.google.api.client.util.store.FileDataStoreFactory;  // Stores credential data in files.

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;  // Used for the list of scopes.

// This class manages the OAuth2 flow and returns the user's credentials.
public class GoogleAuthService {
	// Path to the credentials file (downloaded from Google Cloud Console).
	private static final String CREDENTIALS_FILE_PATH = "src/main/resources/client_secret_826790037879-u427ffkr9ilib09ln7qqn96d46ar3lbq.apps.googleusercontent.com.json";

	// Create a JSON_FACTORY using Jackson for JSON processing.
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	// Define the scopes required for the application (profile and email).
	private static final List<String> SCOPES = List.of(
			"https://www.googleapis.com/auth/userinfo.profile",  // Access to basic profile info.
			"https://www.googleapis.com/auth/userinfo.email"     // Access to the user's email.
	);

	// The authorize() method initiates the OAuth2 flow and returns a Credential object.
	public static Credential authorize() throws Exception {
		// Use try-with-resources to automatically close the InputStream.
		try (InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH)) {
			// Load the client secrets from the JSON file.
			GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

			// Build the GoogleAuthorizationCodeFlow using the provided client secrets and scopes.
			GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
					new NetHttpTransport(),   // HTTP transport
					JSON_FACTORY,             // JSON factory
					clientSecrets,            // Client secrets
					SCOPES                    // Scopes requested
			)
					.setDataStoreFactory(new FileDataStoreFactory(new File("tokens")))  // Store credentials in the 'tokens' folder.
					.setAccessType("offline")       // Request offline access to get a refresh token.
					.setApprovalPrompt("force")
					.build()  ;   // Force user to re-consent every time (deprecated; new versions use setPrompt(\"consent\")).\n            .build();

			// Launch a local server to handle the OAuth2 callback and open the browser for user login.
			return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		}
	}
}
