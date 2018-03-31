package de.ubweb.taskplaner.task.model;

public enum TaskStatus {
	CREATED(1), SOLVED(2), FAILED(3), TIMED_OUT(4), CANCELED(5);

	private int statusCode;

	private TaskStatus(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public static TaskStatus convertStatusCode(int statusCode) {
		switch (statusCode) {
		case 1:
			return CREATED;
		case 2:
			return SOLVED;
		case 3:
			return FAILED;
		case 4:
			return TIMED_OUT;
		case 5:
			return CANCELED;
		default:
			return null;
		}
	}
}
