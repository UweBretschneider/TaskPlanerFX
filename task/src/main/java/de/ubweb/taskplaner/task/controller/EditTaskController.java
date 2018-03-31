package de.ubweb.taskplaner.task.controller;

import de.ubweb.taskplaner.task.model.Task;
import de.ubweb.taskplaner.task.model.TaskManager;
import de.ubweb.taskplaner.task.presenter.EditTaskPresenter;
import de.ubweb.taskplaner.task.view.EditTaskView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EditTaskController {
	private Task task;

	private EditTaskView view;
	private EditTaskPresenter presenter;
	private Stage stage;

	public void editTask(Task task) {
		this.task = task;

		view = new EditTaskView();
		presenter = view.getPresenter();
		presenter.setController(this);
		presenter.setTask(task);
		presenter.setView(view);

		stage = new Stage();
		stage.setTitle("Aufgabe editieren");
		stage.setScene(new Scene(view.getView(), 800, 600));
		stage.show();
	}

	public void editSelectedTask(Task task) {
		TaskManager.getInstance().updateTask(task);
		stage.close();
	}
}
