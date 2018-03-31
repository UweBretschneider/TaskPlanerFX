package de.ubweb.taskplaner.task.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import de.ubweb.taskplaner.task.database.TaskDAO;

public class TaskManager extends Observable {
	private static volatile TaskManager ONLY_INSTANCE;

	private Map<LocalDate, ScheduleContainer> scheduledTaskMap = new HashMap<>();

	private List<Task> openTasks;
	private List<Task> recentlyFinishedTasks;

	private TaskDAO taskDao = new TaskDAO();

	private TaskManager() {
		openTasks = taskDao.retrieveOpenTasks();

		for (Task openTask : openTasks) {
			if (openTask.getScheduledFor() != null) {
				if (scheduledTaskMap.containsKey(openTask.getScheduledFor())) {
					scheduledTaskMap.get(openTask.getScheduledFor()).addTask(openTask);
				} else {
					ScheduleContainer container = new ScheduleContainer();
					container.setScheduledForDate(openTask.getScheduledFor());
					container.addTask(openTask);
					scheduledTaskMap.put(openTask.getScheduledFor(), container);
				}
			}
		}

		recentlyFinishedTasks = taskDao.retrieveRecentlyFinished(5);
	}

	public static TaskManager getInstance() {
		if (ONLY_INSTANCE == null) {
			synchronized (TaskManager.class) {
				if (ONLY_INSTANCE == null) {
					ONLY_INSTANCE = new TaskManager();
				}
			}
		}
		return ONLY_INSTANCE;
	}

	public List<Task> getOpenTasks() {
		return openTasks;
	}

	public ScheduleContainer getScheduledTasksFor(LocalDate date) {
		if (scheduledTaskMap.containsKey(date)) {
			return scheduledTaskMap.get(date);
		} else {
			ScheduleContainer container = new ScheduleContainer();
			container.setScheduledForDate(date);
			scheduledTaskMap.put(date, container);

			return container;
		}
	}

	public void scheduleTaskFor(LocalDate date, Task task) {
		//is this task already scheduled?
		if (task.getScheduledFor() != null) {
			if (date.isEqual(task.getScheduledFor())) {
				//nothing to do...
				return;
			} else {
				scheduledTaskMap.get(task.getScheduledFor()).removeTask(task);
			}
		}

		task.setScheduledFor(date);
		taskDao.update(task);

		if (scheduledTaskMap.containsKey(date)) {
			scheduledTaskMap.get(date).addTask(task);
		} else {
			ScheduleContainer container = new ScheduleContainer();
			container.setScheduledForDate(date);
			container.addTask(task);
			scheduledTaskMap.put(date, container);
		}

		setChanged();
		notifyObservers();
	}

	public void rescheduleTaskFor(LocalDate date, Task task) {
		//is this task already scheduled?
		if (task.getScheduledFor() != null) {
			if (date.isEqual(task.getScheduledFor())) {
				//nothing to do...
				return;
			} else {
				scheduledTaskMap.get(task.getScheduledFor()).removeTask(task);
			}
		}

		task.setScheduledFor(date);
		task.incRescheduleCount();
		taskDao.update(task);

		if (scheduledTaskMap.containsKey(date)) {
			scheduledTaskMap.get(date).addTask(task);
		} else {
			ScheduleContainer container = new ScheduleContainer();
			container.setScheduledForDate(date);
			container.addTask(task);
			scheduledTaskMap.put(date, container);
		}

		setChanged();
		notifyObservers();

	}

	public List<Task> getRecentlyFinishedTasks() {
		return recentlyFinishedTasks;
	}

	public void createTask(Task task) {
		taskDao.create(task);
		if (task.getTaskStatus().equals(TaskStatus.CREATED)) {
			openTasks.add(task);
		} else {
			recentlyFinishedTasks = taskDao.retrieveRecentlyFinished(5);
		}

		setChanged();
		notifyObservers();

	}

	public void updateTask(Task task) {
		taskDao.update(task);

		setChanged();
		notifyObservers();
	}

	public void deleteTask(Task task) {
		openTasks.remove(task);
		taskDao.delete(task);

		setChanged();
		notifyObservers();

	}

	public void finishTask(Task task) {
		task.setTaskStatus(TaskStatus.SOLVED);
		task.setFinishedAt(LocalDate.now());
		taskDao.update(task);

		scheduledTaskMap.get(task.getScheduledFor()).removeTask(task);
		openTasks.remove(task);

		recentlyFinishedTasks.add(0, task);
		recentlyFinishedTasks.remove(recentlyFinishedTasks.size() - 1);

		TaskManagerUpdate update = new TaskManagerUpdate();
		update.setTask(task);
		update.setUpdateType(TaskManagerUpdateType.TASK_FINISHED);

		setChanged();
		notifyObservers(update);
	}

	public void markTaskAsFailed(Task task) {
		task.setTaskStatus(TaskStatus.FAILED);
		task.setFinishedAt(LocalDate.now());
		taskDao.update(task);

		scheduledTaskMap.get(task.getScheduledFor()).removeTask(task);
		openTasks.remove(task);

		recentlyFinishedTasks = taskDao.retrieveRecentlyFinished(5);

		setChanged();
		notifyObservers();

	}

	public void rescheduleTask(Task task) {
		task.setTaskStatus(TaskStatus.CREATED);
		task.incRescheduleCount();

		scheduledTaskMap.get(task.getScheduledFor()).removeTask(task);
		task.setScheduledFor(null);
		openTasks.add(task);

		taskDao.update(task);

		setChanged();
		notifyObservers();
	}

	public void scheduleTaskFor(Task task, LocalDate date) {
		task.setScheduledFor(date);

		if (scheduledTaskMap.containsKey(date)) {
			scheduledTaskMap.get(date).addTask(task);
		} else {
			ScheduleContainer container = new ScheduleContainer();
			container.setScheduledForDate(date);
			container.addTask(task);
			scheduledTaskMap.put(date, container);
		}

		taskDao.update(task);
		setChanged();
		notifyObservers();
	}

	public void markAsStartedButUnfinished(Task task) {
		task.setStartedAt(LocalDate.now());

		scheduledTaskMap.get(task.getScheduledFor()).removeTask(task);
		task.setScheduledFor(task.getScheduledFor().plus(1, ChronoUnit.DAYS));

		taskDao.update(task);

		if (scheduledTaskMap.containsKey(task.getScheduledFor())) {
			scheduledTaskMap.get(task.getScheduledFor()).addTask(task);
		} else {
			ScheduleContainer container = new ScheduleContainer();
			container.setScheduledForDate(task.getScheduledFor());
			container.addTask(task);
			scheduledTaskMap.put(task.getScheduledFor(), container);
		}

		setChanged();
		notifyObservers();
	}

	public double calculateSuccessRatio(int days) {
		List<Task> tasksLastNDays = taskDao.retrieveRecentlyFinishedByDay(days);
		int finished = 0;
		int unfinished = 0;
		for (Task task : tasksLastNDays) {
			switch (task.getTaskStatus()) {
			case CANCELED:
			case TIMED_OUT:
			case FAILED:
				unfinished++;
				break;
			case SOLVED:
				finished++;
				break;
			default:
				break;
			}
		}

		double ratio = (double) finished / (finished + unfinished);

		return ratio;
	}

	public int calculateNrOfSuccessfull(int days) {
		List<Task> tasksLastNDays = taskDao.retrieveRecentlyFinishedByDay(days);
		int finished = 0;
		for (Task task : tasksLastNDays) {
			switch (task.getTaskStatus()) {
			case CANCELED:
			case TIMED_OUT:
			case FAILED:
				break;
			case SOLVED:
				finished++;
				break;
			default:
				break;
			}
		}

		return finished;
	}

}
