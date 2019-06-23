package de.ubweb.taskplaner.task.presenter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import de.ubweb.taskplaner.task.controller.DisplayTasksController;
import de.ubweb.taskplaner.task.model.ScheduleContainer;
import de.ubweb.taskplaner.task.model.SolutionQuality;
import de.ubweb.taskplaner.task.model.SolutionTime;
import de.ubweb.taskplaner.task.model.Task;
import de.ubweb.taskplaner.task.model.TaskStatus;
import de.ubweb.taskplaner.task.view.TaskTableWrapper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

public class TasksPresenter implements Initializable {
	private ObservableList<Task> openTasks;
	private ObservableList<TaskTableWrapper> recentlyFinishedTasks;
	private ObservableList<Task> scheduledTasks;

	private ScheduleContainer scheduleContainer;

	private DisplayTasksController controller;

	@FXML
	private TableView<Task> openTasksTableView;
	@FXML
	private TableColumn<Task, Integer> oTIdColumn;
	@FXML
	private TableColumn<Task, StringProperty> oTNameColumn;
	@FXML
	private TableColumn<Task, LocalDate> oTScheduledForColumn;
	@FXML
	private TableColumn<Task, Integer> oTRescheduleCountColumn;
	@FXML
	private TableColumn<Task, LocalDate> oTStartedAtColumn;

	@FXML
	private Label dateLabel;
	@FXML
	private TableView<Task> scheduledTasksTableView;
	@FXML
	private TableColumn<Task, Integer> sTIdColumn;
	@FXML
	private TableColumn<Task, StringProperty> sTNameColumn;
	@FXML
	private TableColumn<Task, LocalDate> sTScheduledForColumn;
	@FXML
	private TableColumn<Task, Integer> sTRescheduleCountColumn;

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
	private TableColumn<TaskTableWrapper, SolutionQuality> fTQualityColumn;
	@FXML
	private TableColumn<TaskTableWrapper, SolutionTime> fTTimeColumn;

	public TasksPresenter() {

	}

	public void setScheduleContainer(ScheduleContainer scheduleContainer) {
		this.scheduleContainer = scheduleContainer;

		scheduledTasks = FXCollections.observableArrayList(scheduleContainer.getTasks());
		scheduledTasksTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		scheduledTasksTableView.getItems().clear();
		scheduledTasksTableView.setItems(scheduledTasks);

		dateLabel.setText("" + scheduleContainer.getScheduledForDate());
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

		finishedTasksTableView.setRowFactory(tv -> new TableRow<TaskTableWrapper>() {
			@Override
			protected void updateItem(TaskTableWrapper item, boolean empty) {
				super.updateItem(item, empty);

				if (item != null && !empty && item.isHasChanges()) {
					Timeline flasher = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
						if (item.getTaskStatus().equals(TaskStatus.SOLVED)) {
							setStyle("-fx-background-color: green");
						} else if (item.getTaskStatus().equals(TaskStatus.CANCELED)) {
							setStyle("-fx-background-color: yellow");
						} else if (item.getTaskStatus().equals(TaskStatus.FAILED)) {
							setStyle("-fx-background-color: red");
						}

					}), new KeyFrame(Duration.seconds(1.0), e -> {
						setStyle("");
					}));
					flasher.setCycleCount(3);
					flasher.play();
					flasher.setOnFinished(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							item.setHasChanges(false);
						}
					});
				}
			}
		});

	}

	public void setOpenTasks(List<Task> openTasks) {
		this.openTasks = FXCollections.observableArrayList(openTasks);

		openTasksTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		openTasksTableView.getItems().clear();
		openTasksTableView.setItems(this.openTasks);
	}

	public DisplayTasksController getController() {
		return controller;
	}

	public void setController(DisplayTasksController controller) {
		this.controller = controller;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sTIdColumn.setCellValueFactory(new PropertyValueFactory<>("taskId"));
		sTNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		sTScheduledForColumn.setCellValueFactory(new PropertyValueFactory<>("scheduledFor"));
		sTRescheduleCountColumn.setCellValueFactory(new PropertyValueFactory<>("rescheduleCount"));

		fTIdColumn.setCellValueFactory(new PropertyValueFactory<>("taskId"));
		fTNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		fTFinishedAtColumn.setCellValueFactory(new PropertyValueFactory<>("finishedAt"));
		fTStatusColumn.setCellValueFactory(new PropertyValueFactory<>("taskStatus"));
		
		fTQualityColumn.setCellValueFactory(new PropertyValueFactory<>("solutionQuality"));
		fTTimeColumn.setCellValueFactory(new PropertyValueFactory<>("solutionTime"));

		oTIdColumn.setCellValueFactory(new PropertyValueFactory<>("taskId"));
		oTNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		oTScheduledForColumn.setCellValueFactory(new PropertyValueFactory<>("scheduledFor"));
		oTRescheduleCountColumn.setCellValueFactory(new PropertyValueFactory<>("rescheduleCount"));
		oTStartedAtColumn.setCellValueFactory(new PropertyValueFactory<>("startedAt"));

		oTScheduledForColumn.setCellFactory(column -> {
			return new TableCell<Task, LocalDate>() {
				@Override
				protected void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						// Format date.
						setText(item.toString());

						// Style all dates in March with a different color.
						if (item.isBefore(LocalDate.now())) {
							setStyle("-fx-background-color: red");
						} else {
							setStyle("");
						}
					}
				}
			};
		});
	}

	public void addRecentlyFinishedTask(Task task) {
		TaskTableWrapper tableWrapper = new TaskTableWrapper(task);
		tableWrapper.setHasChanges(true);
		recentlyFinishedTasks.add(0, tableWrapper);
		recentlyFinishedTasks.remove(recentlyFinishedTasks.size() - 1);

		finishedTasksTableView.refresh();
		/*
		ObservableList<TaskTableWrapper> temp = FXCollections.observableArrayList(recentlyFinishedTasks);
		
		recentlyFinishedTasks.removeAll(recentlyFinishedTasks);
		recentlyFinishedTasks.addAll(temp);
		*/
	}

	public void minusOneDay(ActionEvent event) {
		controller.minusOneDay();
	}

	public void selectDate(ActionEvent event) {
		controller.selectDate();
	}

	public void plusOneDay(ActionEvent event) {
		controller.plusOneDay();
	}

	public void editSelectedOpenTask(ActionEvent event) {
		Task selectedTask = openTasksTableView.getSelectionModel().getSelectedItem();
		if (selectedTask != null) {
			controller.editTask(selectedTask);
		}
	}

	public void editSelectedClosedTask(ActionEvent event) {
		Task selectedTask = finishedTasksTableView.getSelectionModel().getSelectedItem().getTask();
		if (selectedTask != null) {
			controller.editTask(selectedTask);
		}
	}

	public void scheduleSelectedOpenTaskToday(ActionEvent event) {
		Task selectedTask = openTasksTableView.getSelectionModel().getSelectedItem();
		if (selectedTask != null) {
			controller.scheduleTaskForToday(selectedTask);
		}
	}

	public void scheduleSelectedOpenTaskFor(ActionEvent event) {
		Task selectedTask = openTasksTableView.getSelectionModel().getSelectedItem();
		if (selectedTask != null) {
			controller.scheduleTaskFor(selectedTask);
		}
	}

	public void markTaskAsFinished(ActionEvent event) {
		Task selectedTask = scheduledTasksTableView.getSelectionModel().getSelectedItem();
		if (selectedTask != null) {
			controller.markTaskAsFinished(selectedTask);
		}
	}

	public void markTaskAsFailed(ActionEvent event) {
		Task selectedTask = scheduledTasksTableView.getSelectionModel().getSelectedItem();
		if (selectedTask != null) {
			controller.markTaskAsFailed(selectedTask);
		}
	}

	public void rescheduleTask(ActionEvent event) {
		Task selectedTask = scheduledTasksTableView.getSelectionModel().getSelectedItem();
		if (selectedTask != null) {
			controller.rescheduleTask(selectedTask);
		}
	}

	public void markAsStartedButUnfinished(ActionEvent event) {
		Task selectedTask = scheduledTasksTableView.getSelectionModel().getSelectedItem();
		if (selectedTask != null) {
			controller.markAsStartedButUnfinished(selectedTask);
		}
	}

}
