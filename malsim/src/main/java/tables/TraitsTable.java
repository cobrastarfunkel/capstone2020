package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.AppSchemas.TraitsTableSchema;
import database.DatabaseHelper;
import database.SqliteDatabase;

public class TraitsTable {

	private DatabaseHelper dbh;

	public TraitsTable(SqliteDatabase db) {
		this.dbh = new DatabaseHelper(db);
		createTraitsTable();
	}

	private void createTraitsTable() {
		String sql = String.format(
				"CREATE TABLE IF NOT EXISTS %s (%s INTEGER NOT NULL, %s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, UNIQUE(%s));",
				TraitsTableSchema.NAME, TraitsTableSchema.Cols.idNumber, TraitsTableSchema.Cols.tIdNumber,
				TraitsTableSchema.Cols.language, TraitsTableSchema.Cols.os, TraitsTableSchema.Cols.scType,
				TraitsTableSchema.Cols.idNumber);

		dbh.createTable(sql);
		insertTraitsIntoTable();
	}

	private void insertTraitsIntoTable() {
		for (Integer id : dbh.getScenarios().keySet()) {

			String sql = String.format("INSERT OR IGNORE INTO %s (%s, %s, %s, %s) VALUES (?,?,?,?);",
					TraitsTableSchema.NAME, TraitsTableSchema.Cols.idNumber, TraitsTableSchema.Cols.language,
					TraitsTableSchema.Cols.os, TraitsTableSchema.Cols.scType);

			try (Connection conn = dbh.getDb().connect(); PreparedStatement pst = conn.prepareStatement(sql)) {
				pst.setInt(1, dbh.getScenarios().get(id).getId());

				pst.setString(2, dbh.getScenarios().get(id).getLanguage());
				pst.setString(3, dbh.getScenarios().get(id).getOs());
				pst.setString(4, dbh.getScenarios().get(id).getType());
				pst.executeUpdate();

				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
