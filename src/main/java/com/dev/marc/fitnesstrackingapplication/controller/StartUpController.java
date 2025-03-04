package com.dev.marc.fitnesstrackingapplication.controller;

import com.dev.marc.fitnesstrackingapplication.model.GoogleAuthService;
import com.dev.marc.fitnesstrackingapplication.model.GoogleUser;
import com.dev.marc.fitnesstrackingapplication.model.SimpleDataSource;
import com.dev.marc.fitnesstrackingapplication.model.UserService;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.json.JSONObject;

import javax.sql.DataSource;
import java.net.URL;
import java.util.ResourceBundle;

public class StartUpController implements Initializable {

	@FXML
	private Label label;

	@FXML
	private AnchorPane mainAnchorPane;

	@FXML
	private Pane pane;

	@FXML
	private ImageView backgroundImage;

	@FXML
	private Button btnLogin;

	@FXML
	private void handleLogin() {
		try {
			// Authorize and retrieve credentials.
			Credential credential = GoogleAuthService.authorize();
			System.out.println("Login successful!");

			// Fetch user details using our updated method.
			GoogleUser googleUser = fetchUserDetails(credential);

			if (googleUser == null || googleUser.getId().isEmpty()) {
				System.err.println("Failed to fetch user details or missing Google ID.");
				return;
			}

			// Use UserService to store/retrieve user information from the database.
			DataSource dataSource = new SimpleDataSource();
			UserService userService = new UserService(dataSource);
			// Check by Google ID (using googleUser.getGoogleId())
			GoogleUser existingUser = userService.findByGoogleId(googleUser.getId());
			if (existingUser == null) {
				userService.save(googleUser);
				System.out.println("New user saved to the database.");
			} else {
				System.out.println("User already exists in the database.");
			}

			// Update UI elements.
			label.setText("Welcome, " + googleUser.getName() + "!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// Stub method: Replace with actual API call (e.g., People API) to fetch user details.
	public GoogleUser fetchUserDetails(Credential credential) {
		try {
			HttpTransport httpTransport = new NetHttpTransport();
			JsonFactory jsonFactory = new JacksonFactory();

			HttpRequestFactory requestFactory = httpTransport.createRequestFactory(credential);
			GenericUrl url = new GenericUrl("https://www.googleapis.com/oauth2/v2/userinfo");

			String jsonResponse = requestFactory.buildGetRequest(url).execute().parseAsString();
			JSONObject jsonObject = new JSONObject(jsonResponse);

			GoogleUser user = new GoogleUser();
			// Use optString() to provide a default value if a field is missing.
			user.setId(jsonObject.optString("id", ""));
			user.setName(jsonObject.optString("name", "Unknown"));
			user.setEmail(jsonObject.optString("email", "Unknown"));  // Will return "Unknown" if email not provided.
			user.setProfilePictureUrl(jsonObject.optString("picture", ""));

			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// Initialization logic (if any) goes here.
	}
}
