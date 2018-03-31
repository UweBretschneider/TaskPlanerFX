package de.ubweb.taskplaner.task.model;

public class TaskManagerUpdate {
	private TaskManagerUpdateType updateType;
	private Task task;

	public TaskManagerUpdateType getUpdateType() {
		return updateType;
	}

	public void setUpdateType(TaskManagerUpdateType updateType) {
		this.updateType = updateType;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
