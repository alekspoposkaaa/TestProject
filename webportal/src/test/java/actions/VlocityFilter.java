package actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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

public class VlocityFilter extends Login {

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
	 * Script used to create vlocity filter on https://test.salesforce.com/ page with using locators for the elements
	 * @param cpq
	 * @param filterName
	 * @throws IOException means Those methods throw the IOException whenever an input or output operation is failed or interpreted
	 */
	@Test(enabled=false, dataProvider="dataProviders", dataProviderClass = CreateDataProviders.class)
	public void vlocityFilter(String cpq, String filterName) throws IOException {
		
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

		if (!titleAttribute.equals("Configure Price Quote (CPQ)")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("app.launcher")))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("search")))).sendKeys(cpq);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("cpq.button")))).click();
		}
		
		log.info("Navigated to CPQ App");
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("orders.click")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("vlocity.filter")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("new.filter")))).click();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("name.filter")))).sendKeys(filterName);
		
		log.info("The name of the vlocity filter is " + filterName);

		WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("select.filter"))));
		Select select = new Select(dropdown);
		select.selectByIndex(4);

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("save.filter")))).click();
		
		log.info("Vlocity Filter is successfully created!");
		
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