package ui;

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
public class CreateAccount extends Login {
	
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
	 * Script used to create an Account for Consumer Customer on https://test.salesforce.com/ page with using locators for the elements
	 * @param service
	 * @param accountName
	 * @param accountPhone
	 * @throws IOException means Those methods throw the IOException whenever an input or output operation is failed or interpreted
	 */
	@Test(enabled=false, dataProvider="dataProviders", dataProviderClass = CreateDataProviders.class)
	public void createAccount(String service, String accountName, String accountPhone) throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Locators.properties");
		Properties properties = new Properties();
		properties.load(fis);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement title = driver.findElement(By.xpath(properties.getProperty("class")));
		String titleAttribute = title.getAttribute("title");

		if (!titleAttribute.equals("Service")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("app.launcher")))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("search")))).sendKeys(service);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("service.button")))).click();
		}
		
		log.info("Navigated to Service App");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("performance")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("account")))).click();
		
		//assertions
		WebElement accountsTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("account"))));
		Assert.assertEquals(true, accountsTab.isDisplayed());
		log.warn("Accounts Tab should be displayed!");
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("new.account")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("consumer")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("next")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("name.account")))).sendKeys(accountName);
		
		log.info("Account name '" + accountName + "' is entered in textbox");
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("phone.account")))).sendKeys(accountPhone);
		
		log.info("Phone number '" + accountPhone + "' is entered in textbox");
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("save.account")))).click();
						
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