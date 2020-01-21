package databse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.junit.Test;

import database.AppSchemas.ScenariosTableSchema;
import database.SqliteDatabase;

public class SqliteDatabseTest {
	private String dbName = "gradleDb.sqlite";
	private SqliteDatabase testDb;

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
				assertTrue(scenarios.containsKey(rs.getString("filename")));

				// Shouldn't match because the version stored in Scenarios is a byte[] the DB
				// returns a String
				assertFalse(scenarios.get(rs.getString("filename")).equals(rs.getString("content")));

				// TODO: Covert file contents back from byte array to String and compare
			}
		} catch (SQLException e) {
			fail(e.toString());
		}

	}

}
