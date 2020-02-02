package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.AppSchemas.ScenariosTableSchema;
import database.DatabaseHelper;
import database.SqliteDatabase;

public class ScenariosTable {

	private DatabaseHelper dbh;

	public ScenariosTable(SqliteDatabase db) {
		this.dbh = new DatabaseHelper(db);
		createScenariosTable();
	}

	private void createScenariosTable() {
		String sql = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER, %s TEXT, UNIQUE(%s,%s));",
				ScenariosTableSchema.NAME, ScenariosTableSchema.Cols.idNumber, ScenariosTableSchema.Cols.scName,
				ScenariosTableSchema.Cols.idNumber, ScenariosTableSchema.Cols.scName);

		dbh.createTable(sql);
		insertScenariosIntoTable();
	}

	private void insertScenariosIntoTable() {
		for (Integer id : dbh.getScenarios().keySet()) {

			String sql = String.format("INSERT OR IGNORE INTO %s (%s, %s) VALUES (?,?)", ScenariosTableSchema.NAME,
					ScenariosTableSchema.Cols.idNumber, ScenariosTableSchema.Cols.scName);

			try (Connection conn = dbh.getDb().connect(); PreparedStatement pst = conn.prepareStatement(sql)) {
				pst.setInt(1, dbh.getScenarios().get(id).getId());

				pst.setString(2, dbh.getScenarios().get(id).getScName());
				pst.executeUpdate();

				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
