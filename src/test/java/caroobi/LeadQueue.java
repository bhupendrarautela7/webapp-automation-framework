package caroobi;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import common.LaunchBrowser;
import junit.framework.Assert;

public class LeadQueue extends LaunchBrowser {

	String sf_lead_id = null;

	@Test(priority = 0)
	public void login_to_starfleet_application() throws Exception {

		try {

			loginGmail();
			Thread.sleep(1000);
			openURL(propConfig.getProperty("rocket_lead_queue_url"));
			Thread.sleep(6000);
			click(By.xpath(propObjctRepo.getProperty("xpath_starfleet_sing_in_with_google_button")));
			System.out.println("Clicked on button'Login as Google' in starfleet.");
			Thread.sleep(6000);
			scrolltoElement(By.xpath(propObjctRepo.getProperty("xpath_starfleet_sales_menu")));
			click(By.xpath(propObjctRepo.getProperty("xpath_starfleet_sales_menu")));
			System.out.println("Clicked on sales menu on starfleet dashboard.");
			Thread.sleep(2000);
			scrolltoElement(By.linkText(propObjctRepo.getProperty("link_starfleet_sales_menu_leadq")));
			click(By.linkText(propObjctRepo.getProperty("link_starfleet_sales_menu_leadq")));
			System.out.println("Clicked on lead queue sub menu.");

	

		} catch (Exception e) {
			addErrorlogs(e, "Error on opening lead queue page.");
			

		}

	}
	
	
	@Test(priority = 1)
	public void apply_filters_in_lead_queue() throws Exception {
		
    System.out.println("Applying filters to see last few hour's leads in lead queue.");
    
    Thread.sleep(2000);
    System.out.println("1");
    scrolltoElement(By.xpath(propObjctRepo.getProperty("xpath_hr_filter_dropdown")));
    click(By.xpath(propObjctRepo.getProperty("xpath_hr_filter_dropdown")));
	Thread.sleep(2000);
	 System.out.println("2");
	scrolltoElement(By.xpath(propObjctRepo.getProperty("xpath_hr_filter_dropdown_search")));
    clear(By.xpath(propObjctRepo.getProperty("xpath_hr_filter_dropdown_search")));
	Thread.sleep(2000); 
	 System.out.println("3");
	scrolltoElement(By.xpath(propObjctRepo.getProperty("xpath_hr_filter_dropdown_search")));
    type(By.xpath(propObjctRepo.getProperty("xpath_hr_filter_dropdown_search")), "3");
	Thread.sleep(2000);
	 System.out.println("4");
	scrolltoElement(By.xpath(propObjctRepo.getProperty("xpath_hr_filter_dropdown_search")));
    enter(By.xpath(propObjctRepo.getProperty("xpath_hr_filter_dropdown_search")));
	Thread.sleep(2000);
	 System.out.println("5");
	scrolltoElement(By.xpath(propObjctRepo.getProperty("xpath_hr_filter_button")));
    click(By.xpath(propObjctRepo.getProperty("xpath_hr_filter_button")));
    
    
    
 /*   driver.findElement(By.xpath("")).click();
    driver.findElement(By.xpath("")).clear();
    driver.findElement(By.xpath("")).sendKeys("2");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='-Hours-'])[2]/following::input[1]")).sendKeys(Keys.ENTER);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='-Days-'])[2]/following::i[1]")).click();
     */
    
    
    
	}
	
	
	
	

	@Test(priority = 2)
	public void get_lead_to_verify_in_Lead_queue() throws Exception {

		try {

			// globalLeadId

			CreateLead cl = new CreateLead();
			int lead_id = cl.globalLeadId;
			System.out.println("=====================================================");
			System.out.println("Will check this lead in lead queue [Backend ID] : " + lead_id);

			sf_lead_id = cl.globalSFLeadId;

			if (sf_lead_id == null)

			{
				System.out.println("Lead is not synced to SF, Sf lead ID  : " + sf_lead_id);

			}

			else {
				
			

				System.out.println("Will check this lead in starfleet lead queue [Salesforce ID] : " + sf_lead_id);

			}

		} catch (Exception e) {
			addErrorlogs(e, "error on checking lead in lead queue");

		}

	}

	@Test(priority = 3)
	public void check_lead_on_lead_queue() throws Exception {
		
		try {
			
			;
			Thread.sleep(5000);
			System.out.println("Verifying, Is test lead is synced to lead queue?");
			scrolltoElement(By.xpath(propObjctRepo.getProperty("xpath_starfleet_lead_queue_row")));
		boolean synced = isSFleadPresentonStarfleet(By.xpath(propObjctRepo.getProperty("xpath_starfleet_lead_queue_row")),
				sf_lead_id);
		
		System.out.println(synced + " status");

		if (synced == true)

		{

			System.out.println("Lead is synced to Lead Queue and 'Lead Score' is calculated Successfully !");

		}

		else if (synced == false)

		{

			Assert.fail("Lead is not synced and not showing in Lead queue table");

		}}
		
		catch (Exception e) {
			addErrorlogs(e, "error on checking lead in lead queue");
			Assert.fail("Lead is not synced, Testcase is failed.");

		}

	}

	@Test(priority = 4)
	public void check_lead_score_in_lead_queue() throws Exception {
		
		try {
		String xpathpart1 = propObjctRepo.getProperty("xpath_starfleet_lead_scor_part1");
	    String xpathpart2 = propObjctRepo.getProperty("xpath_starfleet_lead_scor_part2");
		
        String xpath = xpathpart1+ sf_lead_id+xpathpart2;
	    String leadscore = (getText(By.xpath(xpath), sf_lead_id));
	    
	    System.out.println("Lead socre for the lead is : " + leadscore);
	    
	    
		System.out.println("=====================================================");

	}
		catch (Exception e) {
			addErrorlogs(e, "error on checking lead in lead queue");
			Assert.fail("Lead is not synced So can't check lead score.");

		}
		
	}

}