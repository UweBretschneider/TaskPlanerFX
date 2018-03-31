package de.ubweb.taskplaner.project.controller;

import de.ubweb.taskplaner.project.model.Project;
import de.ubweb.taskplaner.project.presenter.ProjectPresenter;
import de.ubweb.taskplaner.project.view.ProjectView;
import de.ubweb.taskplaner.task.model.Task;
import de.ubweb.taskplaner.task.model.TaskManager;
import de.ubweb.taskplaner.task.view.SelectTaskView;
import javafx.scene.Parent;

public class ProjectController {
	private Project project;
	private ProjectView view;
	private ProjectPresenter presenter;

	private SelectTaskView selectView;

	public void displayProject(Project project) {
		view = new ProjectView();
		presenter = view.getPresenter();
		presenter.setController(this);

		this.project = project;

		presenter.setProject(project);
	}

	public Parent getView() {
		return view.getView();
	}

	public void linkTask() {
		selectView = new SelectTaskView();
		selectView.getPresenter().setController(this);
		selectView.getPresenter().setTasks(TaskManager.getInstance().getOpenTasks());
	}

	public void linkTask(Task selectedTask) {
		project.addTask(selectedTask);
		selectView.closeStage();

		presenter.setProject(project);
	}

}
