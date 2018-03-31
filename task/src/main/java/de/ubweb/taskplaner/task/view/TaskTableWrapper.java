package de.ubweb.taskplaner.task.view;

import java.time.LocalDate;

import de.ubweb.taskplaner.task.model.Category;
import de.ubweb.taskplaner.task.model.Task;
import de.ubweb.taskplaner.task.model.TaskDifficulty;
import de.ubweb.taskplaner.task.model.TaskPriority;
import de.ubweb.taskplaner.task.model.TaskStatus;
import de.ubweb.taskplaner.task.model.TaskType;

public class TaskTableWrapper {
	private Task task;

	//for table rows to display highlighting
	private boolean hasChanges = false;

	public TaskTableWrapper(Task task) {
		this.task = task;
	}

	public Task getTask() {
		return task;
	}

	public boolean isHasChanges() {
		return hasChanges;
	}

	public void setHasChanges(boolean hasChanges) {
		this.hasChanges = hasChanges;
	}

	public int getTaskId() {
		return task.getTaskId();
	}

	public String getTitle() {
		return task.getTitle();
	}

	public TaskType getTaskType() {
		return task.getTaskType();
	}

	public LocalDate getCreatedAt() {
		return task.getCreatedAt();
	}

	public LocalDate getActiveFrom() {
		return task.getActiveFrom();
	}

	public LocalDate getStartedAt() {
		return task.getStartedAt();
	}

	public LocalDate getFinishedAt() {
		return task.getFinishedAt();
	}

	public LocalDate getScheduledFor() {
		return task.getScheduledFor();
	}

	public LocalDate getDeadline() {
		return task.getDeadline();
	}

	public String getDescription() {
		return task.getDescription();
	}

	public TaskStatus getTaskStatus() {
		return task.getTaskStatus();
	}

	public TaskDifficulty getTaskDifficulty() {
		return task.getTaskDifficulty();
	}

	public TaskPriority getTaskPriority() {
		return task.getTaskPriority();
	}

	public Category getCategory() {
		return task.getCategory();
	}

	public int getRescheduleCount() {
		return task.getRescheduleCount();
	}

	public Task getParentTask() {
		return task.getParentTask();
	}

}
