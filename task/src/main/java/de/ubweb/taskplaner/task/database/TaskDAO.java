package de.ubweb.taskplaner.task.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import de.ubweb.taskplaner.main.application.MySQLCon;
import de.ubweb.taskplaner.task.model.SolutionQuality;
import de.ubweb.taskplaner.task.model.SolutionTime;
import de.ubweb.taskplaner.task.model.Task;
import de.ubweb.taskplaner.task.model.TaskDifficulty;
import de.ubweb.taskplaner.task.model.TaskPriority;
import de.ubweb.taskplaner.task.model.TaskStatus;
import de.ubweb.taskplaner.task.model.TaskType;
import de.ubweb.utils.database.QueryBuilder;

public class TaskDAO {
	public static final String TABLE_NAME = "tasks";

	public static final String PRIMARY_KEY = "task_id";
	private static final String COL_TYPE = "task_type";
	private static final String COL_CREATED_AT = "created_at";
	private static final String COL_ACTIVE_FROM = "active_from";
	private static final String COL_DEADLINE = "deadline";
	private static final String COL_STARTED_AT = "started_at";
	private static final String COL_FINISHED_AT = "finished_at";
	private static final String COL_SCHEDULED_FOR = "scheduled_for";
	private static final String COL_TITLE = "title";
	private static final String COL_DESCRIPTION = "description";
	private static final String COL_STATUS = "task_status";
	private static final String COL_DIFFICULTY = "difficulty";
	private static final String COL_PRIORITY = "priority";
	private static final String FK_CATEGORY = "category_id";
	private static final String COL_RESCHEDULE_COUNT = "reschedule_count";
	private static final String FK_PARENT = "parent_task_id";
	private static final String COL_SOLUTION_QUALITY = "solution_quality";
	private static final String COL_SOLUTION_TIME = "solution_time";

	private static final String RETRIEVE_CURRENT = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_STATUS + " = " + TaskStatus.CREATED.getStatusCode();
	private static final String RETRIEVE_PRIORITY = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_STATUS + " = " + TaskStatus.CREATED.getStatusCode()
			+ " AND " + COL_PRIORITY + " = ?";

	private static final String RETRIEVE_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + PRIMARY_KEY + " = ?";

	private static final String RETRIEVE_RECENTLY_FINISHED = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_STATUS + " != "
			+ TaskStatus.CREATED.getStatusCode() + " ORDER BY " + COL_FINISHED_AT + " DESC, " + PRIMARY_KEY + " DESC LIMIT ?";

	private static final String RETRIEVE_RECENTLY_FINISHED_BY_DAY = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_STATUS + " != "
			+ TaskStatus.CREATED.getStatusCode() + " AND " + COL_FINISHED_AT + " > ?";

	private static final String UPDATE = QueryBuilder.buildPreparedUpdateStatement(TABLE_NAME, PRIMARY_KEY, COL_TYPE, COL_CREATED_AT, COL_ACTIVE_FROM,
			COL_DEADLINE, COL_STARTED_AT, COL_FINISHED_AT, COL_SCHEDULED_FOR, COL_TITLE, COL_DESCRIPTION, COL_STATUS, COL_DIFFICULTY, COL_PRIORITY, FK_CATEGORY,
			COL_RESCHEDULE_COUNT, FK_PARENT, COL_SOLUTION_QUALITY, COL_SOLUTION_TIME);

	private static final String CREATE = QueryBuilder.buildPreparedInsertStatement(TABLE_NAME, COL_TYPE, COL_CREATED_AT, COL_ACTIVE_FROM, COL_DEADLINE,
			COL_STARTED_AT, COL_FINISHED_AT, COL_SCHEDULED_FOR, COL_TITLE, COL_DESCRIPTION, COL_STATUS, COL_DIFFICULTY, COL_PRIORITY, FK_CATEGORY,
			COL_RESCHEDULE_COUNT, FK_PARENT, COL_SOLUTION_QUALITY, COL_SOLUTION_TIME);

	private static final String DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + PRIMARY_KEY + " = ?";

	private CategoryDAO categoryDao = new CategoryDAO();

	public Task retrieveById(int taskId) {
		Task task = null;

		try (PreparedStatement stmt = MySQLCon.getInstance().prepareStatement(RETRIEVE_BY_ID)) {
			stmt.setInt(1, taskId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				task = convertResultSetToTask(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return task;
	}

	public List<Task> retrieveOpenTasks() {
		List<Task> currentTasks = new ArrayList<>();

		try (PreparedStatement stmt = MySQLCon.getInstance().prepareStatement(RETRIEVE_CURRENT)) {
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				currentTasks.add(convertResultSetToTask(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return currentTasks;
	}

	public void create(Task task) {
		try (PreparedStatement stmt = MySQLCon.getInstance().prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS)) {

			stmt.setInt(1, task.getTaskType().getStatusCode());

			if (task.getCreatedAt() != null) {
				stmt.setDate(2, MySQLCon.convertLocalDate(task.getCreatedAt()));
			} else {
				stmt.setNull(2, java.sql.Types.DATE);
			}
			if (task.getActiveFrom() != null) {
				stmt.setDate(3, MySQLCon.convertLocalDate(task.getActiveFrom()));
			} else {
				stmt.setNull(3, java.sql.Types.DATE);
			}
			if (task.getDeadline() != null) {
				stmt.setDate(4, MySQLCon.convertLocalDate(task.getDeadline()));
			} else {
				stmt.setNull(4, java.sql.Types.DATE);
			}

			if (task.getStartedAt() != null) {
				stmt.setDate(5, MySQLCon.convertLocalDate(task.getStartedAt()));
			} else {
				stmt.setNull(5, java.sql.Types.DATE);
			}

			if (task.getFinishedAt() != null) {
				stmt.setDate(6, MySQLCon.convertLocalDate(task.getFinishedAt()));
			} else {
				stmt.setNull(6, java.sql.Types.DATE);
			}

			if (task.getScheduledFor() != null) {
				stmt.setDate(7, MySQLCon.convertLocalDate(task.getScheduledFor()));
			} else {
				stmt.setNull(7, java.sql.Types.DATE);
			}

			stmt.setString(8, task.getTitle());
			if (task.getDescription() != null) {
				stmt.setString(9, task.getDescription());
			} else {
				stmt.setNull(9, java.sql.Types.VARCHAR);
			}
			stmt.setInt(10, task.getTaskStatus().getStatusCode());
			stmt.setInt(11, task.getTaskDifficulty().getStatusCode());
			stmt.setInt(12, task.getTaskPriority().getStatusCode());
			stmt.setInt(13, task.getCategory().getCategoryId());
			stmt.setInt(14, task.getRescheduleCount());
			if (task.getParentTask() != null) {
				stmt.setInt(15, task.getParentTask().getTaskId());
			} else {
				stmt.setNull(15, java.sql.Types.VARCHAR);
			}
			
			stmt.setInt(16, task.getSolutionQuality().getStatusCode());
			stmt.setInt(17, task.getSolutionTime().getStatusCode());

			stmt.execute();

			ResultSet rs = stmt.getGeneratedKeys();

			if (rs.next()) {
				task.setTaskId(rs.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Task> retrieveRecentlyFinished(int nrOfTasks) {
		List<Task> recentlyFinishedTasks = new ArrayList<>();

		try (PreparedStatement stmt = MySQLCon.getInstance().prepareStatement(RETRIEVE_RECENTLY_FINISHED)) {
			stmt.setInt(1, nrOfTasks);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				recentlyFinishedTasks.add(convertResultSetToTask(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return recentlyFinishedTasks;
	}

	public List<Task> retrieveRecentlyFinishedByDay(int days) {
		List<Task> recentlyFinishedTasks = new ArrayList<>();

		LocalDate today = LocalDate.now();
		LocalDate pastDate = today.minusDays(days);

		try (PreparedStatement stmt = MySQLCon.getInstance().prepareStatement(RETRIEVE_RECENTLY_FINISHED_BY_DAY)) {
			stmt.setDate(1, java.sql.Date.valueOf(pastDate));

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				recentlyFinishedTasks.add(convertResultSetToTask(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return recentlyFinishedTasks;
	}

	private Task convertResultSetToTask(ResultSet rs) throws SQLException {
		Task atomicTask = new Task();

		atomicTask.setTaskId(rs.getInt(PRIMARY_KEY));
		atomicTask.setTaskType(TaskType.convertStatusCode(rs.getInt(COL_TYPE)));

		atomicTask.setCreatedAt(MySQLCon.convertDateToLocalDate(rs.getDate(COL_CREATED_AT)));
		atomicTask.setActiveFrom(MySQLCon.convertDateToLocalDate(rs.getDate(COL_ACTIVE_FROM)));
		atomicTask.setDeadline(MySQLCon.convertDateToLocalDate(rs.getDate(COL_DEADLINE)));
		atomicTask.setStartedAt(MySQLCon.convertDateToLocalDate(rs.getDate(COL_STARTED_AT)));
		atomicTask.setFinishedAt(MySQLCon.convertDateToLocalDate(rs.getDate(COL_FINISHED_AT)));
		atomicTask.setScheduledFor(MySQLCon.convertDateToLocalDate(rs.getDate(COL_SCHEDULED_FOR)));
		atomicTask.setTitle(rs.getString(COL_TITLE));
		atomicTask.setDescription(rs.getString(COL_DESCRIPTION));
		atomicTask.setTaskStatus(TaskStatus.convertStatusCode(rs.getInt(COL_STATUS)));
		atomicTask.setTaskDifficulty(TaskDifficulty.convertStatusCode(rs.getInt(COL_DIFFICULTY)));
		atomicTask.setTaskPriority(TaskPriority.convertStatusCode(rs.getInt(COL_PRIORITY)));
		atomicTask.setCategory(categoryDao.retrieveById(rs.getInt(FK_CATEGORY)));

		atomicTask.setRescheduleCount(rs.getInt(COL_RESCHEDULE_COUNT));
		if (rs.getInt(FK_PARENT) == 0) {
			atomicTask.setParentTask(null);
		} else {
			atomicTask.setParentTask(retrieveById(rs.getInt(FK_PARENT)));
		}
		
		atomicTask.setSolutionQuality(SolutionQuality.convertStatusCode(rs.getInt(COL_SOLUTION_QUALITY)));
		atomicTask.setSolutionTime(SolutionTime.convertStatusCode(rs.getInt(COL_SOLUTION_TIME)));
		
		return atomicTask;
	}

	public void update(Task task) {
		try (PreparedStatement stmt = MySQLCon.getInstance().prepareStatement(UPDATE)) {

			stmt.setInt(1, task.getTaskType().getStatusCode());

			if (task.getCreatedAt() != null) {
				stmt.setDate(2, MySQLCon.convertLocalDate(task.getCreatedAt()));
			} else {
				stmt.setNull(2, java.sql.Types.DATE);
			}
			if (task.getActiveFrom() != null) {
				stmt.setDate(3, MySQLCon.convertLocalDate(task.getActiveFrom()));
			} else {
				stmt.setNull(3, java.sql.Types.DATE);
			}
			if (task.getDeadline() != null) {
				stmt.setDate(4, MySQLCon.convertLocalDate(task.getDeadline()));
			} else {
				stmt.setNull(4, java.sql.Types.DATE);
			}
			if (task.getStartedAt() != null) {
				stmt.setDate(5, MySQLCon.convertLocalDate(task.getStartedAt()));
			} else {
				stmt.setNull(5, java.sql.Types.DATE);
			}

			if (task.getFinishedAt() != null) {
				stmt.setDate(6, MySQLCon.convertLocalDate(task.getFinishedAt()));
			} else {
				stmt.setNull(6, java.sql.Types.DATE);
			}

			if (task.getScheduledFor() != null) {
				stmt.setDate(7, MySQLCon.convertLocalDate(task.getScheduledFor()));
			} else {
				stmt.setNull(7, java.sql.Types.DATE);
			}

			stmt.setString(8, task.getTitle());
			if (task.getDescription() != null) {
				stmt.setString(9, task.getDescription());
			} else {
				stmt.setNull(9, java.sql.Types.VARCHAR);
			}
			stmt.setInt(10, task.getTaskStatus().getStatusCode());
			stmt.setInt(11, task.getTaskDifficulty().getStatusCode());
			stmt.setInt(12, task.getTaskPriority().getStatusCode());
			stmt.setInt(13, task.getCategory().getCategoryId());

			stmt.setInt(14, task.getRescheduleCount());
			if (task.getParentTask() != null) {
				stmt.setInt(15, task.getParentTask().getTaskId());
			} else {
				stmt.setNull(15, java.sql.Types.INTEGER);
			}

			stmt.setInt(16, task.getSolutionQuality().getStatusCode());
			stmt.setInt(17, task.getSolutionTime().getStatusCode());
			
			stmt.setInt(18, task.getTaskId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(Task task) {
		try (PreparedStatement stmt = MySQLCon.getInstance().prepareStatement(DELETE)) {
			stmt.setInt(1, task.getTaskId());
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
