package de.ubweb.taskplaner.main.event;

public class ResultEvent extends CustomEvent {
	public static final int DISPLAY_RESULTS = 1;

	public ResultEvent(int action) {
		super(action);
	}

}
