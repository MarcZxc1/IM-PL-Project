package com.dev.marc.fitnesstrackingapplication.controller;

import com.dev.marc.fitnesstrackingapplication.model.GoogleAuthService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
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


	@FXML private Button btnLogin;

	@FXML
	private void handleLogin() {
		try {
			GoogleAuthService.authorize();
			System.out.println("Login successful!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
//		GaussianBlur backgroundBlur = new GaussianBlur(20); // Increase value for stronger blur
//		mainAnchorPane.setEffect(backgroundBlur);

//		// Apply a subtle blur to the glass pane for realism
//		GaussianBlur paneBlur = new GaussianBlur(10);
//		pane.setEffect(paneBlur);
	}
}
