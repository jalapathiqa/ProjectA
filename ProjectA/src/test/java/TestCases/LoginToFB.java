package TestCases;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import PageObjects.FBLogin_PageObjects;
import TestBase.BaseClass;

public class LoginToFB extends BaseClass {

	@Test
	public void ValidLoginTOFB() {

		String uname = "jpreddy911@gmail.com";
				String pword = "Kala@1234";
		FBLogin_PageObjects fb = PageFactory.initElements(driver, FBLogin_PageObjects.class);
		fb.loginToFB(uname, pword);
	
	}

}
