package de.ubweb.taskplaner.goal.view;

import java.io.IOException;

import de.ubweb.taskplaner.goal.presenter.GoalPresenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class GoalDetailView {
	private Parent rootView;
	private GoalPresenter presenter;

	public GoalDetailView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/ubweb/taskplaner/goal/view/goal_detail_view.fxml"));
			rootView = loader.load();
			presenter = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Parent getView() {
		return rootView;
	}

	public GoalPresenter getPresenter() {
		return presenter;
	}
}
