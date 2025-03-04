package com.dev.marc.fitnesstrackingapplication.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
//		GaussianBlur backgroundBlur = new GaussianBlur(20); // Increase value for stronger blur
//		mainAnchorPane.setEffect(backgroundBlur);

//		// Apply a subtle blur to the glass pane for realism
//		GaussianBlur paneBlur = new GaussianBlur(10);
//		pane.setEffect(paneBlur);
	}
}
