package scenarios;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellNotAvailableException;
import com.profesorfalken.jpowershell.PowerShellResponse;

public class ScenarioHelper {

	public void executeFile(File file, String language, String option) throws IOException, InterruptedException {
		// Deploy Powershell Scenario
		if (language.contentEquals("powershell") || option.contentEquals("reset")) {
			deployPowershellScript(file);

			// Deploy executable Scenario
		} else if (language.contentEquals("c++")) {
			deployExecutable(file);
		}

	}

	private void deployPowershellScript(File file) {

		try (PowerShell powerShell = PowerShell.openSession()) {
			Map<String, String> config = new HashMap<String, String>();
			config.put("maxWait", "80000");
			PowerShellResponse response = powerShell.configuration(config).executeScript(file.toString());

			System.out.println("Script output:" + response.getCommandOutput());

		} catch (PowerShellNotAvailableException e) {
			System.out.println(e.getMessage());
		}

	}

	private void deployExecutable(File file) throws IOException, InterruptedException {

		Process exec = Runtime.getRuntime().exec(file.toString());
		exec.waitFor();
		System.out.println(exec.exitValue());

	}

	public File getScenarioFile(String file) {
		File newFile = new File(Scenario.DEPLOY_PATH + "\\" + file);
		return newFile;
	}

	public byte[] convertFileToBytes(Path file) throws IOException {
		byte[] byteFile = Files.readAllBytes(file);
		return byteFile;
	}

	public File convertBytesToFile(byte[] file) throws IOException {
		String tempFilePath = Scenario.DEPLOY_PATH + "\\" + "temp.exe";
		Files.write(Paths.get(tempFilePath), file);
		return new File(tempFilePath);

	}

}
