package data.providers;

import java.lang.reflect.Method;
import org.testng.annotations.DataProvider;

/**
 * Class for DataProviders who are used to pass data to test scripts in TestNG
 * @author Aleksandra.Pavleska
 */

public class CreateDataProviders {
	
	/**
	 * Method used to return the required data for appropriate method
	 * @param m 
	 * @return returns the values of the objects
	 */
	@DataProvider(name = "dataProviders")
	public Object[][] dataSet(Method m) {

		Object[][] dataInfo = null;

		if (m.getName().equals("accountCpq")) {
			
			dataInfo = new Object[][] {

				{ "cpq", "Aleks P", "077555444" } 
			};
		} else if(m.getName().equals("accountService"))
		{
			dataInfo = new Object[][] {
				{ "service", "Aleks P", "071898525" }
		};
		} else if(m.getName().equals("createLicense"))
		{
			dataInfo = new Object[][] {
				{ "Licenses", "Salesforce License" }
		};
		} else if(m.getName().equals("createVlocityRule"))
		{
			dataInfo = new Object[][] {
				{ "cpq", "RuleNumberOne" }
		};
		} else if(m.getName().equals("createCatalog"))
		{
			dataInfo = new Object[][] {
				{ "partner order", "Vogue", "1906", "Automations", "99,00" }
		};
		} else if(m.getName().equals("vlocityFilter"))
		{
			dataInfo = new Object[][] {
				{ "cpq", "Filter1" }
		};
		} else if(m.getName().equals("createContact"))
		{
			dataInfo = new Object[][] {
				{ "service", "077555444", "Trajkovska", "Aleks P" }
		};
		} else if(m.getName().equals("orchestrationPlan"))
		{
			dataInfo = new Object[][] {
				{ "order management" }
		};
		} else if(m.getName().equals("createPayment"))
		{
			dataInfo = new Object[][] {
				{ "payment adjustments", "Drag&Drop example", "Aleks P" }
		};
		} else if(m.getName().equals("createProduct"))
		{
			dataInfo = new Object[][] {
				{ "cpq", "5G Network", "5G" }
		};
		} else if(m.getName().equals("contactRequest"))
		{
			dataInfo = new Object[][] {
				{ "contact requests", "Petrovska" }
		};
		} else if(m.getName().equals("createPackage"))
		{
			dataInfo = new Object[][] {
				{ "packages", "QA", "Aleks P" }
		};
		} else if(m.getName().equals("createSystem"))
		{
			dataInfo = new Object[][] {
				{ "systems", "www.qa.com", "QA System" }
		};
		} else if(m.getName().equals("createLead"))
		{
			dataInfo = new Object[][] {
				{ "marketing", "Smith", "SEAVUS" }
		};
		} else if(m.getName().equals("createPartnerContract"))
		{
			dataInfo = new Object[][] {
				{ "partner contract terms", "Seavus Contract", "QA", "a", "c", "a", "v" }
		};
		} else if(m.getName().equals("createPaymentTwo"))
		{
			dataInfo = new Object[][] {
				{ "payment adjustments", "Slider example", "Aleks P" }
		};
		} else if(m.getName().equals("createAccount"))
		{
			dataInfo = new Object[][] {
				{ "service", "Aleks P", "071898525" }
		};
		} else if(m.getName().equals("downloadFile"))
		{
			dataInfo = new Object[][] {
				{ "service" }
		};
		} else if(m.getName().equals("editGroup"))
		{
			dataInfo = new Object[][] {
				{ "service", "Automation TestInfo" }
		};
		} else if(m.getName().equals("editPlanDefinition"))
		{
			dataInfo = new Object[][] {
				{ "order management", "Seavus Plan" }
		};
		} else if(m.getName().equals("emailList"))
		{
			dataInfo = new Object[][] {
				{ "service", "Project", "Sara@seavus.com" }
		};
		} else if(m.getName().equals("createContactTwo"))
		{
			dataInfo = new Object[][] {
				{ "service", "077555444", "Trajkovska", "Aleks P" }
		};
		} else if(m.getName().equals("createLeadTwo"))
		{
			dataInfo = new Object[][] {
				{ "marketing", "Smith", "SEAVUS" }
		};
		} else if(m.getName().equals("createPaymentThree"))
		{
			dataInfo = new Object[][] {
				{ "payment adjustments", "Slider example", "Aleks P" }
		};
		} else if(m.getName().equals("downloadFileTwo"))
		{
			dataInfo = new Object[][] {
				{ "service" }
		};
		} else if(m.getName().equals("emailListTwo"))
		{
			dataInfo = new Object[][] {
				{ "service", "Project", "Sara@seavus.com" }
		};
		
		}
		
		return dataInfo;
	}
}

