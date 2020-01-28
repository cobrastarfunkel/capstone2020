package scenarios;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class ScenarioBuilder {

	/**
	 * Load the Scenarios into a HashMap for easy retrieval
	 * 
	 * @return HashMap<Integer, Scenario> of the Scenarios: ID number is the Key
	 */
	public HashMap<Integer, Scenario> loadScenarios() {
		HashMap<Integer, Scenario> scenarios = new HashMap<Integer, Scenario>();
		Scenario scHolder = null;

		// Get the Path to where the yaml files are stored
		Path dir_name = Paths.get(Scenario.CONFIG_PATH);

		// Stream the Path from above
		try (DirectoryStream<Path> paths = Files.newDirectoryStream(dir_name)) {
			for (Path filename : paths) {

				// Create a new Scenario
				scHolder = createScenario(filename.toFile());

				// Put Scenario in the HashMap
				scenarios.put(scHolder.getId(), scHolder);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return scenarios;
	}

	/**
	 * Create a new Scenario from a yaml file
	 * 
	 * @param configFile: yaml file that contains the parameters for the Scenario
	 * @return tScenario Scenario: A new Scenario
	 */
	private Scenario createScenario(File configFile) {
		ObjectMapper om = new ObjectMapper(new YAMLFactory());
		Scenario tScenario = null;

		try {
			// Reads the file and matches keys to Scenario variables
			tScenario = om.readValue(configFile, Scenario.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return tScenario;
	}

}
