package de.ubweb.taskplaner.project.model;

public enum ProjectStatus {
	CREATED(1), FINISHED(2), FAILED(3);

	private int statusCode;

	private ProjectStatus(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public static ProjectStatus convertStatusCode(int statusCode) {
		switch (statusCode) {
		case 1:
			return CREATED;
		case 2:
			return FINISHED;
		case 3:
			return FAILED;
		default:
			return null;
		}
	}
}
