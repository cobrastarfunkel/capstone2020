package simulator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author Layne
 */

public class SimulatorController implements Initializable {

    //These items are for the list of scenarios
    @FXML private Label scenarioLabel;
    @FXML private ListView listView;
    @FXML private Button openButton;

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
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
