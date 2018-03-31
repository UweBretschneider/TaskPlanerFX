package de.ubweb.taskplaner.project.controller;

import de.ubweb.taskplaner.project.model.Project;
import de.ubweb.taskplaner.project.model.ProjectManager;
import de.ubweb.taskplaner.project.presenter.CreateProjectPresenter;
import de.ubweb.taskplaner.project.view.CreateProjectView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreateProjectController {
	private CreateProjectView view;
	private CreateProjectPresenter presenter;
	private Stage stage;

	public void createProject() {
		view = new CreateProjectView();
		presenter = view.getPresenter();
		presenter.setController(this);

		stage = new Stage();
		stage.setTitle("Projekt erstellen");
		stage.setScene(new Scene(view.getView(), 800, 600));
		stage.show();
	}

	public void createSelectedProject(Project project) {
		ProjectManager.getInstance().createProject(project);

		stage.close();
	}
}
