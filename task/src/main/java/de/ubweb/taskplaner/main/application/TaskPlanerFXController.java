package de.ubweb.taskplaner.main.application;

import de.ubweb.taskplaner.main.event.EventBus;
import de.ubweb.taskplaner.main.event.ProjectEvent;
import de.ubweb.taskplaner.main.event.TaskEvent;
import javafx.event.ActionEvent;

public class TaskPlanerFXController {

	public void createTask(ActionEvent event) {
		TaskEvent taskEvent = new TaskEvent(TaskEvent.CREATE_TASK);
		EventBus.getInstance().deliverEvent(taskEvent);
	}

	public void createProject(ActionEvent event) {
		ProjectEvent projectEvent = new ProjectEvent(ProjectEvent.CREATE_PROJECT);
		EventBus.getInstance().deliverEvent(projectEvent);
	}

}
