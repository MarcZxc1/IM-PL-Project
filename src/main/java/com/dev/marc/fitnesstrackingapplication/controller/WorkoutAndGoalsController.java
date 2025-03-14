package com.dev.marc.fitnesstrackingapplication.controller;

import com.dev.marc.fitnesstrackingapplication.api.ExerciseService;
import com.dev.marc.fitnesstrackingapplication.utils.TabSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WorkoutAndGoalsController implements Initializable {

	private static String VIEW_PATH = "/com/dev/marc/fitnesstrackingapplication/view/";

	@FXML
	private Button toDashboard;

	@FXML
	private ChoiceBox<String> muscleChoiceBox;

	@FXML
	private Button fetchButton;

	@FXML
	private ListView<String> exerciseListView;

	@FXML
	private AnchorPane paneContainer;

	public void setPaneContainer(AnchorPane paneContainer) {
		this.paneContainer = paneContainer;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// Populate choice box with muscle groups
		muscleChoiceBox.getItems().addAll(
				"Biceps", "Triceps", "Chest", "Quadriceps", "Hamstrings", "Calves",
				"Lats", "Traps", "Lower_back", "Shoulders"
		);


		muscleChoiceBox.setValue("SELECT MUSCLE GROUP");


		fetchButton.setOnAction(event -> {
			fetchButton.setDisable(true);

			String selectedMuscle = muscleChoiceBox.getValue();
			List<String> exercises = ExerciseService.getExercises(selectedMuscle);

			if (exercises.isEmpty()) {
				exerciseListView.getItems().setAll("No exercises available.");
			} else {
				exerciseListView.getItems().setAll(exercises);
			}

			fetchButton.setDisable(false); // Re-enable button after fetching
		});
	}


	@FXML
	public void switchToWaG(ActionEvent event) throws IOException {
		TabSwitch.switchTab(paneContainer, VIEW_PATH + "Workout-and-Goals.fxml",
				1250, 680, false);
	}

	@FXML
	public void HOME(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_PATH + "Dashboard.fxml"));
		Parent root = loader.load();

		// Get the current scene and update it
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root, 1250, 680));
		stage.show();
	}

}
