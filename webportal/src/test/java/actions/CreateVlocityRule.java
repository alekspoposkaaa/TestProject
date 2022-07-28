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

public class CreateVlocityRule extends Login {
	
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
	 * Script used to create an active vlocity rule on https://test.salesforce.com/ page with using locators for the elements
	 * @param cpq
	 * @param ruleName
	 * @throws IOException means Those methods throw the IOException whenever an input or output operation is failed or interpreted
	 */
	@Test(enabled=false, dataProvider="dataProviders", dataProviderClass = CreateDataProviders.class)
	public void createVlocityRule(String cpq, String ruleName) throws IOException {
		
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
		
		log.info("Navigated to CPQ app");
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("orders.click")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("vlocity.rules.tab")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("new.rule")))).click();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("name.of.rule")))).sendKeys(ruleName);

		WebElement ryle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("ryle.type"))));
		Select selectRyle = new Select(ryle);
		selectRyle.selectByIndex(2);

		WebElement applied = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("applied.to"))));
		Select selectApplied = new Select(applied);
		selectApplied.selectByIndex(1);

		WebElement status = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("status"))));
		Select selectStatus = new Select(status);
		selectStatus.selectByIndex(1);

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("save.rule")))).click();
		
		log.info("The vlocity rule is successfully saved!");
		
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