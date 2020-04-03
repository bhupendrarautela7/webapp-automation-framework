package caroobi;
import org.testng.annotations.Test;
import java.io.IOException;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import common.LaunchBrowser;
import common.TestBase;
import junit.framework.Assert;

public class CaroobiHomePage extends LaunchBrowser  {
	
	@Test(priority = 0)
	public void OpenHomePage_test02() throws Exception {

		try {
			openURL(propConfig.getProperty("HomePageUrl"));
			
			System.out.println("Opened home page");

		} catch (Exception e) {
			addErrorlogs(e, "Error on opening  page.");
		}

	}
	
	@Test(priority = 1)
	public void Accept_Cookies_dialog() throws Exception {

		try {
			
			
			click(By.id(propObjctRepo.getProperty("id_cookieCta")));
			System.out.println("Accepted cookies on home page");

			
	
			
		} catch (Exception e) {
			addErrorlogs(e, "Not found accpet cookie dialog.");
		}

	}
	
	@Test(priority = 2)
	public void Check_haeder_on_homepage() throws Exception {

		try {
			
			click(By.xpath(propObjctRepo.getProperty("xpath_abc")));
			System.out.println("header present on home page");

			
		} catch (Exception e) {
			addErrorlogs(e, "Error message");
		}
		
	}
		@Test(priority = 3)
		public void Check_Funnel_on_homepage() throws Exception {

			try {
				
				System.out.println("Funnel is present on home page");
				
				
			} catch (Exception e) {
				addErrorlogs(e, "Error message");
			}

	}
		
		@Test(priority = 4)
		public void Check_Banner_Image_on_homepage() throws Exception {

			try {
				
				System.out.println("banner is present on home page");
			
				
			} catch (Exception e) {
				addErrorlogs(e, "Error message");
			}

	
		}
		
		@SuppressWarnings("deprecation")
		@Test(priority = 5)
		public void CheckBannerWigetsHomepage() throws Exception, InterruptedException {

			boolean isDisplayed = isElementDisplayed(By.id(propObjctRepo.getProperty("xpath_abc")));
		    Assert.assertTrue(isDisplayed);
}
}




