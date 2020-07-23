package selenium_docker_Grid;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Setup_DockerGrid {
	// protected RemoteWebDriver driver;
	// protected ThreadLocal<RemoteWebDriver> threadedDriver;

	@BeforeTest
	public void StartingGrid() throws IOException, InterruptedException {
		Runtime.getRuntime()
				.exec("cmd /c start C:\\Users\\Rishi\\Desktop\\SeleniumLibrary\\Selenium-Grid\\HUB-Copy.bat");
		Thread.sleep(10000);
		System.out.println("selenium Grid HUB started");
		Runtime.getRuntime().exec(
				"cmd /c start C:\\Users\\Rishi\\Desktop\\SeleniumLibrary\\Selenium-Grid\\NodeRun_Chrome-Copy.bat");
		System.out.println("selenium Grid Nodes started");
		Thread.sleep(10000);
	}

	@AfterTest
	public void ClosingGrid() throws IOException, InterruptedException {
		try {
			Runtime.getRuntime().exec("taskkill /im cmd.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
