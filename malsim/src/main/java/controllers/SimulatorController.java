package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import conditions.MacFinder;
import database.SqliteDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import models.ScenarioModel;
import scenarios.ScenarioHelper;

/**
 *
 * @author Layne
 */

public class SimulatorController implements Initializable {

	// These items are for the list of scenarios
	@FXML
	private Label scenarioLabel;
	
	@FXML
	private ListView<String> listViewMain;
	
	@FXML
	private Button openButton;
	
	@FXML
	private ImageView scenarioDocImage;

	@FXML
	private Circle vmIndicator;

	private ObservableList<String> items = FXCollections.observableArrayList();
	private HashMap<Integer, ScenarioModel> scenarios;

	private SqliteDatabase sqliteDB;
	private static ScenarioModel selectedScenario;
	private ScenarioHelper sch = new ScenarioHelper();

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		vmIndicator.setStroke(Color.BLACK);
		vmIndicator.setStrokeWidth(1.5);

		if (MacFinder.isVMMac()) {
			vmIndicator.setFill(Color.GREEN);
		} else {
			vmIndicator.setFill(Color.RED);
		}

		new FXMLLoader(getClass().getResource("/fxmlFiles/SimulatorView.fxml"));

		String scString;
		scenarios = new HashMap<Integer, ScenarioModel>();

		sqliteDB = new SqliteDatabase("guidb.sqlite");

		String sql = "select scenarios.idNumber, scenarios.scName, progress.completed, scenarioTraits.scType, "
				+ "documents.document, malware.dMalware, progress.difficulty\r\n" + "from scenarios\r\n"
				+ "join progress on scenarios.idNumber = progress.idNumber\r\n"
				+ "join documents on scenarios.idNumber = documents.idNumber\r\n"
				+ "join malware on scenarios.idNumber = malware.idNumber\r\n"
				+ "join scenarioTraits on scenarios.idNumber = scenarioTraits.idNumber\r\n";

		try (Connection conn = sqliteDB.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				// Just formatting for the listView, should probably change to a tableView
				scString = (rs.getInt("completed") == 0) ? "\t\t\tIncomplete" : "\t\t\tCompleted";
				items.add(rs.getString("scName") + "\t\t\t" + rs.getString("difficulty") + scString);

				scenarios.put(items.size() - 1,
						new ScenarioModel(rs.getInt("idNumber"), rs.getString("scName"), rs.getBytes("document"),
								rs.getBytes("dMalware"), rs.getString("difficulty"), rs.getString("scType")));
			}
		} catch (Exception e) {

		}
		listViewMain.setItems(items);
	}

	@FXML
	public void handleMouseClick(MouseEvent arg0) throws IOException {
		selectedScenario = scenarios.get(listViewMain.getSelectionModel().getSelectedIndex());

	}

	@FXML
	void openScenario(ActionEvent event) {
		if (selectedScenario == null) {
			selectedScenario = scenarios.get(0);
		}
		ScenarioModel scModel = new ScenarioModel(selectedScenario.getId(), selectedScenario.getName(),
				selectedScenario.getScDoc(), selectedScenario.getScenario(), selectedScenario.getDifficulty(),
				selectedScenario.getType());
		scModel.setSqliteDb(sqliteDB);

		if (scModel.getId() == 10) {

			try {
				File tempFile = sch.convertBytesToFile(sqliteDB.getSecretKey().decryptBytes(scModel.getScenario()));
				sch.executeFile(tempFile, "powershell", "");
			} catch (IOException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException
					| InterruptedException e) {
				e.printStackTrace();
			}

		}

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFiles/ScenarioView.fxml"));
			loader.setController(new ScenarioViewController(scModel));
			Parent root = loader.load();

			Stage scenarioStage = (Stage) listViewMain.getScene().getWindow();

			// May not be good for other monitors, check on different resolutions
			scenarioStage.setX(700);
			scenarioStage.setY(200);

			scenarioStage.setTitle(selectedScenario.getName());
			scenarioStage.setScene(new Scene(root));
			scenarioStage.show();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
