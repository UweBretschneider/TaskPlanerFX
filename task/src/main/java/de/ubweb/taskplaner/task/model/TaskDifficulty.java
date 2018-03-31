package de.ubweb.taskplaner.task.model;

public enum TaskDifficulty {
	EASY(1), MEDIUM(2), HARD(3);

	private int statusCode;

	private TaskDifficulty(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public static TaskDifficulty convertStatusCode(int statusCode) {
		switch (statusCode) {
		case 1:
			return EASY;
		case 2:
			return MEDIUM;
		case 3:
			return HARD;
		default:
			return null;
		}
	}
}
