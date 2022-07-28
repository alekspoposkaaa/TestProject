package wait;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import data.providers.CreateDataProviders;
import login.Login;

/**
 * Class used to handle Assertions, Annotations and TestNG within the Salesforce
 * @author Aleksandra.Pavleska
 */
public class DownloadFile extends Login {

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
	 * Script used to download a File from Files Section on https://test.salesforce.com/ page with using locators for the elements
	 * @param service
	 * @throws IOException means Those methods throw the IOException whenever an input or output operation is failed or interpreted
	 */
	@Test(enabled=false, dataProvider="dataProviders", dataProviderClass = CreateDataProviders.class)
	public void downloadFileTwo(String service) throws IOException {
		
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Locators.properties");
		Properties properties = new Properties();
		properties.load(fis);

		// IMPLICIT WAIT
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		WebElement title = driver.findElement(By.xpath(properties.getProperty("class")));
		String titleAttribute = title.getAttribute("title");

		if (!titleAttribute.equals("Service")) {
			driver.findElement(By.xpath(properties.getProperty("app.launcher"))).click();
			driver.findElement(By.xpath(properties.getProperty("search"))).sendKeys(service);
			driver.findElement(By.xpath(properties.getProperty("service.button"))).click();
		}
		log.info("Navigated to Service App");

		driver.findElement(By.xpath(properties.getProperty("performance"))).click();
		driver.findElement(By.xpath(properties.getProperty("files"))).click();
		
		//assertions
		WebElement filesTab = driver.findElement(By.xpath(properties.getProperty("files")));
		Assert.assertEquals(true, filesTab.isDisplayed());
		log.warn("Files Tab should be displayed!");
		
		driver.findElement(By.xpath(properties.getProperty("file"))).click();
		driver.findElement(By.xpath(properties.getProperty("download"))).click();
		
		log.info("The following file is successfully downloaded");

	}
	
	/**
	 * Method that closes the Chrome browser
	 */
	@AfterMethod
	public void closeWebsite()
	{
		driver.quit();
		log.info("The browser is closed");
	}
}