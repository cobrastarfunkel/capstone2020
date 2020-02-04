package simulator;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Observable;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

	private ObservableList<String> items = FXCollections.observableArrayList();

	SqliteDatabase sqliteDB;
	ScenarioHelper sch;
	Scene queryScene;
	Stage newStage;

	@FXML
	void openScenario(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ScenarioView.fxml"));
			Parent root = fxmlLoader.load();

			Stage scenarioStage = new Stage();
			scenarioStage.initModality(Modality.APPLICATION_MODAL);
			scenarioStage.setTitle("Scenario Description");
			scenarioStage.setScene(new Scene(root));
			scenarioStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SimulatorView.fxml"));

		sqliteDB = new SqliteDatabase("guidb.sqlite");
		sqliteDB.createDatabase();
		sqliteDB.createTables();
		sch = new ScenarioHelper();

		String sql = "SELECT * FROM scenarios";

		try (Connection conn = sqliteDB.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				items.add(rs.getString(2));
			}
		} catch (Exception e) {

		}
		listViewMain.setItems(items);
	}

}
