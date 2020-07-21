package TestCases;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ParallelTesting {
	WebDriver driver;
	
	
	@Test(priority=1)
	public void LogoTest() throws InterruptedException {
		System.out.println("Test 1");
		System.setProperty("webdriver.chrome.driver", "C:/Users/Rishi/Desktop/SeleniumLibrary/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		driver.navigate().to("https://opensource-demo.orangehrmlive.com/");
		
		WebElement ele = driver.findElement(By.xpath("//div[@id='divLogo']//img"));
		Assert.assertTrue(ele.isDisplayed());
		Thread.sleep(5000);

	}
	
	
	@Test(priority=2)
	public void HomePageTitle() throws InterruptedException {
		System.out.println("Test 2");
		System.setProperty("webdriver.chrome.driver", "C:/Users/Rishi/Desktop/SeleniumLibrary/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		driver.navigate().to("https://opensource-demo.orangehrmlive.com/");
		
		String title=driver.getTitle();
		Assert.assertEquals(title, "OrangeHRM");
		Thread.sleep(5000);
	}
	
	@AfterMethod
	public void tearDown() {
		System.out.println("closing all");
		//driver.close();
		driver.quit();
	}

}
