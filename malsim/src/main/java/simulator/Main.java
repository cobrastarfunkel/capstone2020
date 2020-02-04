package simulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

			Parent root = FXMLLoader.load(getClass().getResource("SimulatorView.fxml"));

			primaryStage.setTitle("Malware Simulator v1.0");

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
