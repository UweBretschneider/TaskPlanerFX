package de.ubweb.taskplaner.results.presenter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import de.ubweb.taskplaner.results.controller.DisplayResultsController;
import de.ubweb.taskplaner.task.model.Task;
import de.ubweb.taskplaner.task.model.TaskStatus;
import de.ubweb.taskplaner.task.view.TaskTableWrapper;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ResultsPresenter implements Initializable {
	private ObservableList<TaskTableWrapper> recentlyFinishedTasks;
	private DisplayResultsController controller;

	@FXML
	private TableView<TaskTableWrapper> finishedTasksTableView;
	@FXML
	private TableColumn<TaskTableWrapper, Integer> fTIdColumn;
	@FXML
	private TableColumn<TaskTableWrapper, StringProperty> fTNameColumn;
	@FXML
	private TableColumn<TaskTableWrapper, LocalDate> fTFinishedAtColumn;
	@FXML
	private TableColumn<TaskTableWrapper, TaskStatus> fTStatusColumn;

	@FXML
	private ProgressBar successRatioBar;
	@FXML
	private TextField successfullFinishedField;

	public ResultsPresenter() {

	}

	public void setRecentlyFinishedTasks(List<Task> recentlyFinishedTasks) {
		List<TaskTableWrapper> taskTableWrappers = new ArrayList<>(recentlyFinishedTasks.size());
		for (Task task : recentlyFinishedTasks) {
			taskTableWrappers.add(new TaskTableWrapper(task));
		}

		this.recentlyFinishedTasks = FXCollections.observableArrayList(taskTableWrappers);

		finishedTasksTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		finishedTasksTableView.getItems().clear();
		finishedTasksTableView.setItems(this.recentlyFinishedTasks);
	}

	public void setSuccessRatio(double successRatio) {
		successRatioBar.setProgress(successRatio);
	}

	public void setSuccessfullAmount(int nrOfSuccessfull) {
		successfullFinishedField.setText("" + nrOfSuccessfull);
	}

	public DisplayResultsController getController() {
		return controller;
	}

	public void setController(DisplayResultsController controller) {
		this.controller = controller;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fTIdColumn.setCellValueFactory(new PropertyValueFactory<>("taskId"));
		fTNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		fTFinishedAtColumn.setCellValueFactory(new PropertyValueFactory<>("finishedAt"));
		fTStatusColumn.setCellValueFactory(new PropertyValueFactory<>("taskStatus"));
	}

}
