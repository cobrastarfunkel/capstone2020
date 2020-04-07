package controllers;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ResourceBundle;

import auth.Authenticator;
import database.SqliteDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class RegistrationController implements Initializable {

	@FXML
	private TextField user;

	@FXML
	private TextField password;
	
	@FXML
	private TextField confirmPassword;
	
	@FXML
	private Button registerButton;

	private SqliteDatabase sqliteDB;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		new FXMLLoader(getClass().getResource("/fxmlFiles/RegistrationView.fxml"));

		sqliteDB = new SqliteDatabase("guidb.sqlite");
		sqliteDB.createDatabase();
		sqliteDB.createTables();
	}

	@FXML
	void register(ActionEvent event) throws NoSuchAlgorithmException, NoSuchProviderException {
		if(user.getLength() > 0 && password.getLength() > 0 && confirmPassword.getLength() > 0) {
			
			try {
	
				Authenticator auth = new Authenticator(user.getText(), password.getText(), sqliteDB);
				auth.register();
				
				Parent root = FXMLLoader.load(getClass().getResource("/fxmlFiles/SimulatorView.fxml"));
	
				Stage primaryStage = (Stage) registerButton.getScene().getWindow();
				primaryStage.setTitle("Malware Simulator v1.0");
	
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.show();
	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}