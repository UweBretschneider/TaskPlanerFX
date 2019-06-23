package de.ubweb.taskplaner.main.event;

import de.ubweb.taskplaner.task.model.Task;

public class TaskEvent extends CustomEvent {
	public static final int CREATE_TASK = 1;
	public static final int CREATE_SELECTED_TASK = 2;

	public static final int DISPLAY_TASKS = 10;
	public static final int SWITCH_TO_TOMORROW = 11;
	public static final int SWITCH_TO_YESTERDAY = 12;

	public static final int SCHEDULE_TASK_FOR_TODAY = 20;
	public static final int SCHEDULE_TASK_FOR_DATE = 21;
	public static final int SCHEDULE_TASK_FOR_SELECTED_DATE = 22;

	public static final int FINISH_SELECTED_TASK = 30;
	public static final int MARK_SELECTED_TASK_AS_FAILED = 31;
	public static final int RESCHEDULE_SELECTED_TASK = 32;

	public static final int DELETE_SELECTED_TASK = 40;

	public static final int EDIT_SELECTED_TASK = 50;
	
	public static final int LINK_SELECTED_TASK = 60;

	private Task selectedTask;

	public TaskEvent(int action) {
		super(action);
	}

	public Task getSelectedTask() {
		return selectedTask;
	}

	public void setSelectedTask(Task selectedTask) {
		this.selectedTask = selectedTask;
	}

}
