package TestCases;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class WindowHandles {

	WebDriver driver;

	@Test
	public void openEenadu() throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "C:/Users/Rishi/Desktop/SeleniumLibrary/chromedriver.exe");
		driver = new ChromeDriver();		
		driver.manage().window().maximize();
		driver.get("https://opensource-demo.orangehrmlive.com/");
		
		String window1 = driver.getWindowHandle();
		System.out.println("window1:"+window1);		
		
		driver.findElement(By.xpath("//a[contains(text(),'OrangeHRM, Inc')]")).click();
		Thread.sleep(5000);
		System.out.println("all windows:"+driver.getWindowHandles().size());

		driver.switchTo().window(window1);
		System.out.println("title of window1: "+driver.getTitle());
		
		Set<String> w2 = driver.getWindowHandles();
		for (String window2 : w2) {
			driver.switchTo().window(window2);
			System.out.println("title of window2: "+driver.getTitle());
		}

	}

}
