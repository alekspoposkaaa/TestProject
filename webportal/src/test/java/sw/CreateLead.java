package sw;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
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

public class CreateLead extends Login {

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
	 * Script used to create a lead on https://test.salesforce.com/page with using locators for the elements
	 * @param marketing
	 * @param leadName
	 * @param companyLead
	 * @throws IOException means Those methods throw the IOException whenever an input or output operation is failed or interpreted
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(enabled=false, dataProvider="dataProviders", dataProviderClass = CreateDataProviders.class)
	public void createLead(String marketing, String leadName, String companyLead) throws IOException, InterruptedException, AWTException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Locators.properties");
		Properties properties = new Properties();
		properties.load(fis);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement title = driver.findElement(By.xpath(properties.getProperty("class")));
		String titleAttribute = title.getAttribute("title");

		if (!titleAttribute.equals("Marketing")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("app.launcher")))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("search")))).sendKeys(marketing);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("marketing.tab")))).click();
		}
		
		log.info("Navigated to Marketing App!");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("assistant")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("leads.tab")))).click();
		
		//assertions
		WebElement leadsTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("leads.tab"))));
		Assert.assertEquals(true, leadsTab.isDisplayed());
		log.warn("Leads Tab should be displayed!");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("refresh.here")))).click();

		WebElement click = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("refresh.here"))));
		Actions actions = new Actions(driver);
		actions.contextClick(click).perform();

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		log.info("The current page is now refreshed!");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("new.lead")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("lead.status")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("qualified.lead")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("salutation.lead")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("mister.lead")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("last.name.lead")))).sendKeys(leadName);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("company.lead")))).sendKeys(companyLead);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("save.lead")))).click();
		
		log.info("New Qualified Lead with name '" + leadName + "' with company '" + companyLead + "' is successfully saved!");

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