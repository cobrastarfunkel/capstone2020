package models;

public class ScenarioModel {
	private String name;
	private Integer id;
	private byte[] scDoc;
	private byte[] scenario;
	private String difficulty;

	public ScenarioModel(Integer idNumber, String name, byte[] file, byte[] scenario, String difficulty) {
		this.id = idNumber;
		this.name = name;
		this.scDoc = file;
		this.scenario = scenario;
		this.difficulty = difficulty;
	}

	public void setScDoc(byte[] scDoc) {
		this.scDoc = scDoc;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setScenario(byte[] scenario) {
		this.scenario = scenario;
	}

	public String getName() {
		return name;
	}

	public Integer getId() {
		return id;
	}

	public byte[] getScFile() {
		return scDoc;
	}

	public byte[] getScenario() {
		return scenario;
	}

	public byte[] getScDoc() {
		return scDoc;
	}

	public String getDifficulty() {
		return difficulty;
	}

}
