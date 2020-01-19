package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Scenarios.Scenario;
import database.AppSchemas.ScenariosTableSchema;

public class ScenariosTable {

	/**
	 * Create a scenarios Table Stores files for future use
	 */
	public static void createScenariosTable(SqliteDatabase db) {
		String sql = "CREATE TABLE IF NOT EXISTS " + ScenariosTableSchema.NAME + "(\n"
				+ ScenariosTableSchema.Cols.filename + " TEXT,\n" + ScenariosTableSchema.Cols.contentCol + " BLOB"
				+ ", UNIQUE(" + ScenariosTableSchema.Cols.filename + "," + ScenariosTableSchema.Cols.contentCol + "));";

		try (Connection conn = db.connect(); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
			conn.commit();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public static void insertScenarios(SqliteDatabase db, Scenario sc) {
		String sql = "INSERT OR IGNORE INTO " + ScenariosTableSchema.NAME + "(" + ScenariosTableSchema.Cols.filename
				+ "," + ScenariosTableSchema.Cols.contentCol + ") VALUES (?,?)";

		try (Connection conn = db.connect(); PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setString(1, sc.getName());
			pst.setBytes(2, sc.getStorableFile());
			pst.executeUpdate();
			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void selectDb(SqliteDatabase db, String sql) {

		try (Connection conn = db.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				System.out.println(rs.getString(ScenariosTableSchema.Cols.filename) + "\t"
						+ rs.getString(ScenariosTableSchema.Cols.contentCol));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
