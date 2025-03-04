package com.dev.marc.fitnesstrackingapplication.controller;

import com.almasb.fxgl.entity.action.Action;
import com.dev.marc.fitnesstrackingapplication.utils.SceneSwitcher;
import com.dev.marc.fitnesstrackingapplication.utils.TabSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;

import java.io.IOException;

public class MapController {



	private static String VIEW_PATH = "/com/dev/marc/fitnesstrackingapplication/view/";

	@FXML private Button toDashboard;

	@FXML
	private AnchorPane paneContainer;

	public void setPaneContainer(AnchorPane paneContainer) {
		this.paneContainer = paneContainer;
	}

	@FXML
	public void switchToMap1(ActionEvent event) throws IOException {
		TabSwitch.switchTab(paneContainer, VIEW_PATH + "Track.fxml",
				1250, 680, false);
	}

//
//	@FXML public void backToDashboard(ActionEvent event)throws  IOException{
//		dashboardController.setPaneContainer(paneContainer);
//		dashboardController.backToDashboard(event);
//
//	}

	@FXML
	public void homeButton(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_PATH + "Dashboard.fxml"));
		Parent root = loader.load();

		// Get the current scene and update it
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root, 1250, 680));
		stage.show();
	}



}
