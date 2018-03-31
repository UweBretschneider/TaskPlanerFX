package de.ubweb.taskplaner.project.controller;

import de.ubweb.taskplaner.main.event.EventBus;
import de.ubweb.taskplaner.main.event.ProjectEvent;
import de.ubweb.taskplaner.project.model.Project;
import de.ubweb.taskplaner.project.model.ProjectManager;
import de.ubweb.taskplaner.project.presenter.ProjectsPresenter;
import de.ubweb.taskplaner.project.view.ProjectsView;
import javafx.scene.Parent;

public class DisplayProjectsController {
	private ProjectsView view;
	private ProjectsPresenter presenter;

	public void displayProjects() {
		view = new ProjectsView();
		presenter = view.getPresenter();
		presenter.setController(this);

		presenter.setOpenProjects(ProjectManager.getInstance().getOpenProjects());
		presenter.setClosedProjects(ProjectManager.getInstance().getClosedProjects());
	}

	public Parent getView() {
		return view.getView();
	}

	public void displayProject(Project selectededProject) {
		ProjectEvent event = new ProjectEvent(ProjectEvent.DISPLAY_PROJECT_DETAILS);
		event.setProject(selectededProject);

		EventBus.getInstance().deliverEvent(event);
	}
}
