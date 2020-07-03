package Reporting_Utils;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Listeners extends TestListenerAdapter {

	public ExtentReports extent;
	public ExtentTest test;
	public ExtentHtmlReporter htmlReporter;
	public static WebDriver driver;

	@Override
	public void onStart(ITestContext testContext) {

		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/Reports/Listeners_ExtentReports.html");
		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName("Rest Assured API Report");
		htmlReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host name", "localhost");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("user", "Jp");

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getName());
		test.log(Status.PASS, "Test Case Passed is: " + result.getName());

		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File("./ScreenShots_Pass/" + result.getMethod().getMethodName() + ".png");

		try {
			FileHandler.copy(src, dest);
			test.addScreenCaptureFromPath("./ScreenShots_Pass/" + result.getMethod().getMethodName() + '-' + ".png");
		} catch (IOException e) {
			System.out.println("could not write screenshot " + e.getMessage());
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getName());
		test.log(Status.FAIL, "Test Case Failed is: " + result.getName());
		test.log(Status.FAIL, "Test Case Failed is: " + result.getThrowable());

		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File("./ScreenShots_Fail/" + result.getMethod().getMethodName() + ".png");

		try {
			FileHandler.copy(src, dest);
			test.addScreenCaptureFromPath("./ScreenShots_Fail/" + result.getMethod().getMethodName() + '-' + ".png");
		} catch (IOException e) {
			System.out.println("could not write screenshot " + e.getMessage());
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getName());
		test.log(Status.SKIP, "Test Case Skipped is: " + result.getName());

	}

	@Override
	public void onFinish(ITestContext testContext) {
		extent.flush();
	}

}
