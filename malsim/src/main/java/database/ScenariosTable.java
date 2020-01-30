package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import database.AppSchemas.ScenariosTableSchema;
import scenarios.Scenario;
import scenarios.ScenarioBuilder;

public class ScenariosTable {

	private SqliteDatabase db;
	private DatabaseHelper dbh = new DatabaseHelper();
	private HashMap<Integer, Scenario> scenarios = new HashMap<Integer, Scenario>();
	private ScenarioBuilder scb = new ScenarioBuilder();

	public ScenariosTable(SqliteDatabase db) {
		this.db = db;
		createScenariosTable();
	}

	private void createScenariosTable() {
		String sql = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER, %s TEXT, UNIQUE(%s,%s));",
				ScenariosTableSchema.NAME, ScenariosTableSchema.Cols.idNumber, ScenariosTableSchema.Cols.scName,
				ScenariosTableSchema.Cols.idNumber, ScenariosTableSchema.Cols.scName);

		dbh.createTable(sql, this.db);
		loadScenarios();
	}

	private void loadScenarios() {
		this.scenarios = scb.getScenarios();
		insertScenariosIntoTable();

	}

	private void insertScenariosIntoTable() {
		for (Integer id : this.scenarios.keySet()) {

			String sql = String.format("INSERT OR IGNORE INTO %s (%s, %s) VALUES (?,?)", ScenariosTableSchema.NAME,
					ScenariosTableSchema.Cols.idNumber, ScenariosTableSchema.Cols.scName);

			try (Connection conn = db.connect(); PreparedStatement pst = conn.prepareStatement(sql)) {
				pst.setInt(1, scenarios.get(id).getId());

				pst.setString(2, scenarios.get(id).getScName());
				pst.executeUpdate();

				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
