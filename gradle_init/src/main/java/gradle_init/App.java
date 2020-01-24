/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package gradle_init;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.SqliteDatabase;

public class App {

	public static void main(String[] args) {
		SqliteDatabase sqliteDB = new SqliteDatabase("filesDb.sqlite");

		String sql = "SELECT * FROM scenarios";

		try (Connection conn = sqliteDB.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				System.out.println(rs.getString("filename") + "\t" + rs.getString("content"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
