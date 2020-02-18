package models;

import database.SqliteDatabase;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class ScenarioModel {
	private String name;
	private Integer id;
	private String type;
	private byte[] scDoc;
	private byte[] scenario;
	private String difficulty;
	private SqliteDatabase sqliteDb;

	@FXML
	private ImageView image_class;

	public ScenarioModel(Integer idNumber, String name, byte[] file, byte[] scenario, String difficulty, String type) {
		this.id = idNumber;
		this.name = name;
		this.scDoc = file;
		this.scenario = scenario;
		this.difficulty = difficulty;
		this.type = type;
		setImage_class(type);
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ImageView getImage_class() {
		return image_class;
	}

	public void setImage_class(String type) {

	}

	public SqliteDatabase getSqliteDb() {
		return sqliteDb;
	}

	public void setSqliteDb(SqliteDatabase sqliteDb) {
		this.sqliteDb = sqliteDb;
	}

}
