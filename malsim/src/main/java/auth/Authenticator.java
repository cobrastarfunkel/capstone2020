package auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.AppSchemas.LoginTableSchema;
import database.SqliteDatabase;

@SuppressWarnings("unused")
public class Authenticator {

	private String username;
	private String password;
	SqliteDatabase db;

	public Authenticator(String username, String password) {
		this.username = username;
		this.password = password;
		this.db = new SqliteDatabase("guidb.sqlite");
	}

	public boolean register() {
		if (!userExists()) {
			byte[] salt;
			try {
				salt = getSalt();

				String hashedPw = getHashedPassword(this.password, salt);

				String sql = String.format("INSERT OR IGNORE INTO %s (%s, %s, %s) VALUES (?,?,?)",
						LoginTableSchema.NAME, LoginTableSchema.Cols.username, LoginTableSchema.Cols.password,
						LoginTableSchema.Cols.salt);

				try (Connection conn = this.db.connect(); PreparedStatement pst = conn.prepareStatement(sql)) {

					pst.setString(1, this.username);
					pst.setString(2, hashedPw);
					pst.setBytes(3, salt);

					pst.executeUpdate();
					// Getting here
					System.out.println("In register in try pre commit");
					conn.commit();
					return true;

				} catch (SQLException e) {
					e.printStackTrace();
					System.exit(0);
				}
			} catch (NoSuchAlgorithmException | NoSuchProviderException e1) {
				e1.printStackTrace();
				return false;
			}
		}
		return false;
	}

	private boolean userExists() {
		String sql = "SELECT username FROM logins;";
		try (Connection conn = db.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				if (rs.getString("username").toLowerCase().equals(this.username.toLowerCase())) {
					return true;
				}
			}
		} catch (Exception e) {
			System.out.println("Exception");
			return false;
		}
		return false;
	}

	public boolean login() {
		String salt;

		if (userExists()) {
			String sql = "SELECT * FROM logins;";
			try (Connection conn = db.connect();
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(sql)) {

				while (rs.next()) {
					if (this.username.equals(rs.getString("username")))
						if (getHashedPassword(this.password, rs.getBytes("salt"))
								.contentEquals(rs.getString("password")))
							return true;
				}

				return false;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public String getHashedPassword(String passwordToHash, byte[] salt) {
		String securePassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(salt);

			byte[] bytes = md.digest(passwordToHash.getBytes());

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < bytes.length; i++)
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));

			securePassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return securePassword;
	}

	// Add salt
	public byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");

		byte[] salt = new byte[16];
		sr.nextBytes(salt);

		return salt;
	}
}
