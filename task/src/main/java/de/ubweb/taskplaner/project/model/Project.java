package de.ubweb.taskplaner.project.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import de.ubweb.taskplaner.task.model.Task;
import de.ubweb.taskplaner.task.model.TaskStatus;

public class Project {
	private int projectId;
	private String title;
	private LocalDate createdAt;
	private LocalDate finishedAt;
	private ProjectStatus projectStatus;

	private List<Task> projectTasks;

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Task> getProjectTasks() {
		if (projectTasks == null) {
			projectTasks = ProjectManager.getInstance().retrieveTasksForProject(this);
		}
		return projectTasks;
	}
	
	public List<Task> getOpenTasks(){
		List<Task> openTasks = new ArrayList<>();
		for(Task task: getProjectTasks()) {
			if(task.getTaskStatus() == TaskStatus.CREATED) {
				openTasks.add(task);
			}
		}
		
		return openTasks;
	}
	
	public int getNumberOfOpenTasks() {
		return getOpenTasks().size();
	}
	
	public List<Task> getFinishedTasks(){
		List<Task> openTasks = new ArrayList<>();
		for(Task task: getProjectTasks()) {
			if(task.getTaskStatus() == TaskStatus.SOLVED) {
				openTasks.add(task);
			}
		}
		
		return openTasks;
	}

	public int getNumberOfFinishedTasks() {
		return getFinishedTasks().size();
	}

	public void setProjectTasks(List<Task> projectTasks) {
		this.projectTasks = projectTasks;
	}

	public void addTask(Task selectedTask) {
		if (!projectTasks.contains(selectedTask)) {
			if (ProjectManager.getInstance().linkTaskToProject(this, selectedTask)) {
				projectTasks.add(selectedTask);
			}
		}

	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getFinishedAt() {
		return finishedAt;
	}

	public void setFinishedAt(LocalDate finishedAt) {
		this.finishedAt = finishedAt;
	}

	public ProjectStatus getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(ProjectStatus projectStatus) {
		this.projectStatus = projectStatus;
	}

}
