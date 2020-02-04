package simulator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Observable;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import com.sun.javafx.iio.ImageStorage.ImageType;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import scenarios.ScenarioHelper;

/**
 *
 * @author Layne
 */

public class SimulatorController implements Initializable {
	
	private class GuiScenario {
		private String name;
		private Integer id;
		private byte[] scFile;
		
		public GuiScenario(Integer idNumber, String name, byte[] file) {
			this.id = idNumber;
			this.name = name;
			this.scFile = file;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public byte[] getScFile() {
			return scFile;
		}
		public void setScFile(byte[] scFile) {
			this.scFile = scFile;
		}
		
		
	}

	// These items are for the list of scenarios
	@FXML
	private Label scenarioLabel;
	@FXML
	private ListView<String> listViewMain;
	@FXML
	private Button openButton;
	@FXML
	private ImageView scenarioDocImage;

	private ObservableList<String> items = FXCollections.observableArrayList();
	private HashMap<Integer, GuiScenario> scenarios;

	private SqliteDatabase sqliteDB;
	private static GuiScenario selectedScenario;
	private PDDocument scDoc;
	private PDFRenderer pdRenderer;

	@FXML
	void openScenario(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("ScenarioView.fxml"));
			scDoc = getPDF();
			pdRenderer = new PDFRenderer(scDoc);
			
			for (int page = 0; page < scDoc.getNumberOfPages(); ++page)
			{ 
			    BufferedImage bim = pdRenderer.renderImageWithDPI(page, 300);

			    // suffix in filename will be used as the file format
			    ImageIOUtil.writeImage(bim, "c:\\Test" + "-" + (page+1) + ".png", 300);
			}
			
			Stage scenarioStage = (Stage)listViewMain.getScene().getWindow();
			scenarioStage.setTitle(selectedScenario.getName());
			scenarioStage.setScene(new Scene(root));
			scenarioStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		new FXMLLoader(getClass().getResource("SimulatorView.fxml"));
		String scString;
		scenarios = new HashMap<Integer, SimulatorController.GuiScenario>();

		sqliteDB = new SqliteDatabase("guidb.sqlite");
		sqliteDB.createDatabase();
		sqliteDB.createTables();

		String sql = "select scenarios.idNumber, scenarios.scName, progress.completed, documents.document\r\n" + 
				"from scenarios\r\n" + 
				"join progress on scenarios.idNumber = progress.idNumber\r\n" + 
				"join documents on scenarios.idNumber = documents.idNumber";

		try (Connection conn = sqliteDB.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				scString = (rs.getInt("completed") == 0) ? "\t\tIncomplete" : "\t\tCompleted";
				items.add(rs.getString("scName") + scString);
				
				scenarios.put(items.size()-1, new GuiScenario(rs.getInt("idNumber"), rs.getString("scName"),
						rs.getBytes("document")));
			}
		} catch (Exception e) {

		}
		listViewMain.setItems(items);
	}
	
	@FXML public void handleMouseClick(MouseEvent arg0) throws IOException {
		selectedScenario = scenarios.get(listViewMain.getSelectionModel().getSelectedIndex());
		Integer idnum = selectedScenario.getId();
		

	}
	
	public PDDocument getPDF( ) throws IOException {
		PDDocument tempPdf = PDDocument.load(selectedScenario.getScFile());
		
		return tempPdf;
	}
}
