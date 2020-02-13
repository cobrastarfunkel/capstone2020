package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import models.ScenarioModel;
import scenarios.ScenarioHelper;

public class ScenarioViewController implements Initializable {

	@FXML
	private Button backButton;

	@FXML
	private WebView webView;

	private WebEngine we;

	private final ScenarioModel scenario;

	private final ScenarioHelper sch = new ScenarioHelper();

	public ScenarioViewController(ScenarioModel model) {
		this.scenario = model;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		we = this.webView.getEngine();

		try {
			we.load(sch.convertBytesToFile(scenario.getScDoc()).toURI().toString());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void goHome(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxmlFiles/SimulatorView.fxml"));
			Stage primaryStage = (Stage) webView.getScene().getWindow();
			primaryStage.setTitle("Malware Simulator v1.0");

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
