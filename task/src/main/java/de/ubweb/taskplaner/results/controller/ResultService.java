package de.ubweb.taskplaner.results.controller;

import de.ubweb.taskplaner.main.application.TaskPlanerFX;
import de.ubweb.taskplaner.main.event.CustomEvent;
import de.ubweb.taskplaner.main.event.IService;
import de.ubweb.taskplaner.main.event.ResultEvent;

public class ResultService implements IService {

	public void displayResults() {
		DisplayResultsController controller = new DisplayResultsController();
		TaskPlanerFX.getInstance().addTab(controller.getView(), "Resultate", false);
	}

	@Override
	public void handleEvent(CustomEvent event) {
		if (event instanceof ResultEvent) {
			ResultEvent taskEvent = (ResultEvent) event;
			switch (event.getAction()) {
			case ResultEvent.DISPLAY_RESULTS:
				displayResults();
				break;
			}
		}

	}

}
