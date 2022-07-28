package pi;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class CreateProduct extends Login {

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
	 * Script used to create an active Product on https://test.salesforce.com/ page with using locators for the elements
	 * @param cpq
	 * @param productName
	 * @param productCode
	 * @throws IOException means Those methods throw the IOException whenever an input or output operation is failed or interpreted
	 */
	@Test(enabled=false, dataProvider="dataProviders", dataProviderClass = CreateDataProviders.class)
	public void createProduct(String cpq, String productName, String productCode) throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Locators.properties");
		Properties properties = new Properties();
		properties.load(fis);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement title = driver.findElement(By.xpath(properties.getProperty("class")));
		String titleAttribute = title.getAttribute("title");

		if (!titleAttribute.equals("Configure Price Quote (CPQ)")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("app.launcher")))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("search")))).sendKeys(cpq);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("cpq.button")))).click();
		}
		
		log.info("Navigated to CPQ App!");

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("orders.click")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("products.tab")))).click();
		
		//assertions
		WebElement productsTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("products.tab"))));
		Assert.assertEquals(true, productsTab.isDisplayed());
		log.info("Products Tab should be displayed!");
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("new.product")))).click();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("product.name")))).sendKeys(productName);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("product.code")))).sendKeys(productCode);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("orderable")))).click();

		WebElement checkbox = driver.findElement(By.xpath(properties.getProperty("active")));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", checkbox);
		checkbox.click();

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("save.product")))).click();
		
		log.info("New Product with name '" + productName + "' and code '" + productCode + "' is successfully saved!");

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