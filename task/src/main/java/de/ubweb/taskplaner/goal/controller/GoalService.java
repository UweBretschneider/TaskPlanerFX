package de.ubweb.taskplaner.goal.controller;

import de.ubweb.taskplaner.main.application.TaskPlanerFX;
import de.ubweb.taskplaner.main.event.CustomEvent;
import de.ubweb.taskplaner.main.event.GoalEvent;
import de.ubweb.taskplaner.main.event.IService;

public class GoalService implements IService {

	public void displayGoals() {
		DisplayGoalsController controller = new DisplayGoalsController();
		controller.displayProjects();
		TaskPlanerFX.getInstance().addTab(controller.getView(), "Ziele", false);
	}

	@Override
	public void handleEvent(CustomEvent event) {
		if (event instanceof GoalEvent) {
			GoalEvent goalEvent = (GoalEvent) event;
			switch (event.getAction()) {
			case GoalEvent.DISPLAY_GOALS:
				displayGoals();
				break;
			}
		}

	}

}
