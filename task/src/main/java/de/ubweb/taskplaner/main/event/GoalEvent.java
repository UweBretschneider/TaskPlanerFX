package de.ubweb.taskplaner.main.event;

import de.ubweb.taskplaner.goal.model.Goal;

public class GoalEvent extends CustomEvent {
	public static final int DISPLAY_GOALS = 1;
	public static final int DISPLAY_GOAL_DETAILS = 10;
	
	private Goal selectedGoal;

	public GoalEvent(int action) {
		super(action);
	}

	public Goal getSelectedGoal() {
		return selectedGoal;
	}

	public void setSelectedGoal(Goal selectedGoal) {
		this.selectedGoal = selectedGoal;
	}

	
}
