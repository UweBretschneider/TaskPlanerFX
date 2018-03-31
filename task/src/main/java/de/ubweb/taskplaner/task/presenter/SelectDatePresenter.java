package de.ubweb.taskplaner.task.presenter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import de.ubweb.taskplaner.task.controller.DisplayTasksController;
import de.ubweb.taskplaner.task.view.SelectDateView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;

public class SelectDatePresenter implements Initializable {
	private DisplayTasksController controller;
	private SelectDateView selectDateView;

	@FXML
	private DatePicker datePicker;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		datePicker.setValue(LocalDate.now());
	}

	public LocalDate getSelectedDate() {
		return datePicker.getValue();
	}

	public void selectDate(ActionEvent event) {
		controller.selectDate(getSelectedDate());
		selectDateView.closeStage();
	}

	public void setController(DisplayTasksController controller) {
		this.controller = controller;
	}

	public void setView(SelectDateView selectDateView) {
		this.selectDateView = selectDateView;

	}

}
