package TestCases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class popUp_BasicAuth {
	@Test
	public void openChrome() throws InterruptedException {
		
		String username = "admin";
		String password = "admin";
		
		System.setProperty("webdriver.chrome.driver", "C:/Users/Rishi/Desktop/SeleniumLibrary/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		//driver.get("https://"+username+":"+password+"@" + "the-internet.herokuapp.com/basic_auth");
//		WebDriverWait wait = new WebDriverWait(driver, 10);
//		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
//		alert.authenticateUsing(new UserAndPassword(**admin** , **admin**));
		
		driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");

		
		
	}

}
