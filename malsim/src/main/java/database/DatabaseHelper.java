package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import scenarios.Scenario;
import scenarios.ScenarioBuilder;

/**
 * Used to Build out tables in the database. The Scenarios are initially turned
 * into Java Objects (by the ScenarioBuilder class) and then stored as a
 * HashMap. Next the Scenarios are added to the database when createDatabase
 * from SqliteDatabase class is called
 * 
 * @author Ian Cobia
 *
 */
public class DatabaseHelper {

	private SqliteDatabase db;
	private HashMap<Integer, Scenario> scenarios = new HashMap<Integer, Scenario>();
	private ScenarioBuilder scb = new ScenarioBuilder();

	public DatabaseHelper(SqliteDatabase db) {
		this.db = db;
	}

	public void createTable(String sql) {
		// Connect to DB then execute the SQL
		try (Connection conn = this.db.connect(); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
			conn.commit();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public HashMap<Integer, Scenario> getScenarios() {
		scenarios = scb.getScenarios();
		return scenarios;
	}

	public SqliteDatabase getDb() {
		return db;
	}

}
