package de.ubweb.taskplaner.task.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ScheduleContainer {
	private List<Task> tasks = new ArrayList<>();
	private LocalDate scheduledForDate;

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public LocalDate getScheduledForDate() {
		return scheduledForDate;
	}

	public void setScheduledForDate(LocalDate scheduledForDate) {
		this.scheduledForDate = scheduledForDate;
	}

	public void addTask(Task task) {
		tasks.add(task);

	}

	public void removeTask(Task task) {
		tasks.remove(task);

	}

}
