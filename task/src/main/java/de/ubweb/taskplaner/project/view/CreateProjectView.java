package de.ubweb.taskplaner.project.view;

import java.io.IOException;

import de.ubweb.taskplaner.project.presenter.CreateProjectPresenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class CreateProjectView {
	private Parent rootView;
	private CreateProjectPresenter presenter;

	public CreateProjectView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/ubweb/taskplaner/project/view/create_project.fxml"));
			rootView = loader.load();
			presenter = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Parent getView() {
		return rootView;
	}

	public CreateProjectPresenter getPresenter() {
		return presenter;
	}
}
