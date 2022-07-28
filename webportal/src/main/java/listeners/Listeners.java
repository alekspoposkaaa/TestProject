package listeners;

import java.io.IOException;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import screenshots.ScreenshotFailedTests;

/**
 * Class used as listener interface to modify the TestNG behaviour
 * @author Aleksandra.Pavleska
 */

public class Listeners extends ScreenshotFailedTests implements ITestListener {
	
	/**
	 * Methods used to implement the Listeners
	 */
	
	/**
	 * Method used to report the name of the used method in the specific script
	 */
	public void onTestStart(ITestResult result)
	{
		System.setProperty("org.uncommons.reporting.title", "Salesforce Test Report!");
		Reporter.log("The name of the method is " + result.getName());
		System.out.println("The test started");
	}
	
	/**
	 * Method used to report the status of the script execution
	 */
	public void onTestSuccess(ITestResult result)
	{
		Reporter.log("Status of execution " + result.getStatus());
	}
	
	/**
	 * Method used to capture the screenshot for failed test case
	 */
	public void onTestFailure(ITestResult result)
	{
		System.out.print("Failure, capture a screenshot!");
		try {
			getScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}