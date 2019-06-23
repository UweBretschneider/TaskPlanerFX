package de.ubweb.taskplaner.goal.model;

import java.util.List;
import java.util.Observable;

import de.ubweb.taskplaner.goal.database.GoalDAO;
import de.ubweb.taskplaner.project.database.ProjectDAO;
import de.ubweb.taskplaner.task.model.Task;

public class GoalManager extends Observable {
	private static volatile GoalManager ONLY_INSTANCE;

	private GoalDAO goalDao = new GoalDAO();
	private List<Goal> goals;

	private GoalManager() {
		goals = goalDao.retrieveAll();
	}

	public static GoalManager getInstance() {
		if (ONLY_INSTANCE == null) {
			synchronized (GoalManager.class) {
				if (ONLY_INSTANCE == null) {
					ONLY_INSTANCE = new GoalManager();
				}
			}
		}
		return ONLY_INSTANCE;
	}

	public List<Goal> getGoals() {
		return goals;
	}

	

	public List<Task> retrieveTasksForGoal(Goal goal) {
		return goalDao.retrieveTasks(goal);
	}

	public void linkTaskToGoal(Goal goal, Task selectedTask) {
		goalDao.createLink(goal, selectedTask);
	}



}
