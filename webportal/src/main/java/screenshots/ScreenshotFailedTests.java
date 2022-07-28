package screenshots;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import login.Login;

/**
 * Script used to capture screenshots for failed test cases within Salesforce
 * @author Aleksandra.Pavleska
 */

public class ScreenshotFailedTests extends Login {
	
	/**
	 * Method used to create a screenshot with proper name and save it in proper location
	 * @throws IOException
	 */
	public void getScreenshot() throws IOException {
		
		Date currentDate = new Date();
		String pictureName = currentDate.toString().replace(" ", "-").replace(":", "-");
		File screenshotPicture = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotPicture, new File(".//screenshot//" + pictureName + ".jpg"));
	}

}
