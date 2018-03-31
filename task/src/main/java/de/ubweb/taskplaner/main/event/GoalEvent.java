package de.ubweb.taskplaner.main.event;

public class GoalEvent extends CustomEvent {
	public static final int DISPLAY_GOALS = 1;

	public GoalEvent(int action) {
		super(action);
	}

}
