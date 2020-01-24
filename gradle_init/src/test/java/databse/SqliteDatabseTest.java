package databse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.junit.Test;

import Scenarios.Scenario;
import database.AppSchemas.ScenariosTableSchema;
import database.SqliteDatabase;

public class SqliteDatabseTest {
	private String dbName = "gradleDb.sqlite";
	private SqliteDatabase testDb;

	@Test
	public void testScenarioObject() {
		Scenario testScenario = new Scenario("scenario1.txt");
		assertEquals("scenario1.txt", testScenario.getName());
		assertEquals(Scenario.PATH, testScenario.getPath());
		assertTrue("File should be byte array", testScenario.getStorableFile().getClass().equals(byte[].class));
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
		File dbFile = new File(testDb.getPath());

		assertTrue("Database Should be created", dbFile.exists());

		HashMap<String, byte[]> scenarios = testDb.getScenarioTable().getScenariosHashMap();

		String sql = String.format("SELECT * FROM %s", ScenariosTableSchema.NAME);

		try (Connection conn = testDb.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				// If the files are removed this will fail because the hashmap wont be made but
				// the DB content will still be there
				assertTrue(scenarios.containsKey(rs.getString("filename")));

				// TODO: Covert file contents back from byte array to String and compare
			}
		} catch (SQLException e) {
			fail(e.toString());
		}
	}

}
