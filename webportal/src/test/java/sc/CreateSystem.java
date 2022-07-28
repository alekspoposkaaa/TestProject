package sc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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

public class CreateSystem extends Login {

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
	 * Script used to create a System on https://test.salesforce.com/page with using locators for the elements
	 * @param systems
	 * @param urlSystem
	 * @param systemName
	 * @throws IOException means Those methods throw the IOException whenever an input or output operation is failed or interpreted
	 */
	@Test(dataProvider="dataProviders", dataProviderClass = CreateDataProviders.class)
	public void createSystem(String systems, String urlSystem, String systemName) throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Locators.properties");
		Properties properties = new Properties();
		properties.load(fis);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		FileInputStream excelBook = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\Screenshots.xlsx");
		XSSFWorkbook myWorkbook = new XSSFWorkbook(excelBook);
		XSSFSheet mySheet = myWorkbook.getSheetAt(2);
		XSSFDrawing myDrawing = mySheet.createDrawingPatriarch();

		// 1
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("app.launcher")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("search")))).sendKeys(systems);

		File screenshotFileSystemsTab = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotFileSystemsTab, new File(".//screenshot//systemsApp.jpeg"));
		InputStream inputStreamSystems = new FileInputStream(".//screenshot//systemsApp.jpeg");
		byte[] bytesSystems = IOUtils.toByteArray(inputStreamSystems);
		int idSystems = myWorkbook.addPicture(bytesSystems, Workbook.PICTURE_TYPE_JPEG);
		inputStreamSystems.close();
		ClientAnchor anchorSystems = new XSSFClientAnchor();
		anchorSystems.setCol1(3);
		anchorSystems.setRow1(3);
		XSSFPicture pictureSystems = myDrawing.createPicture(anchorSystems, idSystems);
		pictureSystems.resize(0.8, 0.8);

		// 2
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("systems.tab")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("new.system")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("new.info")))).click();
		
		//assertions
		String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl);
        System.out.println("AssertNotNull Test Passed");
        System.out.println(currentUrl);
        
        log.warn("The current url is " + currentUrl);
		
		File screenshotFileNewSystem = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotFileNewSystem, new File(".//screenshot//newSystem.jpeg"));
		InputStream inputStreamNewSystem = new FileInputStream(".//screenshot//newSystem.jpeg");
		byte[] bytesNewSystem = IOUtils.toByteArray(inputStreamNewSystem);
		int idNewSystem = myWorkbook.addPicture(bytesNewSystem, Workbook.PICTURE_TYPE_JPEG);
		inputStreamNewSystem.close();
		ClientAnchor anchorNewSystem = new XSSFClientAnchor();
		anchorNewSystem.setCol1(3);
		anchorNewSystem.setRow1(4);
		XSSFPicture pictureNewSystem = myDrawing.createPicture(anchorNewSystem, idNewSystem);
		pictureNewSystem.resize(0.7, 0.7);

		// 3
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("url.system")))).sendKeys(urlSystem);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("system.name")))).sendKeys(systemName);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("save.system")))).click();
		
		log.info("New system with name '" + systemName + "and url '" + urlSystem + "' is successfully saved!");
		
		File screenshotFileSaveSystem = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotFileSaveSystem, new File(".//screenshot//saveSystem.jpeg"));
		InputStream inputStreamSaveSystem = new FileInputStream(".//screenshot//saveSystem.jpeg");
		byte[] bytesSaveSystem = IOUtils.toByteArray(inputStreamSaveSystem);
		int idSaveSystem = myWorkbook.addPicture(bytesSaveSystem, Workbook.PICTURE_TYPE_JPEG);
		inputStreamSaveSystem.close();
		ClientAnchor anchorSaveSystem = new XSSFClientAnchor();
		anchorSaveSystem.setCol1(3);
		anchorSaveSystem.setRow1(5);
		XSSFPicture pictureSaveSystem = myDrawing.createPicture(anchorSaveSystem, idSaveSystem);
		pictureSaveSystem.resize(0.8, 0.8);

		FileOutputStream fileOutTwo = new FileOutputStream(System.getProperty("user.dir") + "\\src\\test\\Screenshots.xlsx");
		myWorkbook.write(fileOutTwo);
		
		log.info("The screenshots are successfully saved in Excel file - 'Screenshots.xlsx' !");

		fileOutTwo.close();
		myWorkbook.close();

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