package de.ubweb.taskplaner.project.controller;

import de.ubweb.taskplaner.main.event.CustomEvent;
import de.ubweb.taskplaner.main.event.IService;
import de.ubweb.taskplaner.main.event.TaskEvent;
import de.ubweb.taskplaner.project.model.Project;
import de.ubweb.taskplaner.project.presenter.ProjectPresenter;
import de.ubweb.taskplaner.project.view.ProjectView;
import de.ubweb.taskplaner.task.model.Task;
import de.ubweb.taskplaner.task.model.TaskManager;
import de.ubweb.taskplaner.task.view.SelectTaskView;
import javafx.scene.Parent;

public class ProjectController implements IService{
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
		//presenter.refreshTable();
	}

	@Override
	public void handleEvent(CustomEvent event) {
		if(event instanceof TaskEvent) {
			TaskEvent taskEvent = (TaskEvent) event;
			switch(taskEvent.getAction()) {
			case TaskEvent.LINK_SELECTED_TASK:
				linkTask(taskEvent.getSelectedTask());
				break;
			}
		}
	}

}
