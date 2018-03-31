package de.ubweb.taskplaner.task.view;

import java.io.IOException;

import de.ubweb.taskplaner.task.presenter.SelectTaskPresenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SelectTaskView {
	private Parent rootView;
	private SelectTaskPresenter presenter;

	private Stage stage;

	public SelectTaskView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/ubweb/taskplaner/task/view/select_task_view.fxml"));
			rootView = loader.load();
			presenter = loader.getController();

			presenter.setView(this);

			stage = new Stage();
			stage.setTitle("Task auswählen");
			stage.setScene(new Scene(rootView, 800, 600));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Parent getView() {
		return rootView;
	}

	public void closeStage() {
		stage.close();
	}

	public SelectTaskPresenter getPresenter() {
		return presenter;
	}
}
