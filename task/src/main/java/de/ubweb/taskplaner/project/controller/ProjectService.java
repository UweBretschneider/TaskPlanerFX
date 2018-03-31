package de.ubweb.taskplaner.project.controller;

import de.ubweb.taskplaner.main.application.TaskPlanerFX;
import de.ubweb.taskplaner.main.event.CustomEvent;
import de.ubweb.taskplaner.main.event.IService;
import de.ubweb.taskplaner.main.event.ProjectEvent;
import de.ubweb.taskplaner.project.model.Project;

public class ProjectService implements IService {

	public void displayProjects() {
		DisplayProjectsController controller = new DisplayProjectsController();
		controller.displayProjects();
		TaskPlanerFX.getInstance().addTab(controller.getView(), "Projekte", false);
	}

	public void createProject() {
		CreateProjectController controller = new CreateProjectController();
		controller.createProject();
	}

	public void displayProjectDetails(Project project) {
		ProjectController controller = new ProjectController();
		controller.displayProject(project);
		TaskPlanerFX.getInstance().addTab(controller.getView(), "Projektdetails: " + project.getProjectId(), true);
	}

	@Override
	public void handleEvent(CustomEvent event) {
		if (event instanceof ProjectEvent) {
			ProjectEvent projectEvent = (ProjectEvent) event;
			switch (event.getAction()) {
			case ProjectEvent.DISPLAY_PROJECTS:
				displayProjects();
				break;
			case ProjectEvent.CREATE_PROJECT:
				createProject();
				break;
			case ProjectEvent.DISPLAY_PROJECT_DETAILS:
				displayProjectDetails(projectEvent.getProject());
				break;
			}
		}

	}

}
