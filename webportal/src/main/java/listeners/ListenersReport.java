package listeners;

import java.util.List;
import java.util.Map;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;

public class ListenersReport implements IReporter {

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

		ITestContext testContext = null;
		String suiteName = null;

		for (ISuite suite : suites) {

			suiteName = suite.getName();
			Map<String, ISuiteResult> suiteResults = suite.getResults();

			for (ISuiteResult suiteResult : suiteResults.values()) {

				testContext = suiteResult.getTestContext();
			}
		}

		Email email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("annnnepavleskaa@gmail.com", "wsgcrskqqlcjfrmf"));
		email.setSSLOnConnect(true);

		try {
			email.setFrom("alekspoposkaaa@gmail.com");
			email.setSubject("Status" + suiteName);
			email.setMsg("Total run tests are");
			email.addTo("alekspetrovskaaa@gmail.com");
			email.send();
		} catch (EmailException emails) {
			System.out.print("Email sent!");
			emails.printStackTrace();
		}

	}

}
