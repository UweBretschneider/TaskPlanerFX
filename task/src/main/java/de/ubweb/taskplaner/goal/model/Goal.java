package de.ubweb.taskplaner.goal.model;

import java.util.List;

import de.ubweb.taskplaner.task.model.Task;

public class Goal {
	private int goalId;
	private String title;
	private String description;
	
	private List<Task> tasks;

	public int getGoalId() {
		return goalId;
	}

	public void setGoalId(int goalId) {
		this.goalId = goalId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Task> getTasks() {
		if (tasks == null) {
			tasks = GoalManager.getInstance().retrieveTasksForGoal(this);
		}
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}


	public void addTask(Task selectedTask) {
		if (!tasks.contains(selectedTask)) {
			GoalManager.getInstance().linkTaskToGoal(this, selectedTask);
			tasks.add(selectedTask);
		}
		
	}
	
	@Override
	public String toString() {
		return "Goal [" + title + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + goalId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Goal other = (Goal) obj;
		if (goalId != other.goalId)
			return false;
		return true;
	}
	
	
}
