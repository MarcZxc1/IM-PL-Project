package com.dev.marc.fitnesstrackingapplication.controller;

// Import model classes for authentication and database operations.
import com.dev.marc.fitnesstrackingapplication.model.GoogleAuthService;
import com.dev.marc.fitnesstrackingapplication.model.GoogleUser;
import com.dev.marc.fitnesstrackingapplication.model.SimpleDataSource;
import com.dev.marc.fitnesstrackingapplication.model.UserService;


import com.dev.marc.fitnesstrackingapplication.utils.TabSwitch;
import com.google.api.client.auth.oauth2.Credential;  // Represents OAuth credentials.
import com.google.api.client.http.GenericUrl;          // Represents a URL for HTTP requests.
import com.google.api.client.http.HttpRequest;         // Represents an HTTP request.
import com.google.api.client.http.HttpRequestFactory;  // Factory to create HTTP requests.
import com.google.api.client.http.HttpTransport;       // Handles HTTP transport.
import com.google.api.client.http.javanet.NetHttpTransport; // Implementation of HttpTransport.
import com.google.api.client.json.JsonFactory;         // Interface for JSON parsing.
import com.google.api.client.json.jackson2.JacksonFactory; // Jackson implementation of JsonFactory.
import javafx.fxml.FXML;                              // For FXML injection.
import javafx.fxml.Initializable;                      // Interface to initialize controllers.
import javafx.scene.control.Button;                    // JavaFX UI control for a button.
import javafx.scene.control.Label;                     // JavaFX UI control for a label.
import javafx.scene.control.PasswordField;             // UI control for password input.
import javafx.scene.control.TextField;                 // UI control for text input.
import javafx.scene.image.Image;                       // Represents an image.
import javafx.scene.image.ImageView;                   // Displays an Image.
import javafx.scene.layout.AnchorPane;                 // Layout container.
import javafx.scene.layout.Pane;                       // General-purpose layout container.
import javafx.stage.Stage;                             // Represents the application window.
import org.json.JSONObject;                            // For JSON parsing.

import javax.sql.DataSource;                           // Interface for getting database connections.
import java.io.File;                                   // Represents file and directory pathnames.
import java.io.IOException;                            // Exception thrown during I/O operations.
import java.net.URL;                                   // Represents a Uniform Resource Locator.
import java.util.ResourceBundle;                       // For localization resources.

/**
 * StartUpController manages the login/logout process, fetching user details from Google,
 * updating the UI, and switching scenes after login.
 */
public class StartUpController implements Initializable {

	// Link to a Label in the FXML for showing messages (e.g., welcome text).
	@FXML
	private Label label;

	// Link to the main AnchorPane defined in the FXML.
	@FXML
	private AnchorPane mainAnchorPane;

	// Link to a Pane in the FXML (for additional UI components if needed).
	@FXML
	private Pane pane;

	// Link to the ImageView for the background image.
	@FXML
	private ImageView backgroundImage;

	// Link to the Login button in the FXML.
	@FXML
	private Button btnLogin;

	// Link to the Logout button in the FXML.
	@FXML
	private Button btnLogout;

	// Link to the ImageView that will display the user's profile picture.
	@FXML
	private ImageView userImageView;

	// Link to the TextField for username input (for local login if needed).
	@FXML
	private TextField usernameField;

	// Link to the PasswordField for password input (for local login if needed).
	@FXML
	private PasswordField passwordField;

	@FXML AnchorPane paneContainer;

	// Create an instance of UserService with a SimpleDataSource to perform database operations.
	private final UserService userService = new UserService(new SimpleDataSource());

	public void setPaneContainer(AnchorPane paneContainer) {this.paneContainer = paneContainer;}

	/**
	 * loadUserImage() loads a user's image from the database using the given userId.
	 *
	 * @param userId The id of the user (as int), used to fetch the image path.
	 */
	private void loadUserImage(int userId) {
		try {
			// Retrieve the image path from the database using UserService.
			String imagePath = userService.getImagePathForUser(String.valueOf(userId));
			// Create an Image object with a file URL (assumes a local file path).
			Image image = new Image("file:" + imagePath);
			// Set the Image on the ImageView to display the user's profile picture.
			userImageView.setImage(image);
		} catch (Exception e) {
			// Print any exceptions that occur.
			e.printStackTrace();
		}
	}

	/**
	 * handleLogin() performs the login process:
	 * - Initiates Google OAuth.
	 * - Retrieves user details.
	 * - Checks the database for existing user and saves if necessary.
	 * - Updates the UI and switches the scene.
	 */
	@FXML
	private void handleLogin() {
		try {
			// OPTIONAL: For local login, you can retrieve username and password inputs.
			String username = usernameField.getText();  // Retrieve text from username text field (if used)
			String password = passwordField.getText();    // Retrieve text from password field (if used)

			// Start the Google OAuth flow and obtain the OAuth credentials.
			Credential credential = GoogleAuthService.authorize();
			System.out.println("Login successful!");

			// Retrieve user details from Google using the obtained OAuth credential.
			GoogleUser googleUser = fetchUserDetails(credential);
			System.out.println("Profile Picture URL: " + googleUser.getProfilePictureUrl());

			// Check if the user already exists in the database using the user's Google unique ID.
			// Note: If your GoogleUser class separates the internal id from the Google id,
			// you should use the correct getter (for example, googleUser.getGoogleId()).
			GoogleUser existingUser = userService.findByGoogleId(googleUser.getId());
			if (existingUser == null) {
				// If the user does not exist, save the new user into the database.
				userService.save(googleUser);
				System.out.println("New user saved to the database.");
			} else {
				System.out.println("User already exists in the database.");
			}

			// Update the welcome label on the UI with the user's name.
			label.setText("Welcome, " + googleUser.getName() + "!");

			// Load and display the user's profile picture if available.
			if (!googleUser.getProfilePictureUrl().isEmpty()) {
				Image image;
				// Check if the URL is remote (starts with http:// or https://)
				if (googleUser.getProfilePictureUrl().startsWith("http://") ||
						googleUser.getProfilePictureUrl().startsWith("https://")) {
					// Load the image from the remote URL.
					image = new Image(googleUser.getProfilePictureUrl());
				} else {
					// Otherwise, assume it's a local file path and prefix with "file:".
					image = new Image("file:" + googleUser.getProfilePictureUrl());
				}
				// Set the loaded image to the ImageView to display the profile picture.
				userImageView.setImage(image);
			} else {
				System.out.println("No profile picture URL available.");
			}

			// ********** Scene Switching Code **********
			// Retrieve the current stage (window) from the login button.
//			Stage stage = (Stage) btnLogin.getScene().getWindow();
			// Switch the scene to Register.fxml using the SceneSwitcher utility.
			// Here, we specify the new FXML path, the desired width (800) and height (600),
			// and set withAnimation to true to apply a transition effect.
			TabSwitch.switchTab(paneContainer,
					"/com/dev/marc/fitnesstrackingapplication/view/Register.fxml",
					800, 600, false);
		} catch (Exception e) {
			// If any exception occurs during login or scene switching, print the error stack trace.
			e.printStackTrace();
		}
	}


	/**
	 * handleLogout() is called when the logout button is clicked.
	 * It clears stored OAuth tokens and updates the UI to indicate the user has been logged out.
	 */
	@FXML
	private void handleLogout() {
		// Clear stored OAuth tokens (removes the tokens folder).
		clearTokens();
		// Update the welcome label to indicate logout.
		label.setText("You have been logged out.");
		System.out.println("User logged out successfully.");
	}

	/**
	 * clearTokens() deletes the folder where OAuth tokens are stored.
	 */
	private void clearTokens() {
		File tokenFolder = new File("tokens");  // Reference the tokens folder.
		if (tokenFolder.exists()) {             // Check if the folder exists.
			deleteFolder(tokenFolder);          // Recursively delete the folder and its contents.
			System.out.println("Tokens cleared.");
		} else {
			System.out.println("No tokens found.");
		}
	}

	/**
	 * deleteFolder() recursively deletes all files and subfolders in the given folder.
	 *
	 * @param folder The folder to be deleted.
	 */
	private void deleteFolder(File folder) {
		File[] files = folder.listFiles();  // Get all files in the folder.
		if (files != null) {                // Check if the folder is not empty.
			for (File file : files) {
				if (file.isDirectory()) {  // If the file is a directory,
					deleteFolder(file);    // delete it recursively.
				} else {
					file.delete();         // Otherwise, delete the file.
				}
			}
		}
		folder.delete();  // Delete the folder itself.
	}

	/**
	 * fetchUserDetails() sends a GET request to Google's userinfo endpoint using the provided credential.
	 * It parses the JSON response and maps it to a GoogleUser object.
	 *
	 * @param credential The OAuth Credential for the authenticated user.
	 * @return A GoogleUser object with details such as id, name, email, and profile picture URL.
	 */
	public GoogleUser fetchUserDetails(Credential credential) {
		try {
			// Create an HTTP transport for sending requests.
			HttpTransport httpTransport = new NetHttpTransport();
			// Create a JSON factory for parsing JSON responses.
			JsonFactory jsonFactory = new JacksonFactory();

			// Create an HTTP request factory that uses the OAuth credential.
			HttpRequestFactory requestFactory = httpTransport.createRequestFactory(credential);
			// Set the URL for the Google userinfo endpoint.
			GenericUrl url = new GenericUrl("https://www.googleapis.com/oauth2/v2/userinfo");

			// Execute the GET request and retrieve the response as a JSON string.
			String jsonResponse = requestFactory.buildGetRequest(url).execute().parseAsString();
			// Parse the JSON string into a JSONObject.
			JSONObject jsonObject = new JSONObject(jsonResponse);

			// Create a new GoogleUser object and set its fields using values from the JSON response.
			GoogleUser user = new GoogleUser();
			user.setId(jsonObject.optString("id", "Unknown"));         // Set Google's unique user id.
			user.setName(jsonObject.optString("name", "Unknown"));       // Set user's name.
			user.setEmail(jsonObject.optString("email", "Unknown"));     // Set user's email.
			user.setProfilePictureUrl(jsonObject.optString("picture", "")); // Set profile picture URL.
			return user;  // Return the populated GoogleUser object.
		} catch (IOException e) {
			// Print the exception if an error occurs during the request.
			e.printStackTrace();
			return null;  // Return null if fetching user details fails.
		}
	}

	/**
	 * initialize() is automatically called when the FXML is loaded.
	 * Use it to perform any necessary initialization.
	 *
	 * @param url The location used to resolve relative paths for the root object, or null if unknown.
	 * @param resourceBundle The resources used to localize the root object, or null if not localized.
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// Perform any initialization required when the scene loads.
	}
}