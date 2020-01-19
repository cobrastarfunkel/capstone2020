package databse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import Scenarios.Scenario;
import database.AppSchemas.ScenariosTableSchema;
import database.SqliteDatabase;

public class SqliteDatabseTest {
	private String dbName = "gradleDb.sqlite";
	private SqliteDatabase testDb;
	private String testFileLoc = "C:\\eclipse\\work_space\\capstone\\capstone-project-conspicuous\\gradle_init\\src\\main\\resources\\databases";
	private String testFileName = "TestFile.rtf";

	@Test
	public void testDbCreated() {
		testDb = new SqliteDatabase(dbName);
		File dbFile = new File(testDb.getPath());
		assertTrue("Database Should be created", dbFile.exists());

	}

	@Test
	public void testScenarioObject() {
		Scenario sc = new Scenario("TEST", "C:\\users");
		assertEquals("TEST", sc.getName());
		assertEquals("C:\\users", sc.getPath());

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
		Scenario sc = new Scenario(testFileName, testFileLoc);
		testDb.getScenarioTable().createScenariosTable();
		testDb.getScenarioTable().insertScenarios(sc);

		String sql = "SELECT * FROM scenarios";
		testDb.getScenarioTable().selectDb(sql);

	}

}
