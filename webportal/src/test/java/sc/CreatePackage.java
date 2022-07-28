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

public class CreatePackage extends Login {

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
	 * Script used to create a Package on https://test.salesforce.com/page with using locators for the elements
	 * @param packages
	 * @param namePackage
	 * @param developer
	 * @throws IOException means Those methods throw the IOException whenever an input or output operation is failed or interpreted
	 */
	@Test(enabled=false, dataProvider="dataProviders", dataProviderClass = CreateDataProviders.class)
	public void createPackage(String packages, String namePackage, String developer) throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Locators.properties");
		Properties properties = new Properties();
		properties.load(fis);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		FileInputStream excelBook = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\Screenshots.xlsx");
		XSSFWorkbook myWorkbook = new XSSFWorkbook(excelBook);
		XSSFSheet mySheet = myWorkbook.getSheetAt(1);
		XSSFDrawing myDrawing = mySheet.createDrawingPatriarch();

		// 1
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("app.launcher")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("search")))).sendKeys(packages);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("packages.tab")))).click();
		
		//assertions
		WebElement packagesTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("packages.tab"))));
		Assert.assertEquals(true, packagesTab.isDisplayed());
		log.warn("Packages Tab should be displayed!");

		File screenshotFilePackageTab = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotFilePackageTab, new File(".//screenshot//packagesApp.jpeg"));
		InputStream inputStreamPackages = new FileInputStream(".//screenshot//packagesApp.jpeg");
		byte[] bytes = IOUtils.toByteArray(inputStreamPackages);
		int idPackages = myWorkbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
		inputStreamPackages.close();
		ClientAnchor anchorPackages = new XSSFClientAnchor();
		anchorPackages.setCol1(3);
		anchorPackages.setRow1(3);
		XSSFPicture picturePackages = myDrawing.createPicture(anchorPackages, idPackages);
		picturePackages.resize(0.8, 0.8);

		// 2
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("new.package")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("new.info")))).click();

		File screenshotFileNewPackage = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotFileNewPackage, new File(".//screenshot//newPackage.jpeg"));
		InputStream inputStreamNewPackage = new FileInputStream(".//screenshot//newPackage.jpeg");
		byte[] bytesTwo = IOUtils.toByteArray(inputStreamNewPackage);
		int idNewPackage = myWorkbook.addPicture(bytesTwo, Workbook.PICTURE_TYPE_JPEG);
		inputStreamNewPackage.close();
		ClientAnchor anchorNewPackage = new XSSFClientAnchor();
		anchorNewPackage.setCol1(3);
		anchorNewPackage.setRow1(4);
		XSSFPicture pictureNewPackage = myDrawing.createPicture(anchorNewPackage, idNewPackage);
		pictureNewPackage.resize(0.7, 0.7);

		// 3
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("name.of.package")))).sendKeys(namePackage);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("developer.package.name")))).sendKeys(developer);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("save.package")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("save.success")))).click();
		
		log.info("New Package with name '" + namePackage + "is successfully created by '" + developer + "'");

		File screenshotFileSavePackage = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotFileSavePackage, new File(".//screenshot//savePackage.jpeg"));
		InputStream inputStreamSavePackage = new FileInputStream(".//screenshot//savePackage.jpeg");
		byte[] bytesThree = IOUtils.toByteArray(inputStreamSavePackage);
		int idSavePackage = myWorkbook.addPicture(bytesThree, Workbook.PICTURE_TYPE_JPEG);
		inputStreamSavePackage.close();
		ClientAnchor anchorSavePackage = new XSSFClientAnchor();
		anchorSavePackage.setCol1(3);
		anchorSavePackage.setRow1(5);
		XSSFPicture pictureSavePackage = myDrawing.createPicture(anchorSavePackage, idSavePackage);
		pictureSavePackage.resize(0.8, 0.8);

		FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir") + "\\src\\test\\Screenshots.xlsx");
		myWorkbook.write(fileOut);
		
		log.info("The screenshots are successfully saved in Excel file - 'Screenshots.xlsx' !");

		fileOut.close();
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