package de.ubweb.taskplaner.goal.view;

import de.ubweb.taskplaner.goal.model.Goal;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;

public class GoalView extends TitledPane {
	private final Goal model;

	public GoalView(Goal goal) {
		this.model = goal;

		this.setCollapsible(false);
		this.setText(model.getTitle());

		Label label = new Label();
		label.setText(model.getDescription());

		this.setContent(label);
	}
}
