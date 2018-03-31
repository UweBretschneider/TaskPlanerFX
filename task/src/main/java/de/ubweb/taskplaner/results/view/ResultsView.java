package de.ubweb.taskplaner.results.view;

import java.io.IOException;

import de.ubweb.taskplaner.results.presenter.ResultsPresenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ResultsView {
	private Parent rootView;
	private ResultsPresenter presenter;

	public ResultsView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/ubweb/taskplaner/results/view/results_view.fxml"));
			rootView = loader.load();
			presenter = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Parent getView() {
		return rootView;
	}

	public ResultsPresenter getPresenter() {
		return presenter;
	}

}
