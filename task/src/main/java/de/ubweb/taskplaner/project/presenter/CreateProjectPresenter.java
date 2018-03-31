package de.ubweb.taskplaner.project.presenter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import de.ubweb.taskplaner.project.controller.CreateProjectController;
import de.ubweb.taskplaner.project.model.Project;
import de.ubweb.taskplaner.project.model.ProjectStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class CreateProjectPresenter implements Initializable {
	private CreateProjectController controller;

	@FXML
	private TextField titleField;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void setController(CreateProjectController controller) {
		this.controller = controller;
	}

	public void createProject(ActionEvent event) {
		Project project = new Project();

		project.setTitle(titleField.getText());
		project.setCreatedAt(LocalDate.now());
		project.setProjectStatus(ProjectStatus.CREATED);

		controller.createSelectedProject(project);
	}

}
