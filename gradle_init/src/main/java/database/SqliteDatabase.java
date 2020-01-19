/**
 * Class to create a SQLite database.
 * 
 * @version 1.0
 * 
 * @author Ian Cobia
 */

package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteDatabase {

	private String dbName, path, url;
	private ScenariosTable scenarioTable;

	/**
	 * Constructor
	 * 
	 * Will set the name of the DB, the path to be located in the project under
	 * /src/main/resources/databases, and the Database type.
	 * 
	 * @param dbName String: Name of the database to be created
	 */
	public SqliteDatabase(String dbName) {
		this.dbName = dbName;
		path = new File("src\\main\\resources\\databases\\" + this.dbName).getAbsolutePath();
		url = "jdbc:sqlite:" + path;
		scenarioTable = new ScenariosTable(this);
		createDatabase();
	}

	/*
	 * Create a Database
	 */
	private void createDatabase() {

		try (Connection conn = DriverManager.getConnection(this.url)) {
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("Database Connected");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Connect to DB
	 * 
	 * @return Connection conn: Connection to DB
	 */
	public Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public String getUrl() {
		return url;
	}

	public String getDbName() {
		return dbName;
	}

	public String getPath() {
		return path;
	}

	public ScenariosTable getScenarioTable() {
		return scenarioTable;
	}
}
