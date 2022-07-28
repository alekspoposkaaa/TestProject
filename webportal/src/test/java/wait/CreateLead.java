package wait;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
	 * @param nameLead
	 * @param companyLead
	 * @throws IOException means Those methods throw the IOException whenever an input or output operation is failed or interpreted
	 */
	@Test(enabled=false, dataProvider="dataProviders", dataProviderClass = CreateDataProviders.class)
	public void createLeadTwo(String marketing, String nameLead, String companyLead) throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Locators.properties");
		Properties properties = new Properties();
		properties.load(fis);
		
		//IMPLICIT WAIT
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		log.info("Implicit wait is successfully implemented!");

		WebElement title = driver.findElement(By.xpath(properties.getProperty("class")));
		String titleAttribute = title.getAttribute("title");

		if (!titleAttribute.equals("Marketing")) {
			driver.findElement(By.xpath(properties.getProperty("app.launcher"))).click();
			driver.findElement(By.xpath(properties.getProperty("search"))).sendKeys(marketing);
			driver.findElement(By.xpath(properties.getProperty("marketing.tab"))).click();
		}
		log.info("Navigated to Marketing App");

		driver.findElement(By.xpath(properties.getProperty("assistant"))).click();
		driver.findElement(By.xpath(properties.getProperty("leads.tab"))).click();
		
		//assertions
		WebElement leadsTab = driver.findElement(By.xpath(properties.getProperty("leads.tab")));
		Assert.assertEquals(true, leadsTab.isDisplayed());
		log.warn("Leads Tab should be displayed!");
		
		driver.findElement(By.xpath(properties.getProperty("new.lead"))).click();
		driver.findElement(By.xpath(properties.getProperty("lead.status"))).click();
		driver.findElement(By.xpath(properties.getProperty("qualified.lead"))).click();
		driver.findElement(By.xpath(properties.getProperty("salutation.lead"))).click();
		driver.findElement(By.xpath(properties.getProperty("mister.lead"))).click();
		driver.findElement(By.xpath(properties.getProperty("last.name.lead"))).sendKeys(nameLead);
		driver.findElement(By.xpath(properties.getProperty("company.lead"))).sendKeys(companyLead);
		driver.findElement(By.xpath(properties.getProperty("save.lead"))).click();
		
		log.info("New Qualified Lead with name '" + nameLead + "' and with company '" + companyLead + "' is successfully saved!");

		WebElement click = driver.findElement(By.xpath(properties.getProperty("edit.button")));
		Actions rightClick = new Actions(driver);
		rightClick.contextClick(click).perform();

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
