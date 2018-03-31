package de.ubweb.taskplaner.main.event;

import de.ubweb.taskplaner.project.model.Project;

public class ProjectEvent extends CustomEvent {
	public static final int DISPLAY_PROJECTS = 1;

	public static final int CREATE_PROJECT = 10;

	public static final int DISPLAY_PROJECT_DETAILS = 20;

	private Project project;

	public ProjectEvent(int action) {
		super(action);
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
