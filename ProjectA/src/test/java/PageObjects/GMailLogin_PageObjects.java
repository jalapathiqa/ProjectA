package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class GMailLogin_PageObjects {

	WebDriver driver;
	// define user name

//	@FindBy(how = How.ID, using = "identifierId")
//	private WebElement emailId;

	@FindBy(id = "identifierId")
	private WebElement userName;

	@FindBy(xpath = "//span[contains(text(),'Next')]")
	private WebElement nextafterUserName;

//	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Next')]")
//	private WebElement nextBtnafterUserName;

	@FindBy(name = "password")
	private WebElement enterPassword;

//	@FindBy(how = How.NAME, using = "password")
//	private WebElement enterPwd;

	@FindBy(xpath = "//span[contains(text(),'Next')]")
	private WebElement submitBtn;

//	@FindBy(how=How.XPATH, using="//span[contains(text(),'Next')]")
//	private WebElement loginBtn;

	public GMailLogin_PageObjects() {
		PageFactory.initElements(driver, this);
	}

	public void enterUsername(String uname) {
		userName.sendKeys(uname);
	}

	public void clickNextAfterUserName() {
		nextafterUserName.click();
	}

	public void enterPassword(String pWard) {
		enterPassword.sendKeys(pWard);
	}

	public void clickOnSubmitBtn() {
		submitBtn.click();
	}

	public void loginToGmail(String uname, String pwd) throws InterruptedException {

		userName.sendKeys(uname);
		Thread.sleep(2000);
		nextafterUserName.click();
		enterPassword.sendKeys(pwd);
		submitBtn.click();

	}

}
