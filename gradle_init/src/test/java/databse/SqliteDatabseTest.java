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

		HashMap<String, byte[]> scenarios = testDb.getScenarioTable().getScs();

		String sql = "SELECT * FROM scenarios";

		try (Connection conn = testDb.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				assertTrue(scenarios.containsKey(rs.getString("filename")));

				// TODO: Covert file contents back from byte array to String
				// assertTrue(scenarios.get(rs.getString("filename")).toString().equals(rs.getString("content")));
			}
		} catch (SQLException e) {
			fail(e.toString());
		}

	}

}
