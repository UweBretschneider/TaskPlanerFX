package de.ubweb.taskplaner.main.event;

public abstract class CustomEvent {
	private int action;

	public CustomEvent(int action) {
		this.action = action;
	}

	public int getAction() {
		return action;
	}
}
