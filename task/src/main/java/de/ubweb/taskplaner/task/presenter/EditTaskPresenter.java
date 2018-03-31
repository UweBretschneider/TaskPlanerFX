package de.ubweb.taskplaner.task.presenter;

import java.net.URL;
import java.util.ResourceBundle;

import de.ubweb.taskplaner.task.controller.EditTaskController;
import de.ubweb.taskplaner.task.database.CategoryDAO;
import de.ubweb.taskplaner.task.model.Category;
import de.ubweb.taskplaner.task.model.Task;
import de.ubweb.taskplaner.task.model.TaskDifficulty;
import de.ubweb.taskplaner.task.model.TaskPriority;
import de.ubweb.taskplaner.task.model.TaskStatus;
import de.ubweb.taskplaner.task.model.TaskType;
import de.ubweb.taskplaner.task.view.EditTaskView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditTaskPresenter implements Initializable {
	private EditTaskController controller;
	private EditTaskView view;
	private Task task;

	@FXML
	private TextField titleField;
	@FXML
	private ComboBox<Category> categoryBox;
	@FXML
	private ComboBox<TaskType> taskTypeBox;
	@FXML
	private ComboBox<TaskStatus> statusBox;
	@FXML
	private DatePicker activeFromDatePicker;
	@FXML
	private DatePicker deadlineDatePicker;
	@FXML
	private ComboBox<TaskPriority> priorityBox;
	@FXML
	private ComboBox<TaskDifficulty> difficultyBox;
	@FXML
	private TextArea descriptionField;
	@FXML
	private DatePicker finishedAtPicker;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CategoryDAO categoryDao = new CategoryDAO();
		categoryBox.getItems().addAll(categoryDao.retrieveCategories());

		taskTypeBox.getItems().addAll(TaskType.values());
		priorityBox.getItems().addAll(TaskPriority.values());
		difficultyBox.getItems().addAll(TaskDifficulty.values());
		statusBox.getItems().addAll(TaskStatus.values());
	}

	public void setController(EditTaskController controller) {
		this.controller = controller;
	}

	public Task getTask() {
		task.setTitle(titleField.getText());
		task.setCategory(categoryBox.getSelectionModel().getSelectedItem());
		task.setTaskType(taskTypeBox.getSelectionModel().getSelectedItem());
		task.setActiveFrom(activeFromDatePicker.getValue());
		task.setDeadline(deadlineDatePicker.getValue());
		task.setTaskPriority(priorityBox.getSelectionModel().getSelectedItem());
		task.setTaskDifficulty(difficultyBox.getSelectionModel().getSelectedItem());
		task.setDescription(descriptionField.getText());
		task.setTaskStatus(statusBox.getSelectionModel().getSelectedItem());

		if (finishedAtPicker.getValue() != null) {
			task.setFinishedAt(finishedAtPicker.getValue());
		}

		return task;
	}

	public void setTask(Task task) {
		this.task = task;

		titleField.setText(task.getTitle());
		categoryBox.getSelectionModel().select(task.getCategory());
		taskTypeBox.getSelectionModel().select(task.getTaskType());
		activeFromDatePicker.setValue(task.getActiveFrom());
		deadlineDatePicker.setValue(task.getDeadline());
		priorityBox.getSelectionModel().select(task.getTaskPriority());
		difficultyBox.getSelectionModel().select(task.getTaskDifficulty());
		descriptionField.setText(task.getDescription());
		statusBox.getSelectionModel().select(task.getTaskStatus());

		if (task.getFinishedAt() != null) {
			finishedAtPicker.setValue(task.getFinishedAt());
		}
	}

	public EditTaskView getView() {
		return view;
	}

	public void setView(EditTaskView view) {
		this.view = view;
	}

	public void editTask(ActionEvent event) {
		controller.editSelectedTask(getTask());
	}
}
