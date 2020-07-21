package TestCases;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import PageObjects.FBLogin_PageObjects;
import TestBase.Grid;

public class LoginToFB_Grid extends Grid {

	String testCaseName = "FBLogin";
	@DataProvider(name = "FBLogin")
	public Object[][] read(){
		
		Object[][] data = getData.retrieveTestData(sheetName, testCaseName);
		return data;
	}
	
	@Test(dataProvider = "FBLogin")
	public void ValidLoginTOFB(String execute, String name, String pwd) {


		FBLogin_PageObjects fb = PageFactory.initElements(getDriver(), FBLogin_PageObjects.class);
		
		
		fb.loginToFB(name, pwd);
	
	}

}
