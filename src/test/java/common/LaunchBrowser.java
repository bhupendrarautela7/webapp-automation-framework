package common;
import java.util.logging.Logger;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import common.TestBase;
import junit.framework.Assert;

public class LaunchBrowser extends TestBase {
	
	
	@BeforeSuite
	@Parameters("browser")
	public void initbrowser(String browser)
	{
		log=Logger.getLogger(LaunchBrowser.class.getName());
		try
		{
			System.out.println("Iniating Browser."+ browser);
			initBrowser(browser);
			loadPropertiesFile();
			

		}
		catch(Exception e)
		{
			
			e.printStackTrace();
			Assert.fail();
		}
	}



	//@AfterSuite
	public void closeBrowser()
	{
		dvr.quit();


	}
	

}
