package de.ubweb.taskplaner.task.model;

public enum TaskType {
	ATOMIC_TASK(1), REPEATABLE_TASK(2);

	private int statusCode;

	private TaskType(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public static TaskType convertStatusCode(int statusCode) {
		switch (statusCode) {
		case 1:
			return ATOMIC_TASK;
		case 2:
			return REPEATABLE_TASK;
		default:
			return null;
		}
	}
}
