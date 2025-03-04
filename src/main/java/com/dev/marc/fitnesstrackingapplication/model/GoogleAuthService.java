package com.dev.marc.fitnesstrackingapplication.model;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class GoogleAuthService {
	// Update the path to your credentials file.
	private static final String CREDENTIALS_FILE_PATH = "src/main/resources/client_secret_826790037879-u427ffkr9ilib09ln7qqn96d46ar3lbq.apps.googleusercontent.com.json";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	// Define required scopes. Adjust as necessary.
	private static final List<String> SCOPES = List.of("https://www.googleapis.com/auth/userinfo.profile");

	public static Credential authorize() throws Exception {
		// Load client secrets.
		try (InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH)) {
			GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

			// Build flow and trigger user authorization request.
			GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
					new NetHttpTransport(), JSON_FACTORY, clientSecrets, SCOPES)
					.setDataStoreFactory(new FileDataStoreFactory(new File("tokens")))
					.setAccessType("offline")
					// Force consent to always ask for login and not reuse previous tokens.
					.setApprovalPrompt("force")
					.build();

			// Launch the browser to let user authorize, then returns the credentials.
			return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		}
	}

}