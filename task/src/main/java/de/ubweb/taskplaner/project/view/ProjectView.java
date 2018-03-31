package de.ubweb.taskplaner.project.view;

import java.io.IOException;

import de.ubweb.taskplaner.project.presenter.ProjectPresenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ProjectView {
	private Parent rootView;
	private ProjectPresenter presenter;

	public ProjectView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/ubweb/taskplaner/project/view/project.fxml"));
			rootView = loader.load();
			presenter = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Parent getView() {
		return rootView;
	}

	public ProjectPresenter getPresenter() {
		return presenter;
	}
}
