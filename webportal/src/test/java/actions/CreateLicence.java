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
import data.providers.CreateDataProviders;
import login.Login;

/**
 * Class used to handle Assertions, Annotations and TestNG within the Salesforce
 * @author Aleksandra.Pavleska
 */

public class CreateLicence extends Login {

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
	 * Script used to create a suspended license on https://test.salesforce.com/ page with using locators for the elements
	 * @param licensesTab
	 * @param licenseType
	 * @throws IOException means Those methods throw the IOException whenever an input or output operation is failed or interpreted
	 */
	@Test(dataProvider="dataProviders", dataProviderClass = CreateDataProviders.class)
	public void createLicense(String licensesTab, String licenseType) throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Locators.properties");
		Properties properties = new Properties();
		properties.load(fis);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("app.launcher")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("search")))).sendKeys(licensesTab);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("licenses")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("new.license")))).click();
		
		//assertions
		String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl);
        System.out.println("AssertNotNull Test Passed");
        System.out.println(currentUrl);
        
        log.warn("The current url is " + currentUrl);
		
		WebElement radio = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("type.of.license"))));
		radio.click();
		
		log.info("Expired license is created");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("next.license")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("license.type")))).sendKeys(licenseType);
		
		log.info("This type of license '" + licenseType + "' is created!");
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("save.license")))).click();
		
		log.info("The license is saved!");
		
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