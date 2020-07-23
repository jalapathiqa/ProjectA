package TestBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import ExcelUtils.Read_Excel;

public class BaseClass_Grid {

	public static String ApplBrowser, ApplUrl, User, Password;
	protected RemoteWebDriver driver;
	protected ThreadLocal<RemoteWebDriver> threadedDriver = null;

	public static Properties configProp;
	public static ExtentReports extent;
	public static ExtentTest test;

	public ThreadLocal<String> testCaseName;
	public ThreadLocal<Integer> currentRowNumber;
	public ThreadLocal<ArrayList<Integer>> currentRowList;

	public String fileLocation = System.getProperty("user.dir") + "./TestData/DataDrivenExcel.xlsx";
	public String sheetName = "TestSheet";
	public Read_Excel readExcel = new Read_Excel(fileLocation);

	@BeforeSuite
	public void runBeforeEverything() throws IOException {

		configProp = new Properties();
		File configFile = new File("C:\\config.properties");
		FileInputStream configFileProp = new FileInputStream(configFile);
		configProp.load(configFileProp);

		try {
			ApplBrowser = configProp.getProperty("browser");
			ApplUrl = configProp.getProperty("url");
			User = configProp.getProperty("uName");
			Password = configProp.getProperty("pWord");
		} catch (Exception e) {
			e.printStackTrace();
		}

		extent = new ExtentReports();
		extent.attachReporter(new ExtentHtmlReporter("./Reports/" + "GridExtentReport" + timeStamp() + ".html"));
	}

	@BeforeClass
	public void initializeBrowser() throws MalformedURLException {

		threadedDriver = new ThreadLocal<RemoteWebDriver>();
		testCaseName = new ThreadLocal<String>();
		currentRowNumber = new ThreadLocal<Integer>();
		currentRowList = new ThreadLocal<ArrayList<Integer>>();
		setTestCaseName(this.getClass().getSimpleName());

		if (ApplBrowser.equalsIgnoreCase("chrome")) {
			// System.setProperty("webdriver.chrome.driver",
			// "C:/Users/Rishi/Desktop/SeleniumLibrary/chromedriver.exe");
			DesiredCapabilities cap = DesiredCapabilities.chrome();

			// cap.setCapability("platform", "Windows 10");
			// cap.setCapability("version", "");
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
			threadedDriver.set(driver);

		} else if (ApplBrowser.equalsIgnoreCase("ie")) {
			DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
			driver = new RemoteWebDriver(new URL("https://localhost:4444/wd/hub"), cap);
			threadedDriver.set(driver);
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get(ApplUrl);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();

	}

	@BeforeMethod
	public void beforeTests(Method method) {
		test = extent.createTest(method.getName());

	}

	@AfterMethod
	public void onCompleteTests(ITestResult result) {

		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, "TestCase PASSED is: " + result.getMethod().getMethodName() + timeStamp() + ".png");

			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File dest = new File(System.getProperty("user.dir") + "/Grid_Screenshots_Pass"
					+ result.getMethod().getMethodName() + timeStamp() + ".png");

			try {
				org.openqa.selenium.io.FileHandler.copy(src, dest);
				test.addScreenCaptureFromPath(System.getProperty("user.dir") + "/Grid_Screenshots_Pass"
						+ result.getMethod().getMethodName() + timeStamp() + ".png");

			} catch (IOException e) {
				System.out.println("could not write screenshot " + e.getMessage());
			}

		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, "TestCase FAILED is: " + result.getMethod().getMethodName() + timeStamp() + ".png");

			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File dest = new File(System.getProperty("user.dir") + "/Grid_Screenshots_Pass/"
					+ result.getMethod().getMethodName() + timeStamp() + ".png");

			try {
				org.openqa.selenium.io.FileHandler.copy(src, dest);
				test.addScreenCaptureFromPath(System.getProperty("user.dir") + "/Grid_Screenshots_Fail"
						+ result.getMethod().getMethodName() + timeStamp() + ".png");

			} catch (IOException e) {
				System.out.println("could not write screenshot " + e.getMessage());
			}

		} else {
			test.log(Status.SKIP, "TestCase SKIPPED is: " + result.getMethod().getMethodName() + timeStamp() + ".png");
		}

	}

	@AfterClass
	public void closeBrowsers() {
		driver.close();
		driver.quit();

	}

	@AfterSuite
	public void tearDown() {
		extent.flush();

	}

//	@BeforeTest
//	public void StartingHUB() throws IOException, InterruptedException {
//		Runtime.getRuntime()
//				.exec("cmd /c start C:\\Users\\Rishi\\Desktop\\SeleniumLibrary\\Selenium-Grid\\HUB-Copy.bat");
//		Thread.sleep(10000);
//		System.out.println("selenium Grid HUB started");
//	}
//	public void StartingNodes() throws IOException, InterruptedException {
//		Runtime.getRuntime().exec(
//				"cmd /c start C:\\Users\\Rishi\\Desktop\\SeleniumLibrary\\Selenium-Grid\\NodeRun_Chrome-Copy.bat");
//		System.out.println("selenium Grid Nodes started");
//		Thread.sleep(10000);
//	}
//
//	@AfterTest
//	public void ClosingGrid() throws IOException, InterruptedException {
//		try {
//			Runtime.getRuntime().exec("taskkill /im cmd.exe");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public String timeStamp() {
		return new SimpleDateFormat("dd-MM-yyyy HH.mm.ss").format(new Date());

	}

	public RemoteWebDriver getDriver() {
		return this.driver = threadedDriver.get();
	}

	public String getTestCaseName() {
		return testCaseName.get();
	}

	public void setTestCaseName(String testCaseClassName) {
		this.testCaseName.set(testCaseClassName);
	}

	public ArrayList<Integer> getCurrentRowList() {
		return currentRowList.get();
	}

	public void setCurrentRowList(ArrayList<Integer> value) {
		this.currentRowList.set(value);
	}

	public int getCurrentRowNumber() {
		return currentRowNumber.get();
	}

	public void setCurrentRowNumber(int currentRow) {
		this.currentRowNumber.set(currentRow);
	}
}
