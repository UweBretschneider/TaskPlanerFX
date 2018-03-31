package de.ubweb.taskplaner.task.model;

public enum TaskPriority {
	NORMAL(1), CRITICAL(2), OPTIONAL(3);

	private int statusCode;

	private TaskPriority(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public static TaskPriority convertStatusCode(int statusCode) {
		switch (statusCode) {
		case 1:
			return NORMAL;
		case 2:
			return CRITICAL;
		case 3:
			return OPTIONAL;
		default:
			return null;
		}
	}
}
