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
		String sql = "CREATE TABLE IF NOT EXISTS " + ScenariosTableSchema.NAME + "(\n"
				+ ScenariosTableSchema.Cols.filename + " TEXT,\n" + ScenariosTableSchema.Cols.contentCol + " BLOB"
				+ ", UNIQUE(" + ScenariosTableSchema.Cols.filename + "," + ScenariosTableSchema.Cols.contentCol + "));";

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
		var dirName = Paths.get(Scenario.PATH);

		try (var paths = Files.newDirectoryStream(dirName)) {
			for (Path filename : paths) {
				String[] splitPaths = filename.toString().split("\\\\");
				Scenario tempScenario = new Scenario(splitPaths[splitPaths.length - 1]);
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
		for (Scenario scenario : this.scs) {

			String sql = "INSERT OR IGNORE INTO " + ScenariosTableSchema.NAME + "(" + ScenariosTableSchema.Cols.filename
					+ "," + ScenariosTableSchema.Cols.contentCol + ") VALUES (?,?)";

			try (Connection conn = db.connect(); PreparedStatement pst = conn.prepareStatement(sql)) {
				pst.setString(1, scenario.getName());
				pst.setBytes(2, scenario.getStorableFile());
				pst.executeUpdate();
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
