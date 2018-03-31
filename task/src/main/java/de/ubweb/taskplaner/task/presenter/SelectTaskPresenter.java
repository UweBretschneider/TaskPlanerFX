package de.ubweb.taskplaner.task.presenter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import de.ubweb.taskplaner.project.controller.ProjectController;
import de.ubweb.taskplaner.task.model.Task;
import de.ubweb.taskplaner.task.view.SelectTaskView;
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

public class SelectTaskPresenter implements Initializable {
	private ProjectController controller;
	private SelectTaskView selectTaskView;
	private ObservableList<Task> tasks;

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

	public void setController(ProjectController controller) {
		this.controller = controller;
	}

	public void setView(SelectTaskView selectTaskView) {
		this.selectTaskView = selectTaskView;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = FXCollections.observableArrayList(tasks);

		tasksTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tasksTableView.getItems().clear();
		tasksTableView.setItems(this.tasks);
	}

	public void selectTask(ActionEvent e) {
		Task selectedTask = tasksTableView.getSelectionModel().getSelectedItem();
		if (selectedTask != null) {
			controller.linkTask(selectedTask);
		}
	}
}