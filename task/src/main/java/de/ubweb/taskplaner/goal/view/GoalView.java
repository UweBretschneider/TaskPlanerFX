package de.ubweb.taskplaner.goal.view;


import de.ubweb.taskplaner.goal.controller.DisplayGoalsController;
import de.ubweb.taskplaner.goal.model.Goal;
import de.ubweb.taskplaner.goal.presenter.GoalsPresenter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;

public class GoalView extends TitledPane {
	private final Goal model;
	private DisplayGoalsController controller;

	public GoalView(Goal goal, DisplayGoalsController goalsPresenter) {
		this.model = goal;
		this.controller = goalsPresenter;
		
		this.setCollapsible(false);
		this.setText(model.getTitle());
		
		

		
		BorderPane borderPane = new BorderPane();
		
		Label label = new Label();
		label.setText(model.getDescription());
		borderPane.setCenter(label);

		Button button = new Button();
		button.setText("Tasks");
		button.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	controller.displayGoal(model);
	        }
	    });

		borderPane.setLeft(button);
		
		this.setContent(borderPane);
	}
}
