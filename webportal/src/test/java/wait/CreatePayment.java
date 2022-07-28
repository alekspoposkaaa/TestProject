package wait;

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

public class CreatePayment extends Login {

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
	 * Script used to create a payment adjustment on https://test.salesforce.com/page with using locators for the elements
	 * @param payment
	 * @param textbox
	 * @param paymentAccount
	 * @throws IOException means Those methods throw the IOException whenever an input or output operation is failed or interpreted
	 */
	@Test(dataProvider="dataProviders", dataProviderClass = CreateDataProviders.class)
	public void createPaymentThree(String payment, String textbox, String paymentAccount) throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Locators.properties");
		Properties properties = new Properties();
		properties.load(fis);
		
		//EXPLICIT WAIT
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		log.info("Explicit wait is successfully implemented!");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("app.launcher")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("search")))).sendKeys(payment);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("payment.tab")))).click();
		
		//assertions
		String currentUrl = driver.getCurrentUrl();
		Assert.assertNotNull(currentUrl);
		System.out.println("AssertNotNull Test Passed");
		System.out.println(currentUrl);
		
		log.warn("The current url is " + currentUrl);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("new.payment")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("color.picker")))).click();

		WebElement sliderColor = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("purple.color"))));
		Actions actionColor = new Actions(driver);
		actionColor.dragAndDropBy(sliderColor, 50, 12).perform();

		WebElement sliderShade = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("color.bottom"))));
		Actions actionShade = new Actions(driver);
		actionShade.dragAndDropBy(sliderShade, 55, -42).perform();
		actionShade.dragAndDropBy(sliderShade, 70, -18).perform();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("done.button")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("payment.textbox")))).sendKeys(textbox);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("payment.account")))).sendKeys(paymentAccount);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("payment.aleks")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("payment.save")))).click();
		
		log.info("Payment adjustment for account '" + paymentAccount + "' is successfully saved!");

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
