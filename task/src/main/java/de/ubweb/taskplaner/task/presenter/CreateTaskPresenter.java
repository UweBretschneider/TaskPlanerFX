package de.ubweb.taskplaner.task.presenter;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import de.ubweb.taskplaner.goal.database.GoalDAO;
import de.ubweb.taskplaner.goal.model.Goal;
import de.ubweb.taskplaner.task.controller.CreateTaskController;
import de.ubweb.taskplaner.task.database.CategoryDAO;
import de.ubweb.taskplaner.task.model.Category;
import de.ubweb.taskplaner.task.model.Task;
import de.ubweb.taskplaner.task.model.TaskDifficulty;
import de.ubweb.taskplaner.task.model.TaskPriority;
import de.ubweb.taskplaner.task.model.TaskStatus;
import de.ubweb.taskplaner.task.model.TaskType;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateTaskPresenter implements Initializable {
	private static final int NULL_OBJECT_ID = -99;

	private CreateTaskController controller;

	@FXML
	private TextField titleField;
	@FXML
	private ComboBox<Category> categoryBox;
	@FXML
	private ComboBox<Goal> goalBox;
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

	public void createTask(ActionEvent event) {
		Task task = new Task();
		//this view only supports simple tasks
		task.setTaskType(TaskType.ATOMIC_TASK);

		task.setTitle(titleField.getText());
		task.setCategory(categoryBox.getSelectionModel().getSelectedItem());
		task.setActiveFrom(activeFromDatePicker.getValue());
		task.setDeadline(deadlineDatePicker.getValue());
		task.setTaskPriority(priorityBox.getSelectionModel().getSelectedItem());
		task.setTaskDifficulty(difficultyBox.getSelectionModel().getSelectedItem());
		task.setDescription(descriptionField.getText());

		task.setCreatedAt(LocalDate.now());
		task.setTaskStatus(TaskStatus.CREATED);

		controller.createSelectedTask(task);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CategoryDAO categoryDao = new CategoryDAO();
		categoryBox.setItems(FXCollections.observableList(categoryDao.retrieveCategories()));
		categoryBox.getSelectionModel().select(0);

		GoalDAO goalDao = new GoalDAO();
		List<Goal> goals = goalDao.retrieveAll();
		//create a special null selection item
		Goal nullGoal = new Goal();
		nullGoal.setGoalId(NULL_OBJECT_ID);
		nullGoal.setTitle("(Kein Ziel)");
		goals.add(0, nullGoal);
		goalBox.setItems(FXCollections.observableList(goals));
		goalBox.getSelectionModel().select(0);

		priorityBox.getItems().addAll(TaskPriority.values());
		priorityBox.getSelectionModel().select(1);
		difficultyBox.getItems().addAll(TaskDifficulty.values());
		difficultyBox.getSelectionModel().select(1);

		activeFromDatePicker.setValue(LocalDate.now());
		deadlineDatePicker.setValue(LocalDate.now().plusDays(14));
	}

	public void setController(CreateTaskController controller) {
		this.controller = controller;
	}

}
