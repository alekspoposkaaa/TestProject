package pi;

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

public class CreateOrchestrationPlan extends Login {

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
	 * Class used to create a freezing orchestration plan on https://test.salesforce.com/ page with using locators for the elements
	 * @param order
	 * @throws IOException means Those methods throw the IOException whenever an input or output operation is failed or interpreted
	 */
	@Test(enabled=false, dataProvider="dataProviders", dataProviderClass = CreateDataProviders.class)
	public void orchestrationPlan(String order) throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Locators.properties");
		Properties properties = new Properties();
		properties.load(fis);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement title = driver.findElement(By.xpath(properties.getProperty("class")));
		String titleAttribute = title.getAttribute("title");

		if (!titleAttribute.equals("Order Management (OM)")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("app.launcher")))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("search")))).sendKeys(order);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("order.man.button")))).click();
		}
		
		log.info("Navigated to Order Management App");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("orders.click")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("plans.tab")))).click();
		
		//assertions
		WebElement orchestrationPlans = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("plans.tab"))));
		Assert.assertEquals(true, orchestrationPlans.isDisplayed());
		log.warn("Orchestration Plans Tab should be displayed!");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("new.plan")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("progress.key")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("freezing.key")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("save.plan")))).click();
		
		log.info("New Orchestration Plan is successfully created!");
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