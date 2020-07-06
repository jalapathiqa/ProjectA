package TestBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;

import org.codehaus.groovy.control.messages.Message;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
import com.aventstack.extentreports.reporter.configuration.Theme;

import ExcelUtils.Read_Excel;

public class BaseClass {

	public static WebDriver driver;
	public static String ApplURL, userName, passWord, AppBrowser, httpURL;
	public static Properties configProp;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ExtentHtmlReporter htmlReporter;

	public String fileLocation = System.getProperty("user.dir") + "./TestData/DataDrivenExcel.xlsx";
	public String sheetName = "TestSheet";
	public Read_Excel readExcel = new Read_Excel(fileLocation);

	@BeforeSuite
	public void reunBeforeEverything() throws IOException {
		loadProperties();

		ApplURL = configProp.getProperty("url");
		AppBrowser = configProp.getProperty("browser");
		userName = configProp.getProperty("username");
		passWord = configProp.getProperty("password");
		httpURL = configProp.getProperty("restAPRIurl");

		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "./Reports/"+"ExtentReports" +" - "+ timeStamp()+".html");
		htmlReporter.config().setReportName("Automation Report");
		htmlReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

	}

	@BeforeClass
	public void initializeBrowser() {

		if (AppBrowser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:/Users/Rishi/Desktop/SeleniumLibrary/chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(ApplURL);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		} else if (AppBrowser.equalsIgnoreCase("ie")) {

			driver = new ChromeDriver();
			System.setProperty("webdriver.ie.driver", "C:/Users/Rishi/Desktop/SeleniumLibrary/IEDriverServers.exe");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(ApplURL);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

		}

	}

	@BeforeMethod
	public void beforeMethod(Method method) {
		test = extent.createTest(method.getName());

	}

	@AfterMethod
	public void afterMethod(ITestResult result) {

		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, "Test Case Passed is: " + result.getMethod().getMethodName());

			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File dest = new File(System.getProperty("user.dir")+"./ScreenShots_Pass/"
					+ result.getMethod().getMethodName() + " - "+ timeStamp()+".png");

			try {
				org.openqa.selenium.io.FileHandler.copy(src, dest);
				test.addScreenCaptureFromPath(System.getProperty("user.dir")+"./ScreenShots_Pass/"
						+ result.getMethod().getMethodName() + ".png");
			} catch (IOException e) {
				System.out.println("could not write screenshot " + e.getMessage());
			}
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, "Test Case Failed is: " + result.getMethod().getMethodName());

			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File dest = new File(System.getProperty("user.dir")+"./ScreenShots_Fail/"
					+ result.getMethod().getMethodName() +" - "+ timeStamp()+".png");

			try {
				org.openqa.selenium.io.FileHandler.copy(src, dest);
				test.addScreenCaptureFromPath(System.getProperty("user.dir")+"./ScreenShots_Fail/"
						+ result.getMethod().getMethodName() + ".png");
			} catch (IOException e) {
				System.out.println("could not write screenshot " + e.getMessage());
			}
		} else {
			test.log(Status.SKIP, "Skipped");
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

	public void loadProperties() throws IOException {

		configProp = new Properties();
		File fileConfig = new File("C:\\config.properties");
		FileInputStream fin = new FileInputStream(fileConfig);
		configProp.load(fin);

	}
	
	public String timeStamp() {
		// SimpleDate SimpleDateFormat;
		return new SimpleDateFormat("dd-MM-yyyy HH.mm.ss").format(new Date());
	}
}
