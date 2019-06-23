package de.ubweb.taskplaner.goal.controller;

import de.ubweb.taskplaner.goal.model.Goal;
import de.ubweb.taskplaner.goal.presenter.GoalPresenter;
import de.ubweb.taskplaner.goal.view.GoalDetailView;
import de.ubweb.taskplaner.main.event.CustomEvent;
import de.ubweb.taskplaner.main.event.IService;
import de.ubweb.taskplaner.main.event.TaskEvent;
import de.ubweb.taskplaner.project.model.Project;
import de.ubweb.taskplaner.project.presenter.ProjectPresenter;
import de.ubweb.taskplaner.project.view.ProjectView;
import de.ubweb.taskplaner.task.model.Task;
import de.ubweb.taskplaner.task.model.TaskManager;
import de.ubweb.taskplaner.task.view.SelectTaskView;
import javafx.scene.Parent;

public class GoalController implements IService{
	private Goal goal;
	private GoalDetailView view;
	private GoalPresenter presenter;

	private SelectTaskView selectView;
	
	

	public void displayGoal(Goal goal) {
		view = new GoalDetailView();
		presenter = view.getPresenter();
		presenter.setController(this);

		this.goal = goal;

		presenter.setGoal(goal);
	}

	public Parent getView() {
		return view.getView();
	}

	
	public void linkTask() {
		selectView = new SelectTaskView();
		selectView.getPresenter().setController(this);
		selectView.getPresenter().setTasks(TaskManager.getInstance().getOpenTasks());
	}

	public void linkTask(Task selectedTask) {
		goal.addTask(selectedTask);
		selectView.closeStage();

		presenter.setGoal(goal);
		
	}

	@Override
	public void handleEvent(CustomEvent event) {
		if(event instanceof TaskEvent) {
			TaskEvent taskEvent = (TaskEvent) event;
			switch(taskEvent.getAction()) {
			case TaskEvent.LINK_SELECTED_TASK:
				linkTask(taskEvent.getSelectedTask());
				break;
			}
		}
		
	}
	

}
