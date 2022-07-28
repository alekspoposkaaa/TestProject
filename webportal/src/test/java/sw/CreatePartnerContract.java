package sw;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

public class CreatePartnerContract extends Login {

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
	 * Script used to create partner contract on https://test.salesforce.com/page with using locators for the elements
	 * @param contractTerms
	 * @param nameContract
	 * @param apiKey
	 * @param select
	 * @param copy
	 * @param selectAgain
	 * @param paste
	 * @throws IOException means Those methods throw the IOException whenever an input or output operation is failed or interpreted
	 * @throws InterruptedException
	 */
	@Test(dataProvider="dataProviders", dataProviderClass = CreateDataProviders.class)
	public void createPartnerContract(String contractTerms, String nameContract, String apiKey, String select, String copy, String selectAgain, String paste) throws IOException, InterruptedException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Locators.properties");
		Properties properties = new Properties();
		properties.load(fis);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("app.launcher")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("search")))).sendKeys(contractTerms);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("contract.terms.tab")))).click();
		
		//assertions
		String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl);
        System.out.println("AssertNotNull Test Passed");
        System.out.println(currentUrl);
        
        log.warn("The current url is " + currentUrl);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("new.contract")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("name.contract")))).sendKeys(nameContract);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("api.key")))).sendKeys(apiKey);

		WebElement sourceText = driver.findElement(By.xpath(properties.getProperty("name.contract")));
		Actions action = new Actions(driver);
		action.keyDown(sourceText, Keys.CONTROL).sendKeys(select).sendKeys(copy).build().perform();

		WebElement destinationArea = driver.findElement(By.xpath(properties.getProperty("api.key")));
		action.keyDown(destinationArea, Keys.CONTROL).sendKeys(selectAgain).sendKeys(paste).build().perform();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("contract.currency")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("currency.euro")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("save.contract")))).click();
		
		log.info("New Partner Contract with name '" + nameContract + "' and contract currency-euro is successfully saved!");

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