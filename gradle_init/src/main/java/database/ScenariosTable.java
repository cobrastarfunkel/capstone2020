package database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Scenarios.Scenario;
import database.AppSchemas.ScenariosTableSchema;

public class ScenariosTable {

	private SqliteDatabase db;
	private ArrayList<Scenario> scs = new ArrayList<Scenario>();

	public ScenariosTable(SqliteDatabase db) {
		this.db = db;
		createScenariosTable();
	}

	/**
	 * Create a scenarios Table, Stores files for future use
	 */
	private void createScenariosTable() {
		// SQL to create the Table if it doesn't exist
		/*
		 * String sql = "CREATE TABLE IF NOT EXISTS " + ScenariosTableSchema.NAME +
		 * "(\n" + ScenariosTableSchema.Cols.filename + " TEXT,\n" +
		 * ScenariosTableSchema.Cols.contentCol + " BLOB" + ", UNIQUE(" +
		 * ScenariosTableSchema.Cols.filename + "," +
		 * ScenariosTableSchema.Cols.contentCol + "));";
		 */
		String sql = String.format("CREATE TABLE IF NOT EXISTS %s (%s TEXT, %s BLOB, UNIQUE(%s,%s));",
				ScenariosTableSchema.NAME, ScenariosTableSchema.Cols.filename, ScenariosTableSchema.Cols.contentCol,
				ScenariosTableSchema.Cols.filename, ScenariosTableSchema.Cols.contentCol);

		// Connect to DB then execute the SQL
		try (Connection conn = db.connect(); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
			conn.commit();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		loadScenarios();
	}

	/**
	 * Load Scenarios in an ArrayList from the directory specified in the Scenario
	 * Class
	 */
	private void loadScenarios() {
		// Get the Path to the files defined in the Scenario object
		var dirName = Paths.get(Scenario.PATH);

		// Make sure the path exists and set paths var to the files
		try (var paths = Files.newDirectoryStream(dirName)) {

			// Loop through files in paths
			for (Path filename : paths) {

				// Split on \ into String array so we can get the length
				String[] splitPaths = filename.toString().split("\\\\");

				// Take last element of String array and create a new Scenario with it
				Scenario tempScenario = new Scenario(splitPaths[splitPaths.length - 1]);

				// Add Scenario to ArrayList of Scenarios
				this.scs.add(tempScenario);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		insertScenarios();
	}

	/**
	 * Insert Scenarios into the Database
	 * 
	 * @param sc Scenario: The Scenario to add
	 */
	private void insertScenarios() {
		// Loop through loaded Scenarios
		for (Scenario scenario : this.scs) {

			// Inserts Scenario if both columns are Unique
			/*
			 * String sql = "INSERT OR IGNORE INTO " + ScenariosTableSchema.NAME + "(" +
			 * ScenariosTableSchema.Cols.filename + "," +
			 * ScenariosTableSchema.Cols.contentCol + ") VALUES (?,?)";
			 */

			String sql = String.format("INSERT OR IGNORE INTO %s (%s,%s) VALUES (?,?)", ScenariosTableSchema.NAME,
					ScenariosTableSchema.Cols.filename, ScenariosTableSchema.Cols.contentCol);

			// Connect to the DB and use a prepared statement to insert Scenario into Table
			try (Connection conn = db.connect(); PreparedStatement pst = conn.prepareStatement(sql)) {

				// filename column
				pst.setString(1, scenario.getName());

				// content column
				pst.setBytes(2, scenario.getStorableFile());
				pst.executeUpdate();

				// Commit because auto commit is disabled by the connection method in
				// SqliteDatabse
				conn.commit();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Scenario> getScs() {
		return scs;
	}

}
