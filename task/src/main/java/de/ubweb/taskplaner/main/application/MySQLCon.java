package de.ubweb.taskplaner.main.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class MySQLCon {
	private volatile static MySQLCon onlyInstance;
	private static Connection conn = null;

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	
	private static String USER = "root";
	private static String PW = "hamsat";
	private static String HOST = "localhost";
	private static String PORT = "3306";
	private static String DB = "task_planer";
	
	static void setConnectionProperties(String host, String port, String user, String password, String db) {
		MySQLCon.HOST = host;
		MySQLCon.PORT = port;
		MySQLCon.USER = user;
		MySQLCon.PW = password;
		MySQLCon.DB = db;
	}

	private MySQLCon() {
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(
					"jdbc:mysql://" + HOST + ":" + PORT + "/" + DB + "?" + "user=" + USER + "&" + "password=" + PW);
		} catch (ClassNotFoundException e) {
			System.out.println("Treiber nicht gefunden");
		} catch (SQLException e) {
			System.out.println("Connect nicht moeglich");
		}
	}

	public static MySQLCon getInstance() {
		if (onlyInstance == null) {
			synchronized (MySQLCon.class) {
				if (onlyInstance == null) {
					onlyInstance = new MySQLCon();
				}
			}
		}
		return onlyInstance;
	}

	public Connection getConnection() {
		try {
			if (conn.isClosed()) {
				Class.forName(DRIVER);
				conn = DriverManager.getConnection(
						"jdbc:mysql://" + HOST + ":" + PORT + "/" + DB + "?" + "user=" + USER + "&" + "password=" + PW);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Treiber nicht gefunden");
		} catch (SQLException e) {
			System.out.println("Connect nicht moeglich");
		}
		return conn;
	}

	public PreparedStatement prepareStatement(String sqlQuery) throws SQLException {
		return getConnection().prepareStatement(sqlQuery);
	}

	public PreparedStatement prepareStatement(String sqlQuery, int arg) throws SQLException {
		return getConnection().prepareStatement(sqlQuery, arg);
	}

	public static LocalDate convertDateToLocalDate(java.sql.Date date) {
		if (date == null) {
			return null;
		}
		return date.toLocalDate();
	}

	public static java.sql.Date convertLocalDate(LocalDate localDate) {
		if (localDate == null) {
			return null;
		}

		return java.sql.Date.valueOf(localDate);
	}
}
