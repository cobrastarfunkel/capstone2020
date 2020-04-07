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

import database.DatabaseHelper;
import database.SqliteDatabase;
import database.AppSchemas.LoginTableSchema;

@SuppressWarnings("unused")
public class Authenticator {

	private String username;
	private String password;
	SqliteDatabase db;

	public Authenticator(String username, String password, SqliteDatabase db) {
		this.username = username;
		this.password = password;
		this.db = db;
	}
	
	public void register() throws NoSuchAlgorithmException, NoSuchProviderException {
		if(!userExists()) {
			byte[] salt = getSalt();
			String hashedPw = getHashedPassword(this.password, salt);
			
			String sql = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?,?,?)", LoginTableSchema.NAME,
					LoginTableSchema.Cols.username, LoginTableSchema.Cols.password, LoginTableSchema.Cols.salt);

			try (Connection conn = db.connect(); PreparedStatement pst = conn.prepareStatement(sql)) {
				pst.setString(1, this.username);
				pst.setString(2, hashedPw);
				pst.setBytes(3, salt);
				
				pst.executeUpdate();

				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	
	private boolean userExists() {
		String sql = "SELECT * FROM logins;";
		try {
			System.out.println(this.username);
			Statement stmt;
			ResultSet rs;
			stmt = db.connect().createStatement();
			rs = stmt.executeQuery(sql);
			System.out.println("made it this far");
			while(rs.next()) {
				System.out.println(rs.getString("username"));
				if(rs.getString("username").toLowerCase().equals(this.username.toLowerCase()))
					return true;
			}
			
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	public boolean login() {
		String salt;
		
		if(userExists()) {
			String sql = "SELECT * FROM logins;";
			try {
				Statement stmt;
				ResultSet rs;
				stmt = db.connect().createStatement();
				rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
					if(this.username.equals(rs.getString("username")))
						if(getHashedPassword(this.password, rs.getBytes("salt")).contentEquals(rs.getString("password")))
							return true;
				}
				
				return false;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/*
	 * public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {
	 * String passwordToHash = "password";
	 * byte[] salt = getSalt();
	 * 
	 * String securePassword = getSecurePassword(passwordToHash, salt);
	 * System.out.println(securePassword); //Prints 83ee5baeea20b6c21635e4ea67847f66
	 * 
	 * String regeneratedPassowrdToVerify = getSecurePassword(passwordToHash, salt);
	 * System.out.println(regeneratedPassowrdToVerify); //Prints 83ee5baeea20b6c21635e4ea67847f66
	 * }
	 */

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
