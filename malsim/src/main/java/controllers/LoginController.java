package controllers;

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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	@FXML
	private TextField user;

	@FXML
	private TextField password;

	@FXML
	private Button loginButton;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		new FXMLLoader(getClass().getResource("/fxmlFiles/LoginView.fxml"));
	}

	@FXML
	void openHome(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxmlFiles/SimulatorView.fxml"));

			Stage primaryStage = (Stage) loginButton.getScene().getWindow();
			primaryStage.setTitle("Malware Simulator v1.0");

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}