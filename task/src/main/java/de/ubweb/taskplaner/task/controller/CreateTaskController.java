package de.ubweb.taskplaner.task.controller;

import de.ubweb.taskplaner.task.model.Task;
import de.ubweb.taskplaner.task.model.TaskManager;
import de.ubweb.taskplaner.task.presenter.CreateTaskPresenter;
import de.ubweb.taskplaner.task.view.CreateTaskView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreateTaskController {
	private CreateTaskView view;
	private CreateTaskPresenter presenter;
	private Stage stage;

	public void createTask() {
		view = new CreateTaskView();
		presenter = view.getPresenter();
		presenter.setController(this);

		stage = new Stage();
		stage.setTitle("Aufgabe erstellen");
		stage.setScene(new Scene(view.getView(), 800, 600));
		stage.show();
	}

	public void createSelectedTask(Task task) {
		TaskManager.getInstance().createTask(task);

		stage.close();
	}
}
