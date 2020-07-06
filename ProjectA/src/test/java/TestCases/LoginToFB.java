package TestCases;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import PageObjects.FBLogin_PageObjects;
import TestBase.BaseClass;

public class LoginToFB extends BaseClass {

	String testCaseName = "FBLogin";
	@DataProvider(name="FBLogin")
	public Object[][] getData(){
		Object [][] data = readExcel.retrieveTestData(sheetName, testCaseName);
		return data;
	}
	
	@Test(dataProvider="FBLogin")
	public void ValidLoginTOFB(String execute, String name, String pwd) {

		//String uname = "jpreddy911@gmail.com";
		//		String pword = "Kala@1234";
		FBLogin_PageObjects fb = PageFactory.initElements(driver, FBLogin_PageObjects.class);
		fb.loginToFB(name, pwd);
	
	}

}
