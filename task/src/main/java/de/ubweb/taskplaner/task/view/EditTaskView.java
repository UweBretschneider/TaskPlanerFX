package de.ubweb.taskplaner.task.view;

import java.io.IOException;

import de.ubweb.taskplaner.task.presenter.EditTaskPresenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class EditTaskView {
	private Parent rootView;
	private EditTaskPresenter presenter;

	public EditTaskView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/ubweb/taskplaner/task/view/edit_task.fxml"));
			rootView = loader.load();
			presenter = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Parent getView() {
		return rootView;
	}

	public EditTaskPresenter getPresenter() {
		return presenter;
	}
}
