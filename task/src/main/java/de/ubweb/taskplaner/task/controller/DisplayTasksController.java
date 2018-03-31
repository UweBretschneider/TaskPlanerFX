package de.ubweb.taskplaner.task.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Observable;
import java.util.Observer;

import de.ubweb.taskplaner.main.event.EventBus;
import de.ubweb.taskplaner.main.event.TaskEvent;
import de.ubweb.taskplaner.task.model.ScheduleContainer;
import de.ubweb.taskplaner.task.model.Task;
import de.ubweb.taskplaner.task.model.TaskManager;
import de.ubweb.taskplaner.task.model.TaskManagerUpdate;
import de.ubweb.taskplaner.task.model.TaskManagerUpdateType;
import de.ubweb.taskplaner.task.presenter.TasksPresenter;
import de.ubweb.taskplaner.task.view.SelectDateView;
import de.ubweb.taskplaner.task.view.TasksView;
import javafx.scene.Parent;

public class DisplayTasksController implements Observer {
	private TasksPresenter presenter;
	private TasksView view;

	private LocalDate scheduleDate = LocalDate.now();
	private ScheduleContainer scheduleContainer;

	private boolean selectedDateForScheduleContainer = true;
	private Task selectedTask = null;

	public DisplayTasksController() {
		view = new TasksView();
		presenter = view.getPresenter();
		presenter.setController(this);

		scheduleContainer = TaskManager.getInstance().getScheduledTasksFor(scheduleDate);
		presenter.setScheduleContainer(scheduleContainer);

		presenter.setRecentlyFinishedTasks(TaskManager.getInstance().getRecentlyFinishedTasks());
		presenter.setOpenTasks(TaskManager.getInstance().getOpenTasks());

		TaskManager.getInstance().addObserver(this);
	}

	public Parent getView() {
		return view.getView();
	}

	/*
	
	
	private void scheduleTaskForDate(Task task) {
		if (selectScheduleDateView != null) {
			selectScheduleDateView.getDisplayComponent().requestFocus();
		} else {
			isRescheduleMode = false;
			selectScheduleDateView = new SelectScheduleDateView(this, task);
			selectScheduleDateView.getDisplayComponent().setVisible(true);
		}
	}
	
	private void scheduleTaskForSelectedDate(Task task) {
		if (selectScheduleDateView != null) {
			if (isRescheduleMode) {
				TaskManager.getInstance().rescheduleTaskFor(selectScheduleDateView.getSelectedDate(), task);
			} else {
				TaskManager.getInstance().scheduleTaskFor(selectScheduleDateView.getSelectedDate(), task);
			}
	
			selectScheduleDateView.getDisplayComponent().dispose();
			selectScheduleDateView = null;
		}
	}
	
	private void rescheduleTask(Task task) {
		if (selectScheduleDateView != null) {
			selectScheduleDateView.getDisplayComponent().requestFocus();
		} else {
			isRescheduleMode = true;
			selectScheduleDateView = new SelectScheduleDateView(this, task);
			selectScheduleDateView.getDisplayComponent().setVisible(true);
		}
	}
	
	private void finishTask(Task task) {
		TaskManager.getInstance().finishTask(task);
	}
	
	private void markTaskAsFailed(Task task) {
		TaskManager.getInstance().markTaskAsFailed(task);
	}
	*/
	public void plusOneDay() {
		scheduleDate = scheduleDate.plus(1, ChronoUnit.DAYS);
		scheduleContainer = TaskManager.getInstance().getScheduledTasksFor(scheduleDate);
		presenter.setScheduleContainer(scheduleContainer);
	}

	public void minusOneDay() {
		scheduleDate = scheduleDate.minus(1, ChronoUnit.DAYS);
		scheduleContainer = TaskManager.getInstance().getScheduledTasksFor(scheduleDate);
		presenter.setScheduleContainer(scheduleContainer);
	}

	public void selectDate() {
		selectedDateForScheduleContainer = true;

		SelectDateView selectView = new SelectDateView();
		selectView.getPresenter().setController(this);
	}

	public void selectDate(LocalDate date) {
		if (selectedDateForScheduleContainer) {
			scheduleDate = date;
			scheduleContainer = TaskManager.getInstance().getScheduledTasksFor(scheduleDate);
			presenter.setScheduleContainer(scheduleContainer);
		} else {
			TaskManager.getInstance().scheduleTaskFor(selectedTask, date);

			selectedTask = null;
		}

	}

	public void scheduleTaskForToday(Task task) {
		TaskManager.getInstance().scheduleTaskFor(LocalDate.now(), task);
	}

	public void markTaskAsFinished(Task task) {
		TaskManager.getInstance().finishTask(task);
	}

	public void markTaskAsFailed(Task task) {
		TaskManager.getInstance().markTaskAsFailed(task);
	}

	public void editTask(Task task) {
		TaskEvent taskEvent = new TaskEvent(TaskEvent.EDIT_SELECTED_TASK);
		taskEvent.setSelectedTask(task);
		EventBus.getInstance().deliverEvent(taskEvent);
	}

	public void scheduleTaskFor(Task task) {
		selectedDateForScheduleContainer = false;
		selectedTask = task;

		SelectDateView selectView = new SelectDateView();
		selectView.getPresenter().setController(this);
	}

	public void rescheduleTask(Task task) {
		TaskManager.getInstance().rescheduleTask(task);
		/*
		if (selectScheduleDateView != null) {
			selectScheduleDateView.getDisplayComponent().requestFocus();
		} else {
			isRescheduleMode = true;
			selectScheduleDateView = new SelectScheduleDateView(this, task);
			selectScheduleDateView.getDisplayComponent().setVisible(true);
		}
		*/
	}

	public void markAsStartedButUnfinished(Task task) {
		TaskManager.getInstance().markAsStartedButUnfinished(task);
	}

	@Override
	public void update(Observable o, Object arg) {
		scheduleContainer = TaskManager.getInstance().getScheduledTasksFor(scheduleDate);
		presenter.setScheduleContainer(scheduleContainer);
		presenter.setOpenTasks(TaskManager.getInstance().getOpenTasks());

		if (arg != null && arg instanceof TaskManagerUpdate) {
			TaskManagerUpdate update = (TaskManagerUpdate) arg;
			if (update.getUpdateType().equals(TaskManagerUpdateType.TASK_FINISHED)) {
				presenter.addRecentlyFinishedTask(update.getTask());
			} else {

			}
		} else {
			presenter.setRecentlyFinishedTasks(TaskManager.getInstance().getRecentlyFinishedTasks());
		}

	}

}
