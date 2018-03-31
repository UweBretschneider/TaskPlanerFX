package de.ubweb.taskplaner.main.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ubweb.taskplaner.main.event.EventBus;
import de.ubweb.taskplaner.main.event.GoalEvent;
import de.ubweb.taskplaner.main.event.ProjectEvent;
import de.ubweb.taskplaner.main.event.ResultEvent;
import de.ubweb.taskplaner.main.event.TaskEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TaskPlanerFX extends Application {
	private static final Logger log = LogManager.getLogger(TaskPlanerFX.class);

	
	private static TaskPlanerFX instance;

	private Stage stage;
	private Parent rootView;
	private TabPane mainPane;

	public static void main(String[] args) {
		launch(args);
	}

	public TaskPlanerFX() {
		instance = this;
	}

	// static method to get instance of view
	public static TaskPlanerFX getInstance() {
		return instance;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;
		stage.setTitle("Task Planer FX");

		rootView = FXMLLoader.load(getClass().getResource("/de/ubweb/taskplaner/main/view/main_view.fxml"));

		Scene scene = new Scene(rootView, 1280, 960);
		scene.setFill(Color.OLDLACE);

		mainPane = (TabPane) scene.lookup("#mainPane");

		stage.setScene(scene);

		stage.sizeToScene();
		stage.show();

		readSettings();
		
		initTasks();
		initProjects();
		initGoals();
		initResults();
	}
	
	private void readSettings() {
		InputStream input = getClass().getClassLoader().getResourceAsStream("settings.properties");
		Properties properties = new Properties();
		
		
		try {
			properties.load(input);

			String user = properties.getProperty("user");
			String password = properties.getProperty("password");
			String host = properties.getProperty("host");
			String port = properties.getProperty("port");
			String db = properties.getProperty("database");
			
			MySQLCon.setConnectionProperties(host, port, user, password, db);
			
		} catch (IOException e) {
			log.error(e.getMessage());
		}	
	}

	private void initTasks() {
		TaskEvent taskEvent = new TaskEvent(TaskEvent.DISPLAY_TASKS);
		EventBus.getInstance().deliverEvent(taskEvent);
	}

	private void initProjects() {
		ProjectEvent projectEvent = new ProjectEvent(ProjectEvent.DISPLAY_PROJECTS);
		EventBus.getInstance().deliverEvent(projectEvent);
	}

	private void initGoals() {
		GoalEvent goalEvent = new GoalEvent(GoalEvent.DISPLAY_GOALS);
		EventBus.getInstance().deliverEvent(goalEvent);
	}

	private void initResults() {
		ResultEvent resultEvent = new ResultEvent(ResultEvent.DISPLAY_RESULTS);
		EventBus.getInstance().deliverEvent(resultEvent);
	}

	public void addTab(Parent parent, String title, boolean isClosable) {
		Tab tab = new Tab(title);
		tab.setContent(parent);
		tab.setClosable(isClosable);
		mainPane.getTabs().add(tab);
	}
}
