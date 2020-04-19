package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import auth.Authenticator;
import database.SqliteDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.ScenarioModel;

@SuppressWarnings("unused")
public class LoginController implements Initializable {

	@FXML
	private TextField user;

	@FXML
	private TextField password;

	@FXML
	private Button loginButton;

	@FXML
	private Button registrationButton;

	private SqliteDatabase sqliteDB;
	private static ScenarioModel selectedScenario;
	private Alert a = new Alert(AlertType.WARNING);

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		new FXMLLoader(getClass().getResource("/fxmlFiles/LoginView.fxml"));

	}

	@FXML
	void openHome(ActionEvent event) {

		Authenticator auth = new Authenticator(this.user.getText(), this.password.getText());
		System.out.println("Login Click");
		if (auth.login()) {
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("/fxmlFiles/SimulatorView.fxml"));
				Stage primaryStage = (Stage) loginButton.getScene().getWindow();
				primaryStage.setTitle("Malware Simulator v1.0");

				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			a.setContentText("Invalid Username");
			a.show();
		}

	}

	@FXML
	void openRegistration(ActionEvent event) {

		try {

			Parent root = FXMLLoader.load(getClass().getResource("/fxmlFiles/RegistrationView.fxml"));

			Stage primaryStage = (Stage) loginButton.getScene().getWindow();
			primaryStage.setTitle("User Registration");

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}