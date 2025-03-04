package com.dev.marc.fitnesstrackingapplication;

import com.google.api.client.auth.oauth2.Credential;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static com.dev.marc.fitnesstrackingapplication.model.GoogleAuthService.authorize;

public class Application extends javafx.application.Application {


	private static final String MAIN_VIEW = "/com/dev/marc/fitnesstrackingapplication/view/startUp.fxml";
	private static final String DASHBOARD = "/com/dev/marc/fitnesstrackingapplication/view/Dashboard.fxml";

	@Override
	public void start(Stage stage) {
		try {

			Parent root = FXMLLoader.load(getClass().getResource(MAIN_VIEW));
			Scene scene = new Scene(root);
			stage.setTitle("Fitness Tracker");
			Image icon = new Image(getClass().getResourceAsStream("/assets/dumbell.png"));
			stage.getIcons().add(icon);
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			System.err.println("Error occurred: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		launch();

		try {
			Credential credential = authorize();
			System.out.println("Authorization successful!");
			System.out.println("Access Token: " + credential.getAccessToken());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Authorization failed.");
		}
	}
}
