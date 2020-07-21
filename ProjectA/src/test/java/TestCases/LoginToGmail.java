package TestCases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import PageObjects.GMailLogin_PageObjects;

public class LoginToGmail {
	
	//WebDriver driver;
	
	@Test
	public void GmailLogin() throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "C:/Users/Rishi/Desktop/SeleniumLibrary/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://accounts.google.com/ServiceLogin/identifier?service=mail&passive=true&rm=false&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1&flowName=GlifWebSignIn&flowEntry=AddSession&TL=AM3QAYYF0ELhJAYlVjN_ZjLiEGkDlou03zD0Ep81-p0k3QdCeWM114eQoNiY9RGw");
		
		String uname = "jalapathiqa";
		String pwd = "Selenium@1111";
		
		//GMailLogin_PageObjects gm1 = new GMailLogin_PageObjects();
		GMailLogin_PageObjects gm = PageFactory.initElements(driver, GMailLogin_PageObjects.class);
		
//		gm.enterUsername(uname);
//		Thread.sleep(2000);
//		gm.clickNextAfterUserName();
//		Thread.sleep(3000);
//
//		gm.enterPassword(pWard);
//		gm.clickOnSubmitBtn();
		
		gm.loginToGmail(uname, pwd);
		
		driver.close();
		driver.quit();
	}

}
