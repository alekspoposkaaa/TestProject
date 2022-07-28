package wait;

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

import data.providers.CreateDataProviders;
import login.Login;

/**
 * Class used to handle Assertions, Annotations and TestNG within the Salesforce
 * @author Aleksandra.Pavleska
 */
public class EmailList extends Login {

	/**
	 * Login method that opens the chrome browser and logs into Salesforce
	 * @throws IOException
	 */
	@BeforeMethod
	public void openWebsite() throws IOException
	{
		loginToSalesforce("chrome");
		log.info("You are logged in Salesforce");
	}
	
	/**
	 * Script used to save a draft for email list from section Contacts on https://test.salesforce.com/ page with using locators for the elements
	 * @param service
	 * @param subject
	 * @param receiver
	 * @throws IOException means Those methods throw the IOException whenever an input or output operation is failed or interpreted
	 */
	@Test(dataProvider="dataProviders", dataProviderClass = CreateDataProviders.class)
	public void emailListTwo(String service, String subject, String receiver) throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Locators.properties");
		Properties properties = new Properties();
		properties.load(fis);

		// EXPLICIT WAIT
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("class"))));
		String titleAttribute = title.getAttribute("title");

		if (!titleAttribute.equals("Service")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("app.launcher")))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("search")))).sendKeys(service);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("service.button")))).click();
		}
		log.info("Navigated to Service App");
		
		//assertions
		String currentUrl = driver.getCurrentUrl();
		Assert.assertNotNull(currentUrl);
		System.out.println("AssertNotNull Test Passed");
		System.out.println(currentUrl);
		
		log.warn("The current url is " + currentUrl);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("performance")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("contacts")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("email.list")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("enter.subject")))).sendKeys(subject);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("send.to")))).sendKeys(receiver);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("save.as.draft")))).click();
		
		log.info("New Email List with subject '" + subject + "' and send to '" + receiver + "' is successfully saved as draft!");

	}
	
	/**
	 * Method that closes the Chrome browser
	 */
	@AfterMethod
	public void closeWebsite()
	{
		driver.close();
		log.info("The browser is closed");
	}
}
