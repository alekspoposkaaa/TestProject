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

public class CreateContactRequest extends Login {

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
	 * Script used to create an attempted Contact Request on https://test.salesforce.com/page with using locators for the elements
	 * @param contactRequest
	 * @param contact
	 * @throws IOException means Those methods throw the IOException whenever an input or output operation is failed or interpreted
	 */
	@Test(dataProvider="dataProviders", dataProviderClass = CreateDataProviders.class)
	public void contactRequest(String contactRequest, String contact) throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Locators.properties");
		Properties properties = new Properties();
		properties.load(fis);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		FileInputStream excelBook = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\Screenshots.xlsx");
		XSSFWorkbook myWorkbook = new XSSFWorkbook(excelBook);
		XSSFSheet mySheet = myWorkbook.getSheetAt(3);
		XSSFDrawing myDrawing = mySheet.createDrawingPatriarch();

		// 1
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("app.launcher")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("search")))).sendKeys(contactRequest);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("contact.requests.tab")))).click();
		
		//assertions
		String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl);
        System.out.println("AssertNotNull Test Passed");
        System.out.println(currentUrl);
        
        log.warn("The current url is " + currentUrl);
		
		File screenshotFileRequestsTab = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotFileRequestsTab, new File(".//screenshot//requestsApp.jpeg"));
		InputStream inputStreamRequests = new FileInputStream(".//screenshot//requestsApp.jpeg");
		byte[] bytesRequests = IOUtils.toByteArray(inputStreamRequests);
		int idRequests = myWorkbook.addPicture(bytesRequests, Workbook.PICTURE_TYPE_JPEG);
		inputStreamRequests.close();
		ClientAnchor anchorRequests = new XSSFClientAnchor();
		anchorRequests.setCol1(3);
		anchorRequests.setRow1(3);
		XSSFPicture pictureRequests = myDrawing.createPicture(anchorRequests, idRequests);
		pictureRequests.resize(0.8, 0.8);

		// 2
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("new.contact.request")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("contact.detail")))).click();

		File screenshotFileNewRequest = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotFileNewRequest, new File(".//screenshot//newRequest.jpeg"));
		InputStream inputStreamNewRequest = new FileInputStream(".//screenshot//newRequest.jpeg");
		byte[] bytesNewRequest = IOUtils.toByteArray(inputStreamNewRequest);
		int idNewRequest = myWorkbook.addPicture(bytesNewRequest, Workbook.PICTURE_TYPE_JPEG);
		inputStreamNewRequest.close();
		ClientAnchor anchorNewRequest = new XSSFClientAnchor();
		anchorNewRequest.setCol1(3);
		anchorNewRequest.setRow1(4);
		XSSFPicture pictureNewRequest = myDrawing.createPicture(anchorNewRequest, idNewRequest);
		pictureNewRequest.resize(0.8, 0.8);

		// 3
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("requested.by")))).sendKeys(contact);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("requested.by.acc")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("request.status")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("request.status.attempted")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("contact.detail")))).click();
		
		log.info("New Contact Request is requested by '" + contact + "'");

		File screenshotFileRequestInfo = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotFileRequestInfo, new File(".//screenshot//requestInfo.jpeg"));
		InputStream inputStreamRequestInfo = new FileInputStream(".//screenshot//requestInfo.jpeg");
		byte[] bytesRequestInfo = IOUtils.toByteArray(inputStreamRequestInfo);
		int idRequestInfo = myWorkbook.addPicture(bytesRequestInfo, Workbook.PICTURE_TYPE_JPEG);
		inputStreamRequestInfo.close();
		ClientAnchor anchorRequestInfo = new XSSFClientAnchor();
		anchorRequestInfo.setCol1(3);
		anchorRequestInfo.setRow1(5);
		XSSFPicture pictureRequestInfo = myDrawing.createPicture(anchorRequestInfo, idRequestInfo);
		pictureRequestInfo.resize(0.9, 0.9);

		// 4
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("save.request")))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(properties.getProperty("save.success")))).click();
		
		log.info("New Contact Request is successfully saved!");

		File screenshotFileSaveRequest = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotFileSaveRequest, new File(".//screenshot//saveRequest.jpeg"));
		InputStream inputStreamSaveRequest = new FileInputStream(".//screenshot//saveRequest.jpeg");
		byte[] bytesSaveRequest = IOUtils.toByteArray(inputStreamSaveRequest);
		int idSaveRequest = myWorkbook.addPicture(bytesSaveRequest, Workbook.PICTURE_TYPE_JPEG);
		inputStreamSaveRequest.close();
		ClientAnchor anchorSaveRequest = new XSSFClientAnchor();
		anchorSaveRequest.setCol1(3);
		anchorSaveRequest.setRow1(6);
		XSSFPicture pictureSaveRequest = myDrawing.createPicture(anchorSaveRequest, idSaveRequest);
		pictureSaveRequest.resize(0.8, 0.8);

		FileOutputStream fileOutThree = new FileOutputStream(System.getProperty("user.dir") + "\\src\\test\\Screenshots.xlsx");
		myWorkbook.write(fileOutThree);
		
		log.info("The screenshots are successfully saved in Excel file - 'Screenshots.xlsx' !");

		fileOutThree.close();
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