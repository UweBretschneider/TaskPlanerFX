package de.ubweb.taskplaner.project.presenter;

import java.net.URL;
import java.util.ResourceBundle;

import de.ubweb.taskplaner.project.controller.ProjectController;
import de.ubweb.taskplaner.project.model.Project;
import de.ubweb.taskplaner.task.model.Task;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProjectPresenter implements Initializable {
	private ProjectController controller;
	private Project project;
	private ObservableList<Task> projectTasks;

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

	public ProjectController getController() {
		return controller;
	}

	public void setController(ProjectController controller) {
		this.controller = controller;
	}

	public void setProject(Project project) {
		this.project = project;

		projectTasks = FXCollections.observableArrayList(project.getProjectTasks());

		tasksTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tasksTableView.getItems().clear();
		tasksTableView.setItems(projectTasks);
	}

	public void linkTask(ActionEvent e) {
		controller.linkTask();
	}

	public void refreshTable() {
		tasksTableView.refresh();
		
	}

}
