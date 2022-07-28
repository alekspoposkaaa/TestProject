package pi;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

public class CreateContact extends Login {
	
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
	 * Script used to create a contact on https://test.salesforce.com/ page with using locators for the elements
	 * @param service
	 * @param contactPhone
	 * @param lastName
	 * @param accountName
	 * @throws IOException means Those methods throw the IOException whenever an input or output operation is failed or interpreted
	 */
	@Test(dataProvider="dataProviders", dataProviderClass = CreateDataProviders.class)
	public void createContact(String service, String contactPhone, String lastName, String accountName) throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Locators.properties");
		Properties properties = new Properties();
		properties.load(fis);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		WebElement title = driver.findElement(By.xpath(properties.getProperty("class")));
		String titleAttribute = title.getAttribute("title");

		if (!titleAttribute.equals("Service")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("app.launcher")))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("search")))).sendKeys(service);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("service.button")))).click();
		}
		
		log.info("Navigate to Service App");
		
		//assertions
		String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl);
        System.out.println("AssertNotNull Test Passed");
        System.out.println(currentUrl);
        
        log.warn("The current url is " + currentUrl);
        
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("performance")))).click();

		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("files.tab"))));
		Actions action = new Actions(driver);
		action.moveToElement(element).perform();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("contacts.tab")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("new.contact")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("phone.contact")))).sendKeys(contactPhone);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("contact.last.name")))).sendKeys(lastName);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("account.name")))).sendKeys(accountName);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("aleks.account")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("save.contact")))).click();
		
		log.info("Contact '" + lastName + "' with account '" + accountName + "' with phone number '" + contactPhone + "' is successfully created");
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