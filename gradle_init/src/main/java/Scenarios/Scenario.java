package Scenarios;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Scenario {
	private String filename;
	private String path;
	private byte[] storableFile;

	/**
	 * Constructor
	 * 
	 * @param name String: FileName of the scenario
	 * @param path String: Path to where the file is stored
	 */
	public Scenario(String name) {
		this.filename = name;
		this.path = new File("src\\main\\resources\\scenarioFiles\\" + this.filename).getAbsolutePath();
		storableFile = convertFiletoByteArray(path);
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

	public String getName() {
		return filename;
	}

	public String getPath() {
		return path;
	}

	public byte[] getStorableFile() {
		return storableFile;
	}

}
