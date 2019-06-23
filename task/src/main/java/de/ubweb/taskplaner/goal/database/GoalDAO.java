package de.ubweb.taskplaner.goal.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.ubweb.taskplaner.goal.model.Goal;
import de.ubweb.taskplaner.main.application.MySQLCon;
import de.ubweb.taskplaner.project.model.Project;
import de.ubweb.taskplaner.task.database.TaskDAO;
import de.ubweb.taskplaner.task.model.Task;

public class GoalDAO {
	private static final String TABLE_NAME = "goals";

	private static final String PK_ID = "goal_id";
	private static final String COL_TITLE = "goal_title";
	private static final String COL_DESCRIPTION = "goal_description";

	private static final String RETRIEVE = "SELECT * FROM " + TABLE_NAME;
	
	private static final String RETRIEVE_TASKS = "SELECT * FROM goal_tasks WHERE goal_id = ?";
	
	private static final String CREATE_LINK = "INSERT INTO goal_tasks (goal_id, task_id) VALUES (?,?)";

	private static List<Goal> goalCache = null;

	public List<Goal> retrieveAll() {
		if (goalCache != null) {
			return goalCache;
		} else {
			goalCache = new ArrayList<>();

			try (PreparedStatement stmt = MySQLCon.getInstance().prepareStatement(RETRIEVE)) {
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					goalCache.add(convertResultSetToGoal(rs));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return goalCache;
	}

	private Goal convertResultSetToGoal(ResultSet rs) throws SQLException {
		Goal goal = new Goal();

		goal.setGoalId(rs.getInt(PK_ID));
		goal.setTitle(rs.getString(COL_TITLE));
		goal.setDescription(rs.getString(COL_DESCRIPTION));

		return goal;
	}

	public List<Task> retrieveTasks(Goal goal) {
		List<Task> tasks = new ArrayList<>();

		List<Integer> taskIds = new ArrayList<>();
		try (PreparedStatement stmt = MySQLCon.getInstance().prepareStatement(RETRIEVE_TASKS)) {
			stmt.setInt(1, goal.getGoalId());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				taskIds.add(rs.getInt("task_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (taskIds.size() > 0) {
			TaskDAO taskDao = new TaskDAO();
			for (Integer taskId : taskIds) {
				tasks.add(taskDao.retrieveById(taskId));
			}
		}

		return tasks;
	}
	
	public void createLink(Goal goal, Task selectedTask) {
		try (PreparedStatement stmt = MySQLCon.getInstance().prepareStatement(CREATE_LINK)) {

			stmt.setInt(1, goal.getGoalId());
			stmt.setInt(2, selectedTask.getTaskId());

			stmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
