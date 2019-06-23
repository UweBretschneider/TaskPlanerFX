package de.ubweb.taskplaner.task.model;

public enum SolutionQuality {
	NO_SOLUTION(0), LOW_QUALITY(1), MID_QUALITY(2), HIGH_QUALITY(3);

	private int statusCode;

	private SolutionQuality(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public static SolutionQuality convertStatusCode(int statusCode) {
		switch (statusCode) {
		case 0:
			return NO_SOLUTION;
		case 1:
			return LOW_QUALITY;
		case 2:
			return MID_QUALITY;
		case 3:
			return HIGH_QUALITY;
		default:
			return null;
		}
	}
}
