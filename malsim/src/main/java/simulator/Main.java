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
		try {
			Parent root = FXMLLoader.load(getClass().getResource("SimulatorView.fxml"));
			primaryStage.setTitle("Malware Simulator v1.0");
			SqliteDatabase sqliteDB = new SqliteDatabase("guidb.sqlite");
			sqliteDB.createDatabase();
			sqliteDB.createTables();

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
