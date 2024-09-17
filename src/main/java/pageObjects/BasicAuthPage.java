package pageObjects;

import org.openqa.selenium.By;

import constants.GlobalConstants;

import static helpers.WebUI.*;

public class BasicAuthPage {

//	private static String messageRequiredEmail = "Please fill in this place";
	private static By titleLoginPage = By.xpath("//div[normalize-space()='Sign in to GitHub']");
	private static By inputEmail = By.xpath("//input[@id='login_field']");
	private static By inputPassword = By.xpath("//input[@id='password']");
	private static By buttonSubmitLogin = By.xpath("//input[@name='commit']");
	private static By messageAccDoesNotExist = By.xpath(
			"//body/div[@class='logged-out env-production page-responsive session-authentication']/div[@class='application-main ']/main/div[@id='login']/div[@id='js-flash-container']/div[1]");
	private static By homePageText = By.xpath("//h2[normalize-space()='Home']");
	
	public static void openLoginPage() {
		openWebsite(GlobalConstants.URL);
		waitForPageLoaded();
        verifyElementVisible(titleLoginPage, "Sign in to GitHub");
	}

	public static void loginFailWithEmailNull() {
		openLoginPage();
		sleep(2);
		clickElement(buttonSubmitLogin);
		waitForPageLoaded();
		sleep(1);
		verifyHTML5RequiredField(inputEmail);
	}

	public static void loginFailWithEmailDoesNotExist(String email, String password) {
		openLoginPage();
		waitForPageLoaded();
		setText(inputEmail, email);
		setText(inputPassword, password);
		clickElement(buttonSubmitLogin);
		verifyElementVisible(messageAccDoesNotExist, "Incorrect username or password.");
	}

	public static void loginFailWithNullPassword(String email) {
		openLoginPage();
		waitForPageLoaded();
		setText(inputEmail, email);
		clickElement(buttonSubmitLogin);
		verifyHTML5RequiredField(inputPassword);
	}

	public static void loginFailWithIncorrectPassword(String email, String password) {
		openLoginPage();
		waitForPageLoaded();
		setText(inputEmail, email);
		clearText(inputPassword);
		setText(inputPassword, password);
		clickElement(buttonSubmitLogin);
		verifyElementVisible(messageAccDoesNotExist, "Incorrect username or password.");
	}

	public static void loginSuccessWithValidCredentials(String email, String password) {
		openLoginPage();
		waitForPageLoaded();
		setText(inputEmail, email);
		clearText(inputPassword);
		setText(inputPassword, password);
		clickElement(buttonSubmitLogin);
		waitForElementVisible(homePageText);
		verifyElementVisible(homePageText, "Home");
	}
}
