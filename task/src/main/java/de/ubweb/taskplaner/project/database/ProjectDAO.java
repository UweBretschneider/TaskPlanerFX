package de.ubweb.taskplaner.project.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.ubweb.taskplaner.main.application.MySQLCon;
import de.ubweb.taskplaner.project.model.Project;
import de.ubweb.taskplaner.project.model.ProjectStatus;
import de.ubweb.taskplaner.task.database.TaskDAO;
import de.ubweb.taskplaner.task.model.Task;
import de.ubweb.utils.database.QueryBuilder;

public class ProjectDAO {
	private static final String TABLE_NAME = "projects";
	private static final String PK_ID = "project_id";
	private static final String COL_TITLE = "project_title";
	private static final String COL_CREATED_AT = "created_at";
	private static final String COL_FINISHED_AT = "finished_at";
	private static final String COL_STATUS = "project_status";

	private static final String CREATE = QueryBuilder.buildPreparedInsertStatement(TABLE_NAME, COL_TITLE, COL_CREATED_AT, COL_FINISHED_AT, COL_STATUS);
	private static final String SELECT_OPEN_PROJECTS = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_STATUS + " = 1";
	private static final String SELECT_CLOSED_PROJECTS = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_STATUS + " > 1";

	private static final String RETRIEVE_TASKS = "SELECT * FROM project_tasks WHERE project_id = ?";
	private static final String CREATE_LINK = "INSERT INTO project_tasks (project_id, task_id) VALUES (?,?)";

	private static final String IS_TASK_LINKED = "SELECT * FROM project_tasks WHERE task_id = ?";

	public List<Project> retrieveOpenProjects() {
		List<Project> projects = new ArrayList<>();
		try (PreparedStatement stmt = MySQLCon.getInstance().prepareStatement(SELECT_OPEN_PROJECTS)) {
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				projects.add(convertResultSet(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return projects;
	}

	public List<Project> retrieveClosedProjects() {
		List<Project> projects = new ArrayList<>();
		try (PreparedStatement stmt = MySQLCon.getInstance().prepareStatement(SELECT_CLOSED_PROJECTS)) {
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				projects.add(convertResultSet(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return projects;
	}

	public void create(Project project) {
		try (PreparedStatement stmt = MySQLCon.getInstance().prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, project.getTitle());

			if (project.getCreatedAt() != null) {
				stmt.setDate(2, MySQLCon.convertLocalDate(project.getCreatedAt()));
			} else {
				stmt.setNull(2, java.sql.Types.DATE);
			}
			if (project.getFinishedAt() != null) {
				stmt.setDate(3, MySQLCon.convertLocalDate(project.getFinishedAt()));
			} else {
				stmt.setNull(3, java.sql.Types.DATE);
			}
			stmt.setInt(4, project.getProjectStatus().getStatusCode());

			stmt.execute();

			ResultSet rs = stmt.getGeneratedKeys();

			if (rs.next()) {
				project.setProjectId(rs.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private Project convertResultSet(ResultSet rs) throws SQLException {
		Project project = new Project();

		project.setProjectId(rs.getInt(PK_ID));
		project.setTitle(rs.getString(COL_TITLE));
		project.setCreatedAt(MySQLCon.convertDateToLocalDate(rs.getDate(COL_CREATED_AT)));
		project.setFinishedAt(MySQLCon.convertDateToLocalDate(rs.getDate(COL_FINISHED_AT)));
		project.setProjectStatus(ProjectStatus.convertStatusCode(rs.getInt(COL_STATUS)));

		return project;
	}

	public List<Task> retrieveTasks(Project project) {
		List<Task> tasks = new ArrayList<>();

		List<Integer> taskIds = new ArrayList<>();
		try (PreparedStatement stmt = MySQLCon.getInstance().prepareStatement(RETRIEVE_TASKS)) {
			stmt.setInt(1, project.getProjectId());
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

	public void createLink(Project project, Task selectedTask) {
		try (PreparedStatement stmt = MySQLCon.getInstance().prepareStatement(CREATE_LINK)) {

			stmt.setInt(1, project.getProjectId());
			stmt.setInt(2, selectedTask.getTaskId());

			stmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public boolean isTaskLinked(Task selectedTask) {
		try (PreparedStatement stmt = MySQLCon.getInstance().prepareStatement(IS_TASK_LINKED)) {
			stmt.setInt(1, selectedTask.getTaskId());

			ResultSet rs = stmt.executeQuery();
			return rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

}
