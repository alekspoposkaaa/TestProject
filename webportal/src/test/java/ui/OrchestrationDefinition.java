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
public class OrchestrationDefinition extends Login {

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
	 * Script used to create an specific Orchestration Plan Definition on https://test.salesforce.com/ page with using locators for the elements
	 * @param order
	 * @param planName
	 * @throws IOException means Those methods throw the IOException whenever an input or output operation is failed or interpreted
	 */
	@Test(dataProvider = "dataProviders", dataProviderClass = CreateDataProviders.class)
	public void editPlanDefinition(String order, String planName) throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Locators.properties");
		Properties properties = new Properties();
		properties.load(fis);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		WebElement title = driver.findElement(By.xpath(properties.getProperty("class")));
		String titleAttribute = title.getAttribute("title");

		if (!titleAttribute.equals("Order Management (OM)")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("app.launcher")))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("search")))).sendKeys(order);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("order.man.button")))).click();
		}
		log.info("Navigated to Order Management App!");

		// assertions
		String currentUrl = driver.getCurrentUrl();
		Assert.assertNotNull(currentUrl);
		System.out.println("AssertNotNull Test Passed");
		System.out.println(currentUrl);
		
		log.warn("The current url is " + currentUrl);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("orders.click")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("orchestration.definition.tab")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("new.definition.plan")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("plan.name")))).sendKeys(planName);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("save.definition.plan")))).click();
		
		log.info("New Orchestration Definition Plan with name '" + planName + "' is successfully saved!");

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