package tables;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.AppSchemas.DocumentTableSchema;
import database.DatabaseHelper;
import database.SqliteDatabase;
import scenarios.ScenarioHelper;

public class DocumentsTable {

	private DatabaseHelper dbh;
	private ScenarioHelper sch;

	public DocumentsTable(SqliteDatabase db) {
		this.dbh = new DatabaseHelper(db);
		this.sch = new ScenarioHelper();
		createDocumentsTable();
	}

	private void createDocumentsTable() {
		String sql = String.format(
				"CREATE TABLE IF NOT EXISTS %s (%s INTEGER NOT NULL, %s INTEGER PRIMARY KEY, %s BLOB NOT NULL, UNIQUE(%s));",
				DocumentTableSchema.NAME, DocumentTableSchema.Cols.idNumber, DocumentTableSchema.Cols.dIdNumber,
				DocumentTableSchema.Cols.document, DocumentTableSchema.Cols.idNumber);

		dbh.createTable(sql);
		insertDocumentIntoTable();

	}

	private void insertDocumentIntoTable() {
		for (Integer id : dbh.getScenarios().keySet()) {

			String sql = String.format("INSERT OR IGNORE INTO %s (%s, %s) VALUES (?,?)", DocumentTableSchema.NAME,
					DocumentTableSchema.Cols.idNumber, DocumentTableSchema.Cols.document);

			try (Connection conn = dbh.getDb().connect(); PreparedStatement pst = conn.prepareStatement(sql)) {
				pst.setInt(1, dbh.getScenarios().get(id).getId());

				File scenarioFile = sch.getScenarioFile(this.dbh.getScenarios().get(id).getDocuments());

				pst.setBytes(2, sch.convertFileToBytes(scenarioFile.toPath()));
				pst.executeUpdate();

				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
