package databaseTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import scenarios.ScenarioHelper;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseTest {
	private String dbName = "junitDB.sqlite";
	private SqliteDatabase sqDB = new SqliteDatabase(dbName);
	private String testPath = new File("src\\main\\resources\\databases\\").getAbsolutePath();
	private ScenarioHelper sch = new ScenarioHelper();

	@Test
	public void testAdbCreated() {
		sqDB.createDatabase();
		assertEquals(testPath + "\\" + dbName, sqDB.getPath());
		assertEquals("jdbc:sqlite:" + testPath + "\\" + dbName, sqDB.getUrl());
		assertTrue("DB Exists", new File(sqDB.getPath()).exists());
		sqDB.createTables();
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
		String sql = "SELECT * FROM scenarios";

		try (Connection conn = sqDB.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				if (rs.getInt("idNumber") == 8) {
					assertTrue(rs.getString("scName").contentEquals("Test Scenario 1"));
				} else if (rs.getInt("idNumber") == 9) {
					assertTrue(rs.getString("scName").contentEquals("Unit Test Scenario"));
				}
			}
		} catch (Exception e) {

		}
	}

	@Test
	public void testDmalwareTable() {
		String sql = "SELECT * FROM malware";

		try (Connection conn = sqDB.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				System.out.println("ID: " + rs.getInt("idNumber"));
				if (rs.getInt("idNumber") == 10) {
					File tempFile = sch.convertBytesToTempFile(rs.getBytes("dMalware"));
					sqDB.getSecretKey().decryptFile(tempFile);
					sch.executeFile(tempFile, "powershell", "");
					assertTrue("C:\\cpptest.txt Should be created", new File("C:\\cpptest.txt").exists());

					// Temp file is removed after each run
					tempFile = sch.convertBytesToTempFile(rs.getBytes("dMalware"));
					sqDB.getSecretKey().decryptFile(tempFile);
					sch.executeFile(tempFile, "c++", "reset");
					assertFalse("C:\\cpptest.txt Should be deleted", new File("C:\\cpptest.txt").exists());
				} else {

				}
			}
		} catch (Exception e) {

		}
	}

}
