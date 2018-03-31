package de.ubweb.taskplaner.task.view;

import java.io.IOException;

import de.ubweb.taskplaner.task.presenter.TasksPresenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class TasksView {
	private Parent rootView;
	private TasksPresenter presenter;

	public TasksView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/ubweb/taskplaner/task/view/tasks_view.fxml"));
			rootView = loader.load();
			presenter = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Parent getView() {
		return rootView;
	}

	public TasksPresenter getPresenter() {
		return presenter;
	}

}
