package TestCases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class OpenBrowser {
	@Test
	public void opebChrome() throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "C:/Users/Rishi/Desktop/SeleniumLibrary/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://eenadu.net");
		
		try {
			String wi = driver.getWindowHandle();
			for (String wi1 : driver.getWindowHandles()) {
				driver.switchTo().window(wi1);
				Thread.sleep(1000);
				
			}
			driver.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
