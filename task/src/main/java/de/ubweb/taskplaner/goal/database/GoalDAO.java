package de.ubweb.taskplaner.goal.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.ubweb.taskplaner.goal.model.Goal;
import de.ubweb.taskplaner.main.application.MySQLCon;

public class GoalDAO {
	private static final String TABLE_NAME = "goals";

	private static final String PK_ID = "goal_id";
	private static final String COL_TITLE = "goal_title";
	private static final String COL_DESCRIPTION = "goal_description";

	private static final String RETRIEVE = "SELECT * FROM " + TABLE_NAME;

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

}
