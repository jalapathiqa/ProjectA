package TestBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import ExcelUtils.Read_Excel;

public class Grid {

	public static String AplBrowser, AplUrl, AplUsername, AplPassword;
	// protected ThreadLocal<RemoteWebDriver> threadedDriver = null;
	protected RemoteWebDriver driver = null;

	public static ExtentReports extent;
	public static ExtentTest test;
	public static Properties configProp;

	public String filelocation = "C:\\Users\\Rishi\\git\\ProjectA\\ProjectA\\TestData\\DataDrivenExcel.xlsx";
	public String sheetName = "TestSheet";
	public Read_Excel getData = new Read_Excel(filelocation);

	@BeforeSuite
	public void beforeSuite() throws IOException {

		configProp = new Properties();
		File file = new File("C:\\config.properties");
		FileInputStream fis = new FileInputStream(file);
		configProp.load(fis);

		AplBrowser = configProp.getProperty("browser");
		AplUrl = configProp.getProperty("url");

		extent = new ExtentReports();
		extent.attachReporter(new ExtentHtmlReporter("./Reports/" + "Grid_Reports" + ".html"));

	}

	@BeforeClass
	public void beforeClass() throws MalformedURLException {

		//driver = new RemoteWebDriver(null);

		if (AplBrowser.equalsIgnoreCase("chrome")) {

			DesiredCapabilities cap = DesiredCapabilities.chrome();
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
			setDriver(driver);

		} else if (AplBrowser.equalsIgnoreCase("ie")) {

			DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
			setDriver(driver);
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(AplUrl);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

	}

	@BeforeMethod
	public void beforeMethod(Method method) {
		test = extent.createTest(method.getName());

	}

	@AfterMethod
	public void afterMethod(ITestResult result) {

		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, "Testcase PASSED is: " + result.getMethod().getMethodName() + ".png");
			test.info(result.getMethod().getMethodName());
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File dest = new File(System.getProperty("user.dir")+"/ScreenShots_Pass");

			try {
				FileHandler.copy(src, dest);
				test.addScreenCaptureFromPath(System.getProperty("user.dir")+"/ScreenShots_Pass");
			} catch (IOException e) {
				System.out.println("could not write screen shots" + e.getMessage());
			}

		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, "Testcase FAILED is: " + result.getMethod().getMethodName() + ".png");
			test.info(result.getMethod().getMethodName());
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File dest = new File(System.getProperty("user.dir")+"/ScreenShots_Fail");

			try {
				FileHandler.copy(src, dest);
				test.addScreenCaptureFromPath(System.getProperty("user.dir")+"/ScreenShots_Fail");
			} catch (IOException e) {
				System.out.println("could not write screen shots" + e.getMessage());
			}

		} else {
			test.log(Status.PASS, "Testcase SKIPPED is: " + result.getMethod().getMethodName() + ".png");
			test.info(result.getMethod().getMethodName());
		}
	}

	@AfterClass
	public void afterClass() {
		driver.close();
		driver.quit();
	}

	@AfterSuite
	public void afterSuite() {
		extent.flush();
	}

	public RemoteWebDriver getDriver() {
		return driver;
	}

	public void setDriver(RemoteWebDriver driver) {
		this.driver = driver;
	}

}
