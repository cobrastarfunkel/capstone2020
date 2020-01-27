package database;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import database.AppSchemas.ScenariosTableSchema;
import scenarios.Scenario;
import scenarios.ScenarioBuilder;
import scenarios.ScenarioHelper;

public class ScenariosTable {

	private SqliteDatabase db;
	private HashMap<Integer, Scenario> scenarios = new HashMap<Integer, Scenario>();
	private ScenarioBuilder scb = new ScenarioBuilder();

	public ScenariosTable(SqliteDatabase db) {
		this.db = db;
		createScenariosTable();
	}

	/**
	 * Create a scenarios Table, Stores files for future use
	 */
	private void createScenariosTable() {
		// SQL to create the Table if it doesn't exist
		String sql = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER, %s BLOB, UNIQUE(%s));",
				ScenariosTableSchema.NAME, ScenariosTableSchema.Cols.idNumber, ScenariosTableSchema.Cols.content,
				ScenariosTableSchema.Cols.idNumber);

		// Connect to DB then execute the SQL
		try (Connection conn = db.connect(); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
			conn.commit();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		loadScenarios();
	}

	private void loadScenarios() {
		this.scenarios = scb.loadScenarios();
		insertScenariosIntoTable();
	}

	/**
	 * Insert Scenarios into the Database
	 * 
	 * @param sc Scenario: The Scenario to add
	 */
	private void insertScenariosIntoTable() {
		ScenarioHelper sch = new ScenarioHelper();
		// Loop through loaded Scenarios
		for (Integer id : this.scenarios.keySet()) {

			String sql = String.format("INSERT OR IGNORE INTO %s (%s,%s) VALUES (?,?)", ScenariosTableSchema.NAME,
					ScenariosTableSchema.Cols.idNumber, ScenariosTableSchema.Cols.content);

			// Connect to the DB and use a prepared statement to insert Scenario into Table
			try (Connection conn = db.connect(); PreparedStatement pst = conn.prepareStatement(sql)) {

				// filename column
				pst.setInt(1, scenarios.get(id).getId());

				// TODO: convert to byte array, store in DB
				File scenarioFile = sch.getScenarioFile(scenarios.get(id).getDeploy_file());

				db.getSecretKey().encryptFile(scenarioFile);

				// content column
				pst.setBytes(2, sch.convertFileToBytes(scenarioFile.toPath()));
				pst.executeUpdate();

				// Commit because auto commit is disabled by the connection method in
				// SqliteDatabse
				conn.commit();

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
		}
	}

	public HashMap<Integer, Scenario> getScenariosHashMap() {
		return scenarios;
	}

}
