package databaseTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import database.SqliteDatabase;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseTest {
	private String dbName = "junitDB.sqlite";
	private SqliteDatabase sqDB = new SqliteDatabase(dbName);
	private String testPath = new File("src\\main\\resources\\databases\\").getAbsolutePath();

	@Test
	public void testAdbCreated() {
		sqDB.createDatabase();
		assertEquals(testPath + "\\" + dbName, sqDB.getPath());
		assertEquals("jdbc:sqlite:" + testPath + "\\" + dbName, sqDB.getUrl());
		assertTrue("DB Exists", new File(sqDB.getPath()).exists());

	}

	@Test
	public void testBdbConnect() {
		String sql = "SELECT scName FROM scenarios";
		ArrayList<String> scNames = new ArrayList<String>();

		try (Connection conn = sqDB.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				scNames.add(rs.getString("scName"));
			}

			assertTrue(scNames.contains("Test Scenario 1"));

		} catch (Exception e) {

		}
	}

	@Test
	public void testCscenarioTable() {

	}

}
