package tables;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.AppSchemas.LoginTableSchema;
import database.DatabaseHelper;
import database.SqliteDatabase;

@SuppressWarnings("unused")
public class LoginsTable {

	private DatabaseHelper dbh;
	
	public LoginsTable(SqliteDatabase db) {
		this.dbh = new DatabaseHelper(db);
		createLoginsTable();
	}
	
	private void createLoginsTable() {
		String sql = String.format(
				"CREATE TABLE IF NOT EXISTS %s (%s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL);",
				LoginTableSchema.NAME, LoginTableSchema.Cols.username, LoginTableSchema.Cols.password,
				LoginTableSchema.Cols.salt);
		
		dbh.createTable(sql);
	}
}
