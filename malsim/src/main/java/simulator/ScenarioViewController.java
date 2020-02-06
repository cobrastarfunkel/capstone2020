package simulator;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class ScenarioViewController implements Initializable {

	@FXML
	private Button backButton;

	@FXML
	private WebView webView;

	private WebEngine we;

	private byte[] urlPath;

	private Integer id;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// webView = new WebView();
		we = this.webView.getEngine();
		System.out.println(urlPath);

		we.load("https://google.com");
		// Stage scenarioStage = (Stage) Parent.

	}

}
