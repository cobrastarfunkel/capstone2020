package databse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import Scenarios.Scenario;
import database.AppSchemas.ScenariosTableSchema;
import database.SqliteDatabase;

public class SqliteDatabseTest {
	private String dbName = "gradleDb.sqlite";
	private SqliteDatabase testDb;

	private String testFileName = "scenario1.txt";

	@Test
	public void testDbCreated() {
		testDb = new SqliteDatabase(dbName);
		File dbFile = new File(testDb.getPath());
		assertTrue("Database Should be created", dbFile.exists());

	}

	@Test
	public void testScenarioObject() {
		Scenario sc = new Scenario("scenario1.txt");
		assertEquals("scenario1.txt", sc.getName());
		assertEquals(
				"C:\\eclipse\\work_space\\capstone\\capstone-project-conspicuous\\gradle_init\\src\\main\\resources\\scenarios\\scenario1.txt",
				sc.getPath());

	}

	@Test
	public void testSchemas() {
		assertEquals("scenarios", ScenariosTableSchema.NAME);
		assertEquals("filename", ScenariosTableSchema.Cols.filename);
		assertEquals("content", ScenariosTableSchema.Cols.contentCol);
	}

	@Test
	public void testScenariosTableCreated() {
		testDb = new SqliteDatabase(dbName);

		String sql = "SELECT filename FROM scenarios";

		try (Connection conn = testDb.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
