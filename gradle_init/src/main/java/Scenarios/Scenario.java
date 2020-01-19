package Scenarios;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.AppSchemas.ScenariosTableSchema;
import database.SqliteDatabase;

public class Scenario {
	private String name;
	private String path;
	private byte[] storableFile;

	public Scenario() {
		name = "";
		path = "";
	}

	public Scenario(String name, String path) {
		this.name = name;
		this.path = path;
		storableFile = convertFiletoByteArray(path.concat("\\" + this.name));
	}

	/**
	 * Converts Files to a Byte array to store it in the Database
	 * 
	 * @param file String: Path to the File that needs to be converted.
	 * @return bs byte[]: Byte array of the File
	 */
	private byte[] convertFiletoByteArray(String file) {
		ByteArrayOutputStream bs = null;
		try {
			File scFile = new File(file);
			FileInputStream fis = new FileInputStream(scFile);
			byte[] buffer = new byte[1024];
			bs = new ByteArrayOutputStream();
			for (int len; (len = fis.read(buffer)) != -1;) {
				bs.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return bs != null ? bs.toByteArray() : null;
	}

	public String retrieveFile(SqliteDatabase db, String sql, String filename) {
		String fileContents = "";
		ResultSet rs = null;
		FileOutputStream fs = null;
		Connection conn = null;
		PreparedStatement pst = null;

		try {
			conn = db.connect();
			pst = conn.prepareStatement(sql);
			pst.setString(1, filename);
			rs = pst.executeQuery();

			File file = new File(filename);
			fs = new FileOutputStream(file);

			while (rs.next()) {
				InputStream input = rs.getBinaryStream(ScenariosTableSchema.Cols.contentCol);
				byte[] buffer = new byte[1024];
				while (input.read(buffer) > 0) {
					fs.write(buffer);
				}
			}
		} catch (SQLException | IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}

				if (conn != null) {
					conn.close();
				}
				if (fs != null) {
					fs.close();
				}

			} catch (SQLException | IOException e) {
				System.out.println(e.getMessage());
			}
		}

		return fileContents;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public byte[] getStorableFile() {
		return storableFile;
	}

}
