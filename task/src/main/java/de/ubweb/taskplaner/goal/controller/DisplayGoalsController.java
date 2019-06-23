package de.ubweb.taskplaner.goal.controller;

import de.ubweb.taskplaner.goal.database.GoalDAO;
import de.ubweb.taskplaner.goal.model.Goal;
import de.ubweb.taskplaner.goal.model.GoalManager;
import de.ubweb.taskplaner.goal.presenter.GoalsPresenter;
import de.ubweb.taskplaner.goal.view.GoalsView;
import de.ubweb.taskplaner.main.event.EventBus;
import de.ubweb.taskplaner.main.event.GoalEvent;
import javafx.scene.Parent;

public class DisplayGoalsController {
	private GoalsView view;
	private GoalsPresenter presenter;

	public void displayProjects() {
		view = new GoalsView();
		presenter = view.getPresenter();
		presenter.setController(this);

		presenter.setGoals(GoalManager.getInstance().getGoals());
	}

	public Parent getView() {
		return view.getView();
	}

	public void displayGoal(Goal model) {
		GoalEvent event = new GoalEvent(GoalEvent.DISPLAY_GOAL_DETAILS);
		event.setSelectedGoal(model);

		EventBus.getInstance().deliverEvent(event);
	}

}
