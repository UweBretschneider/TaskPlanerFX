package de.ubweb.taskplaner.goal.controller;

import de.ubweb.taskplaner.goal.model.Goal;
import de.ubweb.taskplaner.main.application.TaskPlanerFX;
import de.ubweb.taskplaner.main.event.CustomEvent;
import de.ubweb.taskplaner.main.event.GoalEvent;
import de.ubweb.taskplaner.main.event.IService;
import de.ubweb.taskplaner.project.controller.ProjectController;
import de.ubweb.taskplaner.project.model.Project;

public class GoalService implements IService {

	public void displayGoals() {
		DisplayGoalsController controller = new DisplayGoalsController();
		controller.displayProjects();
		TaskPlanerFX.getInstance().addTab(controller.getView(), "Ziele", false);
	}
	
	public void displayGoalDetails(Goal goal) {
		GoalController controller = new GoalController();
		controller.displayGoal(goal);
		TaskPlanerFX.getInstance().addTab(controller.getView(), "Zieldetails: " + goal.getGoalId(), true);
	}

	@Override
	public void handleEvent(CustomEvent event) {
		if (event instanceof GoalEvent) {
			GoalEvent goalEvent = (GoalEvent) event;
			switch (event.getAction()) {
			case GoalEvent.DISPLAY_GOALS:
				displayGoals();
				break;
			case GoalEvent.DISPLAY_GOAL_DETAILS:
				displayGoalDetails(goalEvent.getSelectedGoal());
				break;
			}
		}

	}

}
