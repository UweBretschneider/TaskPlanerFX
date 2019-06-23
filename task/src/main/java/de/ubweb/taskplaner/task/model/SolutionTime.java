package de.ubweb.taskplaner.task.model;

public enum SolutionTime {
	NO_SOLUTION(0), SLOW(1), MEDIUM(2), FAST(3);

	private int statusCode;

	private SolutionTime(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public static SolutionTime convertStatusCode(int statusCode) {
		switch (statusCode) {
		case 0:
			return NO_SOLUTION;
		case 1:
			return SLOW;
		case 2:
			return MEDIUM;
		case 3:
			return FAST;
		default:
			return null;
		}
	}
}
