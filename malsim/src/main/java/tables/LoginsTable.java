package tables;

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
				"CREATE TABLE IF NOT EXISTS %s (%s TEXT PRIMARY KEY, %s TEXT NOT NULL, %s TEXT NOT NULL, UNIQUE(%s));",
				LoginTableSchema.NAME, LoginTableSchema.Cols.username, LoginTableSchema.Cols.password,
				LoginTableSchema.Cols.salt, LoginTableSchema.Cols.username);

		dbh.createTable(sql);
	}
}
