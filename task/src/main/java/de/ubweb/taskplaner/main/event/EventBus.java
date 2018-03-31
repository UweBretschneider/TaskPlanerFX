package de.ubweb.taskplaner.main.event;

import java.util.HashMap;
import java.util.Map;

import de.ubweb.taskplaner.goal.controller.GoalService;
import de.ubweb.taskplaner.project.controller.ProjectService;
import de.ubweb.taskplaner.results.controller.ResultService;
import de.ubweb.taskplaner.task.controller.TaskService;

public class EventBus {
	private static EventBus INSTANCE = new EventBus();
	private Map<Class<? extends CustomEvent>, IService> serviceMap;

	private EventBus() {
		serviceMap = new HashMap<>();
		serviceMap.put(TaskEvent.class, new TaskService());
		serviceMap.put(ProjectEvent.class, new ProjectService());
		serviceMap.put(GoalEvent.class, new GoalService());
		serviceMap.put(ResultEvent.class, new ResultService());
	}

	public static EventBus getInstance() {
		return INSTANCE;
	}

	public void registerEvent(Class<? extends CustomEvent> c, IService controller) {
		serviceMap.put(c, controller);
	}

	public void deliverEvent(CustomEvent event) {
		if (serviceMap.containsKey(event.getClass())) {
			serviceMap.get(event.getClass()).handleEvent(event);
		}
	}

}
