package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.annotations.Test;

public class FaceBookLogin_PageObjects {

	WebDriver driver;

	// define user name
	@FindBy(how = How.ID, using = "email")
	private WebElement email;

	// define password
	@FindBy(how = How.ID, using = "pass")
	private WebElement passWord;

	// submit button
	@FindBy(how = How.XPATH, using = "//input[@id='u_0_b']")
	private WebElement loginBtn;

	public void loginToFB(String uName, String pWord) {

		email.sendKeys(uName);
		passWord.sendKeys(pWord);
		loginBtn.click();

	}
}
