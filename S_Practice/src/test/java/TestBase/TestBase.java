package TestBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
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
import Excel_Utils.Read_Excel;

public class TestBase {

	public static Properties configProp;
	public static String appBrowser, appUrl, userName, passWord, httpURL;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ExtentHtmlReporter htmlReporter;
	public static WebDriver driver;

	public String filelocation = System.getProperty("user.dir") + "/TestData/DataDrivenExcel.xlsx";
	public String sheetName = "TestSheet";
	public Read_Excel readExcelUtil = new Read_Excel(filelocation);

	@BeforeSuite
	public void reunBeforeEverything() throws Exception {
		loadPropertiesFiles();

		try {
			appBrowser = configProp.getProperty("browser");
			appUrl = configProp.getProperty("url");
			userName = configProp.getProperty("uName");
			passWord = configProp.getProperty("pWord");
			httpURL = configProp.getProperty("restAPRIurl");

			System.out.println(appBrowser);
			System.out.println(appUrl);
			System.out.println(userName);
			System.out.println(passWord);
			System.out.println(httpURL);

		} catch (Exception e) {
			e.printStackTrace();
		}

		htmlReporter = new ExtentHtmlReporter("./Reports/" + "ExtentReports" + timeStamp() + ".html");
		// htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName("Automation Report");
		htmlReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host name", "localhost");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("user", "Jp");
	}

	@BeforeClass
	public void initializeBrowser() {

		if (appBrowser.equalsIgnoreCase("chrome")) {

			System.setProperty("webdriver.chrome.driver", "C:/Users/Rishi/Desktop/SeleniumLibrary/chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(appUrl);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		} else if (appBrowser.equalsIgnoreCase("ie")) {
			driver = new ChromeDriver();
			System.setProperty("webdriver.ie.driver", "C:/Users/Rishi/Desktop/SeleniumLibrary/IEDriverServers.exe");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(appUrl);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		}

	}

	@BeforeMethod
	public void runBeforeTest(Method method) {
		test = extent.createTest(method.getName());
		// test.log(Status.PASS, "Test Case Passed is "+method.getName());
	}

	@AfterMethod
	public void runAfterTest(ITestResult result) {

		if (result.getStatus() == ITestResult.SUCCESS) {

			test.log(Status.PASS, "Test Case Passed is: " + result.getName());
			TakesScreenshot ts = (TakesScreenshot) driver;
			File src = ts.getScreenshotAs(OutputType.FILE);
			File dest = new File(
					"./ScreenShots_Pass/" + result.getMethod().getMethodName() + " " + timeStamp() + ".jpg");
			try {
				FileHandler.copy(src, dest);
				test.addScreenCaptureFromPath("./ScreenShots_Pass/" + result.getMethod().getMethodName() + ".jpg");
			} catch (IOException e) {
				System.out.println("could not write screenshot " + e.getMessage());
			}
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, "Test Case Failed is: " + result.getName());
			// test.log(Status.FAIL, "Test Case Failed is: " + result.getThrowable());

			TakesScreenshot ts = (TakesScreenshot) driver;
			File src = ts.getScreenshotAs(OutputType.FILE);
			File dest = new File(
					"./ScreenShots_Fail/" + result.getMethod().getMethodName() + " " + timeStamp() + ".jpg");
			try {
				FileHandler.copy(src, dest);
				test.addScreenCaptureFromPath("./ScreenShots_Fail/" + result.getMethod().getMethodName() + ".jpg");
			} catch (IOException e) {
				System.out.println("could not write screenshot " + e.getMessage());
			}
		} else {
			test.log(Status.SKIP, "Test Case Skipped is:" + result.getName());

		}

	}

	@AfterClass
	public void afterClass() {
		driver.close();
		driver.quit();
	}

	@AfterSuite
	public void tearDown() {

		extent.flush();
	}

	public void loadPropertiesFiles() throws Exception {

		configProp = new Properties();
		File fileConfigProp = new File("C:\\config.properties");
		FileInputStream fileConfig = new FileInputStream(fileConfigProp);
		configProp.load(fileConfig);

	}

	public String timeStamp() {
		return new SimpleDateFormat("MM/dd/yyyy HH.mm.ss").format(new Date());
	}

}
