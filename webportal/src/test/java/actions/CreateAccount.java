package actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import data.providers.CreateDataProviders;
import login.Login;

/**
 * Class used to handle Log4j2, ReportNG, Assertions, Annotations and TestNG within the Salesforce
 * @author Aleksandra.Pavleska
 */

public class CreateAccount extends Login {

	/**
	 * Login method that opens the chrome browser and logs into Salesforce
	 * @throws IOException
	 */
	@BeforeMethod
	public void openWebsite() throws IOException {
		loginToSalesforce("chrome");
		log.info("You are logged in Salesforce");
	}

	/**
	 * Script used to create a business account on https://test.salesforce.com/ page with using locators for the elements
	 * @param cpq 
	 * @param accountContact
	 * @param accountPhone
	 * @throws IOException means Those methods throw the IOException whenever an input or output operation is failed or interpreted
	 */
	@Test(dataProvider = "dataProviders", dataProviderClass = CreateDataProviders.class)
	public void accountCpq(String cpq, String accountContact, String accountPhone) throws IOException {

		// assertions
		SoftAssert softAssert = new SoftAssert();
		String expectedTitle = "Home | Salesforce";
		String actualTitle = driver.getTitle();
		System.out.println("Verifying the title of Salesforce page");
		softAssert.assertEquals(actualTitle, expectedTitle);

		log.warn("The expected title '" + expectedTitle + "' may not be the same as actual title - '" + actualTitle + "'");

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Locators.properties");
		Properties properties = new Properties();
		properties.load(fis);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement title = driver.findElement(By.xpath(properties.getProperty("class")));
		String titleAttribute = title.getAttribute("title");

		if (!titleAttribute.equals("Configure Price Quote (CPQ)")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("app.launcher")))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("search")))).sendKeys(cpq);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("cpq.button")))).click();
		}

		log.info("Navigated to CPQ app");

		// assertions
		String currentUrl = driver.getCurrentUrl();
		Assert.assertNotNull(currentUrl);
		System.out.println("AssertNotNull Test Passed");
		System.out.println(currentUrl);
		log.info("The current url " + currentUrl + " should not be null");

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("orders.click")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("accounts.locator")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("new.button")))).click();

		WebElement radio = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("business.button"))));
		radio.click();

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("next.button")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("name")))).sendKeys(accountContact);

		log.info("Account name '" + accountContact + "' is entered in textbox");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("phone")))).sendKeys(accountPhone);

		log.info("Phone number '" + accountPhone + "' is entered in textbox");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("save")))).click();
		
		softAssert.assertAll();
	}

	/**
	 * Method that closes the Chrome browser
	 */
	@AfterMethod
	public void closeWebsite() {
		driver.close();
		log.info("The browser is closed");
	}

}