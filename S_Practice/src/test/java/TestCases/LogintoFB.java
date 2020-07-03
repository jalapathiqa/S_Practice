package TestCases;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import PageObjects.FaceBookLogin_PageObjects;
import TestBase.TestBase;

public class LogintoFB extends TestBase {

	String testCaseName = "FBLogin";

	@DataProvider(name = "FBLogin")
	public Object[][] getData() {
		return readExcelUtil.retrieveTestData(sheetName, testCaseName);
	}

	@Test(dataProvider = "FBLogin")
	public void LoginFaceBook(String Execute, String email, String pWord) throws InterruptedException {

		FaceBookLogin_PageObjects fb = PageFactory.initElements(driver, FaceBookLogin_PageObjects.class);

		fb.loginToFB(email, pWord);
		Thread.sleep(2000);
	}
}
