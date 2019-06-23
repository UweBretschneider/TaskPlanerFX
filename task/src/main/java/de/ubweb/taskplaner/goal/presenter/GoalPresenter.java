package de.ubweb.taskplaner.goal.presenter;

import java.net.URL;
import java.util.ResourceBundle;

import de.ubweb.taskplaner.goal.controller.GoalController;
import de.ubweb.taskplaner.goal.model.Goal;
import de.ubweb.taskplaner.project.controller.ProjectController;
import de.ubweb.taskplaner.project.model.Project;
import de.ubweb.taskplaner.task.model.Task;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GoalPresenter implements Initializable{
	private GoalController controller;
	private Goal goal;
	private ObservableList<Task> goalTasks;

	@FXML
	private Label goalLabel;
	
	@FXML
	private TableView<Task> tasksTableView;
	@FXML
	private TableColumn<Task, Integer> idColumn;
	@FXML
	private TableColumn<Task, StringProperty> nameColumn;
	@FXML
	private TableColumn<Task, StringProperty> statusColumn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		idColumn.setCellValueFactory(new PropertyValueFactory<>("taskId"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("taskStatus"));
	}

	public GoalController getController() {
		return controller;
	}

	public void setController(GoalController controller) {
		this.controller = controller;
	}

	public void setGoal(Goal goal) {
		this.goal = goal;
		
		goalLabel.setText(goal.getDescription());

		goalTasks = FXCollections.observableArrayList(goal.getTasks());

		tasksTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tasksTableView.getItems().clear();
		tasksTableView.setItems(goalTasks);
	}

	public void linkTask(ActionEvent e) {
		controller.linkTask();
	}
}
