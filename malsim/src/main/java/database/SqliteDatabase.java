/**
 * Class to create a SQLite database.
 * 
 * @version 1.0
 * 
 * @author Ian Cobia
 */

package database;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.crypto.NoSuchPaddingException;

import encryption.SymmetricKey;
import tables.DocumentsTable;
import tables.MalwareTable;
import tables.ProgressTable;
import tables.ScenariosTable;
import tables.TraitsTable;

public class SqliteDatabase {
	public static final String PATH = new File("src\\main\\resources\\databases\\").getAbsolutePath();

	private String dbFileName, path, url;
	private SymmetricKey secretKey;

	/**
	 * Constructor
	 * 
	 * Will set the name of the DB, the path to be located in the project under
	 * /src/main/resources/databases, and the Database type.
	 * 
	 * @param dbName String: Name of the database to be created
	 */
	public SqliteDatabase(String dbName) {
		try {
			this.secretKey = new SymmetricKey("34erdfcv#$ERDFCV");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		this.dbFileName = dbName;

		// Where the database is stored
		path = new File(PATH + "\\" + this.dbFileName).getAbsolutePath();

		// jdbc connector for sqlite
		url = "jdbc:sqlite:" + path;
	}

	/*
	 * Create a Database
	 */
	public void createDatabase() {

		// Create the database if it doesn't exist and print some info to console
		try (Connection conn = DriverManager.getConnection(this.url)) {
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println(meta.getConnection());
				System.out.println("Database Connected");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Helper method to Connect to DB
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

	public void createTables() {
		new ScenariosTable(this);
		new MalwareTable(this);
		new DocumentsTable(this);
		new ProgressTable(this);
		new TraitsTable(this);
	}

	public SymmetricKey getSecretKey() {
		return secretKey;
	}

	public String getUrl() {
		return url;
	}

	public String getDbName() {
		return dbFileName;
	}

	public String getPath() {
		return path;
	}
}
