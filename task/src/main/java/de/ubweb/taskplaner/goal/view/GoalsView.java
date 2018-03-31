package de.ubweb.taskplaner.goal.view;

import java.io.IOException;

import de.ubweb.taskplaner.goal.presenter.GoalsPresenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class GoalsView {
	private Parent rootView;
	private GoalsPresenter presenter;

	public GoalsView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/ubweb/taskplaner/goal/view/goals_view.fxml"));
			rootView = loader.load();
			presenter = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Parent getView() {
		return rootView;
	}

	public GoalsPresenter getPresenter() {
		return presenter;
	}
}
