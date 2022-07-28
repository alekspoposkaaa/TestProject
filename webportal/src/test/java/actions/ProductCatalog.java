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
import data.providers.CreateDataProviders;
import login.Login;
import org.junit.Assert;

/**
 * Class used to handle Assertions, Annotations and TestNG within the Salesforce
 * @author Aleksandra.Pavleska
 */

public class ProductCatalog extends Login {

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
	 * Script used to create Partner Product Catalog on https://test.salesforce.com/ page with using locators for the elements
	 * @param partnerOrder
	 * @param catalogName
	 * @param catalogID
	 * @param terms
	 * @param price
	 * @throws IOException means Those methods throw the IOException whenever an input or output operation is failed or interpreted
	 * @throws InterruptedException
	 */
	@Test(dataProvider="dataProviders", dataProviderClass = CreateDataProviders.class)
	public void createCatalog(String partnerOrder, String catalogName, String catalogID, String terms, String price) throws IOException, InterruptedException {
				
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
		
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Locators.properties");
		Properties properties = new Properties();
		properties.load(fis);
		
		WebElement title = driver.findElement(By.xpath(properties.getProperty("class")));
		String titleAttribute = title.getAttribute("title");

		if (!titleAttribute.equals("Partner Order")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("app.launcher")))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("search")))).sendKeys(partnerOrder);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("par.order")))).click();
		}
		
		log.info("Navigated to Partner Order App!");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("performance")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("catalogs")))).click();
		
		//assertions
		WebElement partnerCatalog = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("catalogs"))));
		Assert.assertEquals(true, partnerCatalog.isDisplayed());
		log.warn("Partner Product Catalog Tab should be displayed!");
					
		String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl);
        System.out.println("AssertNotNull Test Passed");
        System.out.println(currentUrl);
        
        log.warn("The current url is " + currentUrl);
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("new.catalog")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("catalog.name")))).sendKeys(catalogName);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("catalog.id")))).sendKeys(catalogID);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("contract.terms")))).sendKeys(terms);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("select.automations")))).click();

		WebElement renew = driver.findElement(By.xpath(properties.getProperty("renew.override")));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", renew);
		renew.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("pricing.type")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("fixed")))).click();

		WebElement contract = driver.findElement(By.xpath(properties.getProperty("contract.override")));
		JavascriptExecutor jsTwo = (JavascriptExecutor) driver;
		jsTwo.executeScript("arguments[0].scrollIntoView();", contract);
		contract.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("fixed.price")))).sendKeys(price);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("save.catalog")))).click();
		
		log.info("The Partner Product Catalog is successfully created!");
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