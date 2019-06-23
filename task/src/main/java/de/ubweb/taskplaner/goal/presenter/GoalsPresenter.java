package de.ubweb.taskplaner.goal.presenter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import de.ubweb.taskplaner.goal.controller.DisplayGoalsController;
import de.ubweb.taskplaner.goal.model.Goal;
import de.ubweb.taskplaner.goal.view.GoalView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class GoalsPresenter implements Initializable {
	private DisplayGoalsController controller;

	private ObservableList<Goal> goals;

	@FXML
	private VBox mainVbox;

	public void setController(DisplayGoalsController controller) {
		this.controller = controller;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void setGoals(List<Goal> goals) {
		this.goals = FXCollections.observableArrayList(goals);

		for (Goal goal : this.goals) {
			mainVbox.getChildren().add(new GoalView(goal, controller));
		}
	}

}
