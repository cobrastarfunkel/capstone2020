package scenarios;

import java.io.File;
import java.util.HashMap;

public class Scenario {

	public static final String CONFIG_PATH = new File("src\\main\\resources\\configFiles\\").getAbsolutePath();
	public static final String DEPLOY_PATH = new File("src\\main\\resources\\scenarioFiles\\").getAbsolutePath();

	private String name, language, type, deploy_file, reset_file;
	private HashMap<String, String> documents;
	private int id;
	private boolean completed;

	public String getName() {
		return name;
	}

	public String getDeploy_file() {
		return deploy_file;
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

	public HashMap<String, String> getDocuments() {
		return documents;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	@Override
	public String toString() {

		return String.format("ID: %d\nName: %s\nDeploy: %s\nReset: %s\nLanguage: %s", this.id, this.name,
				this.deploy_file, this.reset_file, this.language);
	}
}
