package de.ubweb.taskplaner.task.view;

import java.io.IOException;

import de.ubweb.taskplaner.task.presenter.CreateTaskPresenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class CreateTaskView {
	private Parent rootView;
	private CreateTaskPresenter presenter;

	public CreateTaskView() {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/de/ubweb/taskplaner/task/view/create_task.fxml"));
			rootView = loader.load();
			presenter = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Parent getView() {
		return rootView;
	}

	public CreateTaskPresenter getPresenter() {
		return presenter;
	}
}
