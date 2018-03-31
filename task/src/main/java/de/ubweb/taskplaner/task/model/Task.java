package de.ubweb.taskplaner.task.model;

import java.time.LocalDate;

public class Task {
	private int taskId = -1;
	private String title;
	private TaskType taskType;

	private LocalDate createdAt = LocalDate.now();
	private LocalDate activeFrom = LocalDate.now();
	private LocalDate startedAt;
	private LocalDate finishedAt;
	private LocalDate scheduledFor;
	private LocalDate deadline;

	private String description;

	private TaskStatus taskStatus;
	private TaskDifficulty taskDifficulty;
	private TaskPriority taskPriority;

	private Category category;

	private int rescheduleCount = 0;

	private Task parentTask = null;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getActiveFrom() {
		return activeFrom;
	}

	public void setActiveFrom(LocalDate activeFrom) {
		this.activeFrom = activeFrom;
	}

	public LocalDate getFinishedAt() {
		return finishedAt;
	}

	public void setFinishedAt(LocalDate finishedAt) {
		this.finishedAt = finishedAt;
	}

	public LocalDate getScheduledFor() {
		return scheduledFor;
	}

	//only task manager should do that
	public void setScheduledFor(LocalDate scheduledFor) {
		this.scheduledFor = scheduledFor;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TaskStatus getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}

	public TaskDifficulty getTaskDifficulty() {
		return taskDifficulty;
	}

	public void setTaskDifficulty(TaskDifficulty taskDifficulty) {
		this.taskDifficulty = taskDifficulty;
	}

	public TaskPriority getTaskPriority() {
		return taskPriority;
	}

	public void setTaskPriority(TaskPriority taskPriority) {
		this.taskPriority = taskPriority;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getRescheduleCount() {
		return rescheduleCount;
	}

	public void setRescheduleCount(int rescheduleCount) {
		this.rescheduleCount = rescheduleCount;
	}

	public void incRescheduleCount() {
		this.rescheduleCount++;
	}

	public Task getParentTask() {
		return parentTask;
	}

	public void setParentTask(Task parentTask) {
		this.parentTask = parentTask;
	}

	public LocalDate getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(LocalDate startedAt) {
		this.startedAt = startedAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + taskId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (taskId != other.taskId)
			return false;
		return true;
	}

}
