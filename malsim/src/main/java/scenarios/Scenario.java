package scenarios;

import java.io.File;

public class Scenario {

	public static final String CONFIG_PATH = new File("src\\main\\resources\\configFiles\\").getAbsolutePath();
	public static final String DEPLOY_PATH = new File("src\\main\\resources\\scenarioFiles\\").getAbsolutePath();

	private String language, type, reset_file, os;
	private String dMalware, scName;

	private String documents;
	private int id;

	// sqlite doesn't have a bool data type 1=complete 0=not
	private int completed;

	public String getdMalware() {
		return dMalware;
	}

	public String getScName() {
		return scName;
	}

	public String getDeploy_file() {
		return dMalware;
	}

	public String getReset_file() {
		return reset_file;
	}

	public String getLanguage() {
		return language;
	}

	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getDocuments() {
		return documents;
	}

	public int isCompleted() {
		return completed;
	}

	public void setCompleted(int completed) {
		this.completed = completed;
	}

	public String getOs() {
		return os;
	}

	@Override
	public String toString() {

		return String.format("ID: %d\nName: %s\nDeploy: %s\nReset: %s\nLanguage: %s\n" + "Document: %s", this.id,
				this.scName, this.dMalware, this.reset_file, this.language, this.documents);
	}
}
