package caroobi;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import common.LaunchBrowser;
import common.TestBase;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;

public class Check_Latest_Lead_Created extends  TestBase  {


	@BeforeTest
	public void beforeTest() {  

	}

	@Test
	public void Get_Latest_lead_Created() {

		getLatestLead(propConfig.getProperty("Prod_host"), propConfig.getProperty("Prod_dbUser"), propConfig.getProperty("Prod_dbPassword"),propConfig.getProperty("database"),propConfig.getProperty("query"));

	}



}
