package de.ubweb.taskplaner.project.view;

import java.io.IOException;

import de.ubweb.taskplaner.project.presenter.ProjectsPresenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ProjectsView {
	private Parent rootView;
	private ProjectsPresenter presenter;

	public ProjectsView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/ubweb/taskplaner/project/view/display_projects.fxml"));
			rootView = loader.load();
			presenter = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Parent getView() {
		return rootView;
	}

	public ProjectsPresenter getPresenter() {
		return presenter;
	}
}
