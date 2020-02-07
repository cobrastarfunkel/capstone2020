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
		String sql = String.format(
				"CREATE TABLE IF NOT EXISTS %s (%s INTEGER NOT NULL, %s INTEGER PRIMARY KEY, %s INTEGER NOT NULL, %s TEXT NOT NULL, UNIQUE(%s));",
				ProgressTableSchema.NAME, ProgressTableSchema.Cols.idNumber, ProgressTableSchema.Cols.pIdNumber,
				ProgressTableSchema.Cols.completed, ProgressTableSchema.Cols.difficulty,
				ProgressTableSchema.Cols.idNumber);

		dbh.createTable(sql);
		insertIntoProgressTable();
	}

	private void insertIntoProgressTable() {
		for (Integer id : dbh.getScenarios().keySet()) {

			String sql = String.format("INSERT OR IGNORE INTO %s (%s, %s, %s) VALUES (?,?,?)", ProgressTableSchema.NAME,
					ProgressTableSchema.Cols.idNumber, ProgressTableSchema.Cols.completed,
					ProgressTableSchema.Cols.difficulty);

			try (Connection conn = dbh.getDb().connect(); PreparedStatement pst = conn.prepareStatement(sql)) {
				pst.setInt(1, dbh.getScenarios().get(id).getId());

				pst.setInt(2, dbh.getScenarios().get(id).isCompleted());

				pst.setString(3, dbh.getScenarios().get(id).getDifficulty());
				pst.executeUpdate();

				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
