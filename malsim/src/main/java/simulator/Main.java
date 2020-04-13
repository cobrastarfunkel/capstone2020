package simulator;

import database.SqliteDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		SqliteDatabase sqliteDB = new SqliteDatabase("guidb.sqlite");
		sqliteDB.createDatabase();
		sqliteDB.createTables();

		Parent root = FXMLLoader.load(getClass().getResource("/fxmlFiles/LoginView.fxml"));

		primaryStage.setTitle("Malware Simulator v1.0");

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
