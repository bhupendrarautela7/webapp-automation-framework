package caroobi;

import org.testng.annotations.Test;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.mysql.jdbc.Statement;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import common.GetGoogleSheetData;
import common.LaunchBrowser;
import common.TestBase;
import common.dbConnections;
import junit.framework.Assert;

public class CreateHobertLead extends LaunchBrowser {
	
	
	public static int globalLeadId = 0;
	public static String globalSFLeadId = null;


	String name = null;
	String email = null;
	String phone = null;
	String fin = null;
	String address = null;
	String car_registraion = null;
	String question = null;
	String pin = null;
	String car_registraion_date = null;
	
	
	
	@Test(priority = 0)
	public void getLead_data_from_google_sheet() throws Exception {

		try {
			
			System.out.println("===========================================");
			System.out.println("Fatching google test data for Lead Creation Horbert.");
			

			/*String range_value = "B3:J";
			String sheetid = propConfig.getProperty("googlesheet_id");

			String[] res = getleaddata(sheetid, range_value);
			name = res[0];
			email = res[1];
			phone = res[2];
			fin = res[3];
			address = res[4];
			car_registraion = res[5];
			question = res[6];
			pin = res[7];
			car_registraion_date = res[8]; */
			
			String range_value = "B2:J2" ;
			
			ArrayList<String> leaddate  = GetGoogleSheetData.getsheetdata(range_value);
			//System.out.println("data returenes : " + leaddate);
			
			name= leaddate.get(0).toString();
			email= leaddate.get(1).toString();
			phone = leaddate.get(2).toString();
			fin = leaddate.get(3).toString();
			address = leaddate.get(4).toString();
			car_registraion = leaddate.get(5).toString();
			question = leaddate.get(6).toString();
			pin = leaddate.get(7).toString();
			car_registraion_date = leaddate.get(8).toString();
			
			System.out.println("Lead test data created successfully!");

		} catch (Exception e) {
			addErrorlogs(e, "Error on getting lead data");
		}

	}


	@Test(priority = 1)
	public void open_home_page_to_create_austausch_mototor_lead() throws Exception {

		try {
			
			System.out.println("==================================");
			openURL(propConfig.getProperty("rocket_home_page"));
			System.out.println("Homepage opened successfully.");

		} catch (Exception e) {
			addErrorlogs(e, "Error on opening page.");
		}

	}

	@Test(priority = 2)
	public void accept_cookies_dialog() throws Exception {

		try {

			
			click(By.id(propObjctRepo.getProperty("id_cookie_cta")));
			Thread.sleep(1000);
			System.out.println("Accepted cookies dialog on homepage.");

		} catch (Exception e) {
			addErrorlogs(e, "Not found accpet cookie dialog.");
		}

	}

	@Test(priority = 3)
	public void select_service_austausch_motor_on_homepage() throws Exception {

		try {

			System.out.println("Choosing service and car on home page.");
			// click(By.xpath(propObjctRepo.getProperty("xpath_Services_dropdown")));
			Thread.sleep(1000);

			click(By.xpath(propObjctRepo.getProperty("xpath_service_selection_dropdown")));
			Thread.sleep(1000);
			clear(By.xpath(propObjctRepo.getProperty("xpath_service_selection_textbox")));
			Thread.sleep(1000);
			type(By.xpath(propObjctRepo.getProperty("xpath_service_selection_textbox")), "Austausch motor");
			Thread.sleep(3000);
			scrolltoElement(By.xpath(propObjctRepo.getProperty("xpath_service_dropdown_austausch_motor_value")));

			click(By.xpath(propObjctRepo.getProperty("xpath_service_dropdown_austausch_motor_value")));

		} catch (Exception e) {
			addErrorlogs(e, "error on finding service selection on homepage.");
		}

	}

	@Test(priority = 4)
	public void select_car_on_homepage() throws Exception {

		try {

			click(By.xpath(propObjctRepo.getProperty("xpath_Auto_dropdown2")));
			System.out.println("Selected car from the dropdown on home page.");

		} catch (Exception e) {
			addErrorlogs(e, "error on selecting automobile brand from dropdown.");
		}

	}

	@Test(priority = 5)
	public void click_on_pin_textbox() throws Exception {

		try {

			click(By.xpath(propObjctRepo.getProperty("xpath_pin_input")));
			System.out.println("Filling PIN on home page");

		} catch (Exception e) {
			addErrorlogs(e, "Error on finding pin input field");
		}

	}

	@Test(priority = 6)
	public void clear_pin_textbox() throws Exception {

		try {

			clear(By.xpath(propObjctRepo.getProperty("xpath_pin_input")));

		} catch (Exception e) {
			addErrorlogs(e, "Error on clearing pin input box");
		}

	}

	@Test(priority = 7)
	public void enter_value_in_pin_textbox() throws Exception {

		try {

			type(By.xpath(propObjctRepo.getProperty("xpath_pin_input")), "1");
			type(By.xpath(propObjctRepo.getProperty("xpath_pin_input")), "0");
			type(By.xpath(propObjctRepo.getProperty("xpath_pin_input")), "1");
			type(By.xpath(propObjctRepo.getProperty("xpath_pin_input")), "1");
			type(By.xpath(propObjctRepo.getProperty("xpath_pin_input")), "5");
			
			System.out.println("Entered PIN on input box on home page.");

		} catch (Exception e) {
			addErrorlogs(e, "Error on entering pin");
		}

	}

	@Test(priority = 8)
	public void click_cta_on_homepage() throws Exception {

		try {

			
			click(By.xpath(propObjctRepo.getProperty("xpath_cta")));
			Thread.sleep(2000);
			System.out.println("Clicked on home page CTA button.");
			

		} catch (Exception e) {
			addErrorlogs(e, "Error on clicking CTA button on home page");
		}

	}
	
	
		@Test(priority = 9)
	public void create_lead_for_auto_offer_generation() throws Exception {

		try {

			
			System.out.println("Filling car details in the funnel...");
			Thread.sleep(7000);
			click(By.xpath(propObjctRepo.getProperty("xpath_model_family_dropdown")));
			Thread.sleep(2000);
			click(By.xpath(propObjctRepo.getProperty("xpath_karosserieform_dropdown")));
			Thread.sleep(2000);
			click(By.xpath(propObjctRepo.getProperty("xpath_model_year_dropdown")));
			Thread.sleep(2000);
			click(By.xpath(propObjctRepo.getProperty("xpath_model_no_dropdown")));
			Thread.sleep(2000);
			click(By.xpath(propObjctRepo.getProperty("xpath_car_details_submit_button")));
			System.out.println("Filling car details in the funnel successfully.");
			Thread.sleep(4000);
			click(By.xpath(propObjctRepo.getProperty("xpath_fixed_price_continue_button")));
			System.out.println("Clicked on get fixed price button.");
			
			clear(By.xpath(propObjctRepo.getProperty("xpath_austauschmotor_lead_name_textbox")));
			type(By.xpath(propObjctRepo.getProperty("xpath_austauschmotor_lead_name_textbox")), name);
			System.out.println("Entered customer name in lead.");
			click(By.xpath(propObjctRepo.getProperty("xpath_austauschmotor_lead_name_submit_button")));
			
			Thread.sleep(1000);
			clear(By.xpath(propObjctRepo.getProperty("xpath_austauschmotor_email_textbox")));
			type(By.xpath(propObjctRepo.getProperty("xpath_austauschmotor_email_textbox")), email);
			System.out.println("Entered customer email address in lead.");
			click(By.xpath(propObjctRepo.getProperty("xpath_austauschmotor_email_submit_button")));
			
			Thread.sleep(1000);
			clear(By.xpath(propObjctRepo.getProperty("xpath_austauschmotor_mobile_textbox")));
			type(By.xpath(propObjctRepo.getProperty("xpath_austauschmotor_mobile_textbox")), phone);
			System.out.println("Entered customer phone in lead.");
			
			
			click(By.xpath(propObjctRepo.getProperty("xpath_austauschmotor_mobile_submit_button")));
			System.out.println("Clicked on submit customer lead form button.");
			
			

			
			Thread.sleep(10000);
			scrolltoElement(By.id(propObjctRepo.getProperty("id_fixed_price_in_button")));
		    click(By.id(propObjctRepo.getProperty("id_fixed_price_in_button")));
			//explicit_wait_click(By.id(propObjctRepo.getProperty("id_fixed_price_in_button")), 10000);
			System.out.println("Clicked on go to fixed price button.");

			

		} catch (Exception e) {
			addErrorlogs(e, "Error");
		}

	}
		
		
		@Test(priority = 10)
		public void Verify_fin_screen_and_fill_fin_details() throws Exception {

			try {
				
				System.out.println("Landed on FIN info screen");


				Thread.sleep(1000);
				System.out.println("Entering FIN information...");
				scrolltoElement(By.xpath(propObjctRepo.getProperty("xpath_austauschmotor_fin_textbox")));
				click(By.xpath(propObjctRepo.getProperty("xpath_austauschmotor_fin_textbox")));
				clear(By.xpath(propObjctRepo.getProperty("xpath_austauschmotor_fin_textbox")));
				type(By.xpath(propObjctRepo.getProperty("xpath_austauschmotor_fin_textbox")), fin);
				System.out.println("Entered FIN value.");
				Thread.sleep(1000);
				System.out.println("Entering car registration date...");
				scrolltoElement(By.xpath(propObjctRepo.getProperty("xpath_austauschmotor_car_registration_date_textbox_area")));
				click(By.xpath(propObjctRepo.getProperty("xpath_austauschmotor_car_registration_date_textbox_area")));
				clear(By.xpath(propObjctRepo.getProperty("xpath_austauschmotor_car_registration_date_textbox")));
				type(By.xpath(propObjctRepo.getProperty("xpath_austauschmotor_car_registration_date_textbox")),car_registraion_date );
				System.out.println("Entered car registration date");
				
				Thread.sleep(1000);
				System.out.println("Clicking get final price button...");
				scrolltoElement(By.xpath(propObjctRepo.getProperty("xpath_austauschmotor_get_final_fixed_price_button")));
				click(By.xpath(propObjctRepo.getProperty("xpath_austauschmotor_get_final_fixed_price_button")));

				Thread.sleep(5000);

			} catch (Exception e) {
				addErrorlogs(e, "Error on fin screen");
			}

		}
		
		
		
		@Test(priority = 11)
		public void connect_to_database_to_verify() throws Exception {

			try {
				
				System.out.println("Creating DB connection..!");
	             dbConnections dbc = new dbConnections();
	             dbc.rocketConnection();
	             

			} catch (Exception e) {
				addErrorlogs(e, "error on fatching lead data");

			}

				

		}
		
		
		
		@Test(priority = 12)
		public void verify_is_lead_submitted_to_databasd() throws Exception {
			
			
			System.out.println("Verifying,Is lead submitted to database?");
				dbConnections dbc = new dbConnections();
				dbc.getlatestlead(email);
				System.out.println("Fatched latest lead record.");
			


		}
		

		
}
