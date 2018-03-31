package de.ubweb.taskplaner.task.controller;

import de.ubweb.taskplaner.main.application.TaskPlanerFX;
import de.ubweb.taskplaner.main.event.CustomEvent;
import de.ubweb.taskplaner.main.event.IService;
import de.ubweb.taskplaner.main.event.TaskEvent;
import de.ubweb.taskplaner.task.model.Task;

public class TaskService implements IService {

	public void displayTasks() {
		DisplayTasksController controller = new DisplayTasksController();
		TaskPlanerFX.getInstance().addTab(controller.getView(), "Aufgabenplanung", false);
	}

	public void createTask() {
		CreateTaskController controller = new CreateTaskController();
		controller.createTask();
	}

	public void editTask(Task task) {
		EditTaskController controller = new EditTaskController();
		controller.editTask(task);
	}

	@Override
	public void handleEvent(CustomEvent event) {
		if (event instanceof TaskEvent) {
			TaskEvent taskEvent = (TaskEvent) event;
			switch (event.getAction()) {
			case TaskEvent.DISPLAY_TASKS:
				displayTasks();
				break;
			case TaskEvent.CREATE_TASK:
				createTask();
				break;
			case TaskEvent.EDIT_SELECTED_TASK:
				editTask(taskEvent.getSelectedTask());
				break;
			}
		}

	}

}
