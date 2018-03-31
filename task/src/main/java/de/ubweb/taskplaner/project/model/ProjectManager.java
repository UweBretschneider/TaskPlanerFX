package de.ubweb.taskplaner.project.model;

import java.util.List;
import java.util.Observable;

import de.ubweb.taskplaner.project.database.ProjectDAO;
import de.ubweb.taskplaner.task.model.Task;

public class ProjectManager extends Observable {
	private static volatile ProjectManager ONLY_INSTANCE;

	private ProjectDAO projectDao = new ProjectDAO();

	private List<Project> openProjects;
	private List<Project> closedProjects;

	private ProjectManager() {
		openProjects = projectDao.retrieveOpenProjects();
		closedProjects = projectDao.retrieveClosedProjects();
	}

	public static ProjectManager getInstance() {
		if (ONLY_INSTANCE == null) {
			synchronized (ProjectManager.class) {
				if (ONLY_INSTANCE == null) {
					ONLY_INSTANCE = new ProjectManager();
				}
			}
		}
		return ONLY_INSTANCE;
	}

	public List<Project> getOpenProjects() {
		return openProjects;
	}

	public List<Project> getClosedProjects() {
		return closedProjects;
	}

	public void createProject(Project project) {
		projectDao.create(project);

		if (project.getProjectStatus().equals(ProjectStatus.CREATED)) {
			openProjects.add(project);
		} else {
			closedProjects.add(project);
		}

		setChanged();
		notifyObservers();

	}

	public List<Task> retrieveTasksForProject(Project project) {
		return projectDao.retrieveTasks(project);
	}

	public boolean linkTaskToProject(Project project, Task selectedTask) {
		if (!projectDao.isTaskLinked(selectedTask)) {
			projectDao.createLink(project, selectedTask);
			return true;
		} else {
			return false;
		}
	}

}
