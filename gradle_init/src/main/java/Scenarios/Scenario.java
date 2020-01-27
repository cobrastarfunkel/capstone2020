package Scenarios;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Scenario {
	// Path variable that can be called to get the list of Scenarios from this
	// directory
	public static final String PATH = new File("src\\main\\resources\\scenarioFiles\\").getAbsolutePath();

	private String filename, fileLoc;

	// Convert file to byte array for storage
	private byte[] storableFile;

	/**
	 * Constructor
	 * 
	 * @param name String: FileName of the scenario
	 * @param PATH String: Path to where the file is stored
	 */
	public Scenario(String name) {
		this.filename = name;
		this.fileLoc = PATH.concat("\\" + filename);
		storableFile = convertFiletoByteArray(fileLoc);
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
			fis.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		// If bs not null return byte array else return null
		return bs != null ? bs.toByteArray() : null;
	}

	public String getName() {
		return filename;
	}

	public String getPath() {
		return PATH;
	}

	public byte[] getStorableFile() {
		return storableFile;
	}

}
