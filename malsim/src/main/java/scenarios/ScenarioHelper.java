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

/**
 * Can run executables and Powershell scripts. Use executeFile with the correct
 * options to run stuff
 * 
 * @author Ian Cobia
 *
 */

public class ScenarioHelper {
	private final String tempFilePath = Scenario.DEPLOY_PATH + "\\" + "temp.exe";
	private final String tempDocPath = Scenario.DEPLOY_PATH + "\\" + "temp.html";

	/**
	 * Will run either deployPowershellScript or deployExecutable base on language
	 * arg
	 * 
	 * @param file     File: the file to be executed
	 * @param language String: c++ or powershell
	 * @param option   String: If you executable takes args pass them here
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void executeFile(File file, String language, String option) throws IOException, InterruptedException {
		// Deploy Powershell Scenario
		if (language.contentEquals("powershell")) {
			deployPowershellScript(file);

			// Deploy executable Scenario
		} else if (language.contentEquals("c++")) {
			deployExecutable(file, option);
		}

	}

	/**
	 * Run Powershell scripts
	 * 
	 * @param file File: The powershell script
	 */
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

	/**
	 * Executes exectuables
	 * 
	 * @param file   File: the file to be executed
	 * @param option String: If you executable takes args pass them here
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void deployExecutable(File file, String options) throws IOException, InterruptedException {

		if (options.isEmpty()) {
			Process exec = Runtime.getRuntime().exec(file.toString());
			exec.waitFor();
			System.out.println(exec.exitValue());
		} else {
			System.out.println(file.getName());
			Process exec = Runtime.getRuntime().exec(String.format("%s %s", file.toString(), options));
			exec.waitFor();
			System.out.println(exec.exitValue());
		}

		// Need a better way to do this. For now it keeps
		// the executable from being deleted during testing
		// but it shouldn't be necessary in the long run.
		// If it is we should figure out a better way to do it
		if (file.getName().contentEquals("temp.exe")) {
			file.delete();
		}
	}

	public File getScenarioFile(String file) {
		File newFile = new File(Scenario.DEPLOY_PATH + "\\" + file);
		return newFile;
	}

	public byte[] convertFileToBytes(Path file) throws IOException {
		byte[] byteFile = Files.readAllBytes(file);
		return byteFile;
	}

	public File convertBytesToTempFile(byte[] file) throws IOException {
		Files.write(Paths.get(tempFilePath), file);
		return new File(tempFilePath);

	}

	public File convertBytesToFile(byte[] file) throws IOException {
		Files.write(Paths.get(tempDocPath), file);
		return new File(tempDocPath);
	}

}
