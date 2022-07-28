package actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
 * Class used to handle Assertions, Annotations and TestNG within the Salesforce
 * @author Aleksandra.Pavleska
 */

public class CreateAccountService extends Login {
		
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
	 * Script used to create an Account for Business Customer on https://test.salesforce.com/ page with using locators for the elements
	 * @param service
	 * @param accountContact
	 * @param accountPhone
	 * @throws IOException means Those methods throw the IOException whenever an input or output operation is failed or interpreted
	 */
	@Test(enabled=false, dataProvider="dataProviders", dataProviderClass = CreateDataProviders.class)
	public void accountService(String service, String accountContact, String accountPhone) throws IOException {
		
		//assertions
		SoftAssert softAssert = new SoftAssert();
		String expectedTitle = "Home | Salesforce";
		String actualTitle = driver.getTitle();
		System.out.println("Verifying the title of Salesforce page");
		softAssert.assertEquals(actualTitle, expectedTitle);
		
		log.warn("The expected title '" + expectedTitle + "' may not be the same as actual title - '" + actualTitle + "'");
		
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Locators.properties");
		Properties properties = new Properties();
		properties.load(fis);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		WebElement title = driver.findElement(By.xpath(properties.getProperty("class")));
		String titleAttribute = title.getAttribute("title");

		if (!titleAttribute.equals("Service")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("app.launcher")))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("search")))).sendKeys(service);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("service.button")))).click();
		}

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("performance")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("account.tab")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("new.acc")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("business")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("next.acc")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("name.acc")))).sendKeys(accountContact);
		
		log.info("The Account Name is '" + accountContact + "'");
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("phone.acc")))).sendKeys(accountPhone);
		
		log.info("The Phone Account is '" + accountPhone + "'");
		
		WebElement address = driver.findElement(By.xpath(properties.getProperty("same.as.billing")));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", address);
		address.click();
		
		log.info("The shipping adress is same as the billing address!");

		WebElement directory = driver.findElement(By.xpath(properties.getProperty("directory.listed")));
		JavascriptExecutor jsTwo = (JavascriptExecutor) driver;
		jsTwo.executeScript("arguments[0].scrollIntoView();", directory);
		directory.click();

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("save.acc")))).click();
		
		log.info("The account is successfully saved!");
		
		softAssert.assertAll();

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