package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.AppSchemas.ProgressTableSchema;
import database.DatabaseHelper;
import database.SqliteDatabase;

public class ProgressTable {

	private DatabaseHelper dbh;

	public ProgressTable(SqliteDatabase db) {
		this.dbh = new DatabaseHelper(db);
		createProgressTable();
	}

	private void createProgressTable() {
		String sql = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER, %s INTEGER, UNIQUE(%s));",
				ProgressTableSchema.NAME, ProgressTableSchema.Cols.idNumber, ProgressTableSchema.Cols.completed,
				ProgressTableSchema.Cols.idNumber);

		dbh.createTable(sql);
		insertIntoProgressTable();
	}

	private void insertIntoProgressTable() {
		for (Integer id : dbh.getScenarios().keySet()) {

			String sql = String.format("INSERT OR IGNORE INTO %s (%s, %s) VALUES (?,?)", ProgressTableSchema.NAME,
					ProgressTableSchema.Cols.idNumber, ProgressTableSchema.Cols.completed);

			try (Connection conn = dbh.getDb().connect(); PreparedStatement pst = conn.prepareStatement(sql)) {
				pst.setInt(1, dbh.getScenarios().get(id).getId());

				pst.setInt(2, dbh.getScenarios().get(id).isCompleted());
				pst.executeUpdate();

				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
