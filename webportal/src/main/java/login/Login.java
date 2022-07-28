package login;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Main class for login on Salesforce page
 * @author Aleksandra.Pavleska
 */

public class Login {

	public static WebDriver driver;
	public static Logger log = (Logger) LogManager.getLogger();

	/**
	 * Script used to login on https://test.salesforce.com/ page with using locators for the elements
	 * @throws IOException means Those methods throw the IOException whenever an input or output operation is failed or interpreted
	 */

	public static void loginToSalesforce(String browser) throws IOException {

		if (browser.equals("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);
		} else if (browser.equals("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else if (browser.endsWith("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Locators.properties");
		Properties properties = new Properties();
		properties.load(fis);

		driver.get(properties.getProperty("link"));
		driver.manage().window().maximize();
		driver.findElement(By.id(properties.getProperty("username"))).sendKeys("team.seavus@partner-prod.com.vlocitysbx");
		driver.findElement(By.id(properties.getProperty("password"))).sendKeys("seavusQA123!");
		driver.findElement(By.id(properties.getProperty("login"))).click();

	}

}
