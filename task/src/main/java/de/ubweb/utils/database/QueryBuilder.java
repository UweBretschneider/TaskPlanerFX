package de.ubweb.utils.database;

public class QueryBuilder {

	public static String buildSimpleSelectStatement(String tableName) {
		return "SELECT * FROM " + tableName;
	}

	public static String buildPreparedInsertStatement(String tableName, String... cols) {
		StringBuilder sb = new StringBuilder();

		sb.append("INSERT INTO " + tableName + " ");
		sb.append("(");

		for (int i = 0; i < cols.length; i++) {
			if (i != cols.length - 1) {
				sb.append(cols[i] + ", ");
			} else {
				sb.append(cols[i] + ") VALUES (");
			}
		}

		for (int i = 0; i < cols.length; i++) {
			if (i != cols.length - 1) {
				sb.append("?,");
			} else {
				sb.append("?)");
			}
		}

		return sb.toString();
	}

	public static String buildPreparedUpdateStatement(String tableName, String primaryKey, String... cols) {
		StringBuilder sb = new StringBuilder();

		sb.append("UPDATE " + tableName + " ");
		sb.append("SET ");

		for (int i = 0; i < cols.length; i++) {
			if (i != cols.length - 1) {
				sb.append(cols[i] + " = ?, ");
			} else {
				sb.append(cols[i] + " = ? ");
			}
		}

		sb.append("WHERE " + primaryKey + " = ?");

		return sb.toString();
	}

	public static String buildComposedPreparedUpdateStatement(String tableName, String primaryKeyFirst,
			String primaryKeySecond, String... cols) {
		StringBuilder sb = new StringBuilder();

		sb.append("UPDATE " + tableName + " ");
		sb.append("SET ");

		for (int i = 0; i < cols.length; i++) {
			if (i != cols.length - 1) {
				sb.append(cols[i] + " = ?, ");
			} else {
				sb.append(cols[i] + " = ? ");
			}
		}

		sb.append("WHERE " + primaryKeyFirst + " = ? AND " + primaryKeySecond + " = ?");

		return sb.toString();
	}

	public static String buildPreparedDeleteStatement(String tableName, String primaryKey) {
		StringBuilder sb = new StringBuilder();

		sb.append("DELETE FROM " + tableName + " ");
		sb.append("WHERE ");
		sb.append(primaryKey);
		sb.append(" = ?");

		return sb.toString();
	}

	public static String buildPreparedDeleteStatement(String tableName, String primaryKeyFirst,
			String primaryKeySecond) {
		StringBuilder sb = new StringBuilder();

		sb.append("DELETE FROM " + tableName + " ");
		sb.append("WHERE ");
		sb.append(primaryKeyFirst);
		sb.append(" = ? AND ");
		sb.append(primaryKeySecond);
		sb.append(" = ?");

		return sb.toString();
	}

}
