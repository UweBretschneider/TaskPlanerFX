package de.ubweb.taskplaner.goal.controller;

import de.ubweb.taskplaner.goal.database.GoalDAO;
import de.ubweb.taskplaner.goal.presenter.GoalsPresenter;
import de.ubweb.taskplaner.goal.view.GoalsView;
import javafx.scene.Parent;

public class DisplayGoalsController {
	private GoalsView view;
	private GoalsPresenter presenter;
	private GoalDAO goalDao = new GoalDAO();

	public void displayProjects() {
		view = new GoalsView();
		presenter = view.getPresenter();
		presenter.setController(this);

		presenter.setGoals(goalDao.retrieveAll());
	}

	public Parent getView() {
		return view.getView();
	}

}
