package com.dev.marc.fitnesstrackingapplication.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class DashboardController {

	private static String VIEW_PATH = "/com/dev/marc/fitnesstrackingapplication/view";

    @FXML private Button TrackBtn;

	@FXML private Button WaGBtn;

	@FXML private Button homeButton;

	ProfileController profileController = new ProfileController();
	MapController mapController = new MapController();
	WorkoutAndGoalsController workoutAndGoalsController = new WorkoutAndGoalsController();

	@FXML
	AnchorPane paneContainer;

	public void setPaneContainer(AnchorPane paneContainer) {
		this.paneContainer = paneContainer;
	}


	@FXML
	public void goToProfile(ActionEvent event) throws IOException {
		profileController.setPaneContainer(paneContainer);
		profileController.goToProfile(event);
	}

	@FXML
	public void goToTrack(ActionEvent event) throws IOException {
		mapController.setPaneContainer(paneContainer);
		mapController.switchToMap1(event);
	}

	@FXML
	public void goToWorkoutGoals(ActionEvent event) throws IOException{
		workoutAndGoalsController.setPaneContainer(paneContainer);
		workoutAndGoalsController.switchToWaG(event);
	}

}
