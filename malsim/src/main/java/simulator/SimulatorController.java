package simulator;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;

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
import javafx.stage.Stage;

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

	private ObservableList<String> items = FXCollections.observableArrayList();
	private HashMap<Integer, ScenarioModel> scenarios;

	private SqliteDatabase sqliteDB;
	private static ScenarioModel selectedScenario;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		new FXMLLoader(getClass().getResource("SimulatorView.fxml"));

		String scString;
		scenarios = new HashMap<Integer, ScenarioModel>();

		sqliteDB = new SqliteDatabase("guidb.sqlite");

		sqliteDB.createDatabase();
		sqliteDB.createTables();

		String sql = "select scenarios.idNumber, scenarios.scName, progress.completed, documents.document, malware.dMalware\r\n"
				+ "from scenarios\r\n" + "join progress on scenarios.idNumber = progress.idNumber\r\n"
				+ "join documents on scenarios.idNumber = documents.idNumber\r\n"
				+ "join malware on scenarios.idNumber = malware.idNumber";

		try (Connection conn = sqliteDB.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				scString = (rs.getInt("completed") == 0) ? "\t\tIncomplete" : "\t\tCompleted";
				items.add(rs.getString("scName") + scString);

				scenarios.put(items.size() - 1, new ScenarioModel(rs.getInt("idNumber"), rs.getString("scName"),
						rs.getBytes("document"), rs.getBytes("dMalware")));
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
				selectedScenario.getScDoc(), selectedScenario.getScFile());

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/simulator/ScenarioView.fxml"));
			loader.setController(new ScenarioViewController(scModel));
			Parent root = loader.load();

			Stage scenarioStage = (Stage) listViewMain.getScene().getWindow();

			scenarioStage.setTitle(selectedScenario.getName());
			scenarioStage.setScene(new Scene(root));
			scenarioStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
