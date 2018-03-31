package de.ubweb.taskplaner.results.controller;

import java.util.Observable;
import java.util.Observer;

import de.ubweb.taskplaner.results.presenter.ResultsPresenter;
import de.ubweb.taskplaner.results.view.ResultsView;
import de.ubweb.taskplaner.task.model.TaskManager;
import javafx.scene.Parent;

public class DisplayResultsController implements Observer {
	private ResultsPresenter presenter;
	private ResultsView view;

	public DisplayResultsController() {
		view = new ResultsView();
		presenter = view.getPresenter();
		presenter.setController(this);

		presenter.setRecentlyFinishedTasks(TaskManager.getInstance().getRecentlyFinishedTasks());
		presenter.setSuccessRatio(TaskManager.getInstance().calculateSuccessRatio(30));
		presenter.setSuccessfullAmount(TaskManager.getInstance().calculateNrOfSuccessfull(30));

		TaskManager.getInstance().addObserver(this);
	}

	public Parent getView() {
		return view.getView();
	}

	@Override
	public void update(Observable o, Object arg) {
		presenter.setRecentlyFinishedTasks(TaskManager.getInstance().getRecentlyFinishedTasks());
	}

}
