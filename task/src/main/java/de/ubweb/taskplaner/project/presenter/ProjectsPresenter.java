package de.ubweb.taskplaner.project.presenter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import de.ubweb.taskplaner.project.controller.DisplayProjectsController;
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

public class ProjectsPresenter implements Initializable {
	private DisplayProjectsController controller;

	private ObservableList<Project> openProjects;
	private ObservableList<Project> closedProjects;

	@FXML
	private TableView<Project> openProjectsTableView;
	@FXML
	private TableColumn<Task, StringProperty> opTitleColumn;
	@FXML
	private TableView<Project> closedProjectsTableView;
	@FXML
	private TableColumn<Task, StringProperty> cpTitleColumn;

	public void setController(DisplayProjectsController controller) {
		this.controller = controller;
	}

	public void setOpenProjects(List<Project> openProjects) {
		this.openProjects = FXCollections.observableArrayList(openProjects);

		openProjectsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		openProjectsTableView.getItems().clear();
		openProjectsTableView.setItems(this.openProjects);

	}

	public void setClosedProjects(List<Project> closedProjects) {
		this.closedProjects = FXCollections.observableArrayList(closedProjects);

		closedProjectsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		closedProjectsTableView.getItems().clear();
		closedProjectsTableView.setItems(this.closedProjects);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		opTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		cpTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
	}

	public void showProjectDetails(ActionEvent event) {
		Project selectededProject = openProjectsTableView.getSelectionModel().getSelectedItem();
		if (selectededProject != null) {
			controller.displayProject(selectededProject);
		}
	}
}
