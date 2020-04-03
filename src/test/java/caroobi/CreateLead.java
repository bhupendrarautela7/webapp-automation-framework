package caroobi;

import org.testng.annotations.Test;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.mysql.jdbc.Statement;
//import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

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
//import common.J2;
import common.LaunchBrowser;
import common.TestBase;
import common.dbConnections;
import junit.framework.Assert;

public class CreateLead extends LaunchBrowser {
	
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
		
		System.out.println("===========================================");
		System.out.println("Preparing test data for Lead Creation -  Horbert.");

		try {

		/*	String range_value = "B2:J";
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
			
			String range_value = "B3:J3" ;
			
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
			
			
			

		} catch (Exception e) {
			addErrorlogs(e, "Error on getting lead data");
		}

	}

	@Test(priority = 1)
	public void open_home_page_to_create_lead() throws Exception {

		try {
			System.out.println("==================================");
			openURL(propConfig.getProperty("rocket_home_page"));
			System.out.println("=====================================");
			System.out.println("Opened Home Page.");

		} catch (Exception e) {
			addErrorlogs(e, "Error on opening page.");
		}

	}

	@Test(priority = 2)
	public void accept_cookies_dialog() throws Exception {

		try {
			Thread.sleep(1000);
			//click(By.id(propObjctRepo.getProperty("id_cookie_cta")));
			explicit_wait_click(By.id(propObjctRepo.getProperty("id_cookie_cta")), 10000);
			System.out.println("Accepted Cookies on home page.");

		} catch (Exception e) {
			addErrorlogs(e, "Not found accpet cookie dialog.");
		}

	}

	@Test(priority = 3)
	public void check_select_service_dropdown_on_homepage() throws Exception {

		try {

			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_Services_dropdown")));
			

		} catch (Exception e) {
			addErrorlogs(e, "error on finding service selection on homepage.");
		}

	}

	@Test(priority = 4)
	public void choose_service_dropdown_on_homepage() throws Exception {

		try {

			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_Services_dropdown_value")));
			System.out.println("Selected Service on Home Page.");

		} catch (Exception e) {
			addErrorlogs(e, "error on service selection from dropdown.");
		}

	}

	@Test(priority = 5)
	public void select_car_on_homepage() throws Exception {

		try {

			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_Auto_dropdown2")));
			System.out.println("Selected car on Home Page.");

		} catch (Exception e) {
			addErrorlogs(e, "error on selecting automobile brand from dropdown.");
		}

	}

	@Test(priority = 6)
	public void click_on_pin_textbox() throws Exception {

		try {

			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_pin_input")));

		} catch (Exception e) {
			addErrorlogs(e, "Error on finding pin input field.");
		}

	}

	@Test(priority = 7)
	public void clear_pin_textbox() throws Exception {

		try {

			Thread.sleep(1000);
			clear(By.xpath(propObjctRepo.getProperty("xpath_pin_input")));

		} catch (Exception e) {
			addErrorlogs(e, "Error on clearing pin input box.");
		}

	}

	@Test(priority = 8)
	public void enter_value_in_pin_textbox() throws Exception {

		try {

			
			type(By.xpath(propObjctRepo.getProperty("xpath_pin_input")), "1");
			type(By.xpath(propObjctRepo.getProperty("xpath_pin_input")), "0");
			type(By.xpath(propObjctRepo.getProperty("xpath_pin_input")), "1");
			type(By.xpath(propObjctRepo.getProperty("xpath_pin_input")), "1");
			type(By.xpath(propObjctRepo.getProperty("xpath_pin_input")), "5");
			System.out.println("Entered PIN on Home Page.");

		} catch (Exception e) {
			addErrorlogs(e, "Error on entering pin.");
		}

	}

	@Test(priority = 9)
	public void click_homepage_cta() throws Exception {

		try {

			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_cta")));
			Thread.sleep(1000);
			System.out.println("Clicked on Homepage CTA button");

		} catch (Exception e) {
			addErrorlogs(e, "Error on clicking CTA button on home page");
		}

	}

	@Test(priority = 10)
	public void funnal_choose_model_family() throws Exception {

		try {

			Thread.sleep(6000);
			System.out.println("waiting to be clikable.");
			click(By.xpath(propObjctRepo.getProperty("xpath_Modellfamilie")));
			//explicit_wait_click(By.xpath(propObjctRepo.getProperty("xpath_Modellfamilie"),6000));
			//explicit_wait_click(By.xpath(propObjctRepo.getProperty("xpath_Modellfamilie")), 10000);
			System.out.println("Clicked finally");

			
			

		} catch (Exception e) {
			addErrorlogs(e, "Error on selecting model family");
		}

	}

	@Test(priority = 11)
	public void funnal_choose_karosserieform() throws Exception {

		try {
			Thread.sleep(3000);
			click(By.xpath(propObjctRepo.getProperty("xpath_Karosserieform")));

		} catch (Exception e) {
			addErrorlogs(e, "Error");
		}

	}

	@Test(priority = 12)
	public void funnal_choose_baujahr() throws Exception {

		try {
			Thread.sleep(3000);
			click(By.xpath(propObjctRepo.getProperty("xpath_Baujahr")));

		} catch (Exception e) {
			addErrorlogs(e, "Error");
		}

	}

	@Test(priority = 13)
	public void funnal_choose_model() throws Exception {

		try {
			Thread.sleep(3000);
			click(By.xpath(propObjctRepo.getProperty("xpath_Modell")));

		} catch (Exception e) {
			addErrorlogs(e, "Error on clicking CTA button on home page");
		}

	}

	@Test(priority = 14)
	public void funnal_choose_services() throws Exception {

		try {
			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_service1")));
			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_service2")));
			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_service3")));

		} catch (Exception e) {
			addErrorlogs(e, "Error on primary secondry services");
		}

	}

	@Test(priority = 15)
	public void funnal_go_next_from_services() throws Exception {

		try {
			Thread.sleep(1000);
			
			scrolltoElement(By.xpath(propObjctRepo.getProperty("xpath_next")));
			click(By.xpath(propObjctRepo.getProperty("xpath_next")));

		} catch (Exception e) {
			addErrorlogs(e, "Error on next click");
		}

	}

	@Test(priority = 16)
	public void save_offer() throws Exception {

		try {
			Thread.sleep(2000);
			click(By.xpath(propObjctRepo.getProperty("xpath_saveoffer_button")));

		} catch (Exception e) {
			addErrorlogs(e, "Error on next clicking save offer");
		}

	}

	@Test(priority = 17)
	public void Save_offer_email_input() throws Exception {

		try {
			Thread.sleep(2000);
			type(By.id(propObjctRepo.getProperty("id_OfferSave")), email);

		} catch (Exception e) {
			addErrorlogs(e, "Error on entering email to save offer send");
		}

	}

	@Test(priority = 18)
	public void save_offer_email_button_click() throws Exception {

		try {
			Thread.sleep(2000);
			click(By.xpath(propObjctRepo.getProperty("xpath_send_offer_email")));

		} catch (Exception e) {
			addErrorlogs(e, "Error on clicking send offer save offer");
		}

	}

	@Test(priority = 19)
	public void close_send_offer_success_popup() throws Exception {

		try {
			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_close_sendoffer_success_popup")));

		} catch (Exception e) {
			addErrorlogs(e, "Error on closing save offer conformation pop up");
		}

	}

	@Test(priority = 20)
	public void go_next_from_offer_price_details() throws Exception {

		try {
			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_next_button_on_offer_page")));

		} catch (Exception e) {
			addErrorlogs(e, "Error on clicking next");
		}
	}

	@Test(priority = 21)
	public void click_on_direct_navigation_button() throws Exception {

		try {
			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_direct_navigation")));

		} catch (Exception e) {
			addErrorlogs(e, "Error on clikcing direct navigation button");
		}

	}

	@Test(priority = 22)
	public void click_on_direct_navigation_Close() throws Exception {

		try {
			Thread.sleep(2000);
			click(By.xpath(propObjctRepo.getProperty("xpath_direct_navigation_close")));

		} catch (Exception e) {
			addErrorlogs(e, "Error on clikcing direct navigation close button");
		}

	}

	@Test(priority = 23)
	public void more_mechanic_list_open_close() throws Exception {

		try {
			Thread.sleep(1000);
			scrolltoElement(By.xpath(propObjctRepo.getProperty("xpath_mechanic_list_open")));

			click(By.xpath(propObjctRepo.getProperty("xpath_mechanic_list_open")));
			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_mechanic_list_close")));

		} catch (Exception e) {
			addErrorlogs(e, "Error on opening closing more mechanic list");
		}

	}

	@Test(priority = 24)
	public void click_and_open_price_details_popup() throws Exception {

		try {
			Thread.sleep(2000);
			click(By.xpath(propObjctRepo.getProperty("xpath_open_price_details_popup")));

		} catch (Exception e) {
			addErrorlogs(e, "Error on opening price details popup.");
		}

	}

	@Test(priority = 25)
	public void click_and_close_price_details_popup() throws Exception {

		try {
			Thread.sleep(6000);
			click(By.xpath(propObjctRepo.getProperty("xpath_close_price_details_popup")));

		} catch (Exception e) {
			addErrorlogs(e, "Error closing price details popup.");
		}

	}

	@Test(priority = 26)
	public void Click_next_button_from_mechanic_screen() throws Exception {

		try {
			Thread.sleep(1000);
			
			scrolltoElement(By.xpath(propObjctRepo.getProperty("xpath_next_button_from_mechanic_screen")));

			click(By.xpath(propObjctRepo.getProperty("xpath_next_button_from_mechanic_screen")));
			
			//div[@class=class="foc-v5-cta offer-section-cta ibm"]

		} catch (Exception e) {
			addErrorlogs(e, "Error on navigating next from mechanic screen");
		}

	}

	@Test(priority = 27)
	public void click_on_skip_date_selection() throws Exception {

		try {
			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_skip_date_selection")));

		} catch (Exception e) {
			addErrorlogs(e, "Error on skipping from job start date selection");
		}

	}

	@Test(priority = 28)
	public void fill_and_submit_lead_name_details() throws Exception {

		try {
			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_lead_name_textbox")));
			clear(By.xpath(propObjctRepo.getProperty("xpath_lead_name_textbox")));
			type(By.xpath(propObjctRepo.getProperty("xpath_lead_name_textbox")), name);
			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_submit_name_value_button")));

		} catch (Exception e) {
			addErrorlogs(e, "Error filling and submitting lead name");
		}

	}

	@Test(priority = 29)
	public void fill_and_submit_lead_email_details() throws Exception {

		try {
			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_lead_email_textbox")));
			clear(By.xpath(propObjctRepo.getProperty("xpath_lead_email_textbox")));
			type(By.xpath(propObjctRepo.getProperty("xpath_lead_email_textbox")), email);
			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_submit_email_value_button")));

		} catch (Exception e) {
			addErrorlogs(e, "Error filling and submitting lead email");
		}

	}

	@Test(priority = 30)
	public void fill_and_submit_lead_mobile_number_details() throws Exception {

		try {
			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_lead_mobile_textbox")));
			clear(By.xpath(propObjctRepo.getProperty("xpath_lead_mobile_textbox")));
			type(By.xpath(propObjctRepo.getProperty("xpath_lead_mobile_textbox")), phone);
			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_submit_mobile_value_button")));

		} catch (Exception e) {
			addErrorlogs(e, "Error filling and submitting phone no");

		}

	}

	@Test(priority = 31)
	public void checkin_page_accordian_open() throws Exception {

		try {

			Thread.sleep(2000);
			String url=getCurrentUrl();
			refreshpage();
			openURL(url);
			Thread.sleep(2000);
			click(By.xpath(propObjctRepo.getProperty("xpath_checkin_page_accordian_open")));

		} catch (Exception e) {
			addErrorlogs(e, "error on checkin page accordian open");

		}

	}

	@Test(priority = 32)
	public void checkin_page_accordian_close() throws Exception {

		try {
			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_checkin_page_accordion_open")));

		} catch (Exception e) {
			addErrorlogs(e, "error on checkin page accordion close");

		}

	}

	@Test(priority = 33)
	public void checkin_page_scroll_to_info() throws Exception {

		try {
			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_scroll_to_info")));

		} catch (Exception e) {
			addErrorlogs(e, "error on checkin page accordion close");

		}

	}

	@Test(priority = 34)
	public void checkin_page_fill_fin_details() throws Exception {

		try {
			Thread.sleep(1000);
			click(By.xpath(propObjctRepo.getProperty("xpath_checkin_fin_textbox")));
			clear(By.xpath(propObjctRepo.getProperty("xpath_checkin_fin_textbox")));
			type(By.xpath(propObjctRepo.getProperty("xpath_checkin_fin_textbox")), fin);

			click(By.xpath(propObjctRepo.getProperty("xpath_checkin_fin_submit_button")));

		} catch (Exception e) {
			addErrorlogs(e, "error on checkin fin updation.");

		}

	}

	@Test(priority = 35)
	public void checkin_page_fill_address_details() throws Exception {

		try {
			Thread.sleep(6000);

			click(By.xpath(propObjctRepo.getProperty("xpath_checkin_address2_textbox_click")));
			clear(By.xpath(propObjctRepo.getProperty("xpath_checkin_address2_textbox")));
			type(By.xpath(propObjctRepo.getProperty("xpath_checkin_address2_textbox")), address);

			click(By.xpath(propObjctRepo.getProperty("xpath_checkin_address1_textbox_click")));
			clear(By.xpath(propObjctRepo.getProperty("xpath_checkin_address1_textbox")));
			type(By.xpath(propObjctRepo.getProperty("xpath_checkin_address1_textbox")), address);

			click(By.xpath(propObjctRepo.getProperty("xpath_checkin_address_submit")));

		} catch (Exception e) {
			addErrorlogs(e, "error on checkin address updation.");

		}

	}

	@Test(priority = 36)
	public void checkin_page_fill_comments() throws Exception {

		try {
			Thread.sleep(6000);
			click(By.id(propObjctRepo.getProperty("id_checkin_commensts")));
			clear(By.id(propObjctRepo.getProperty("id_checkin_commensts")));
			type(By.id(propObjctRepo.getProperty("id_checkin_commensts")), propConfig.getProperty("lead_message"));

			click(By.xpath(propObjctRepo.getProperty("xpath_checkin_comments_submit_button")));

		} catch (Exception e) {
			addErrorlogs(e, "error on additional messaage submit.");

		}

	}
	
	@Test(priority = 37)
	public void connect_rocket_database() throws Exception {

		try {
			System.out.println("=======================================");
			System.out.println("creating rocket DB connection..!");
             dbConnections dbc = new dbConnections();
             dbc.rocketConnection();
             

		} catch (Exception e) {
			addErrorlogs(e, "error on fatching lead data");

		}

	}

	
	
	@Test(priority = 37)
	public void get_lead() throws Exception {
		
			dbConnections dbc = new dbConnections();
			dbc.getlatestlead(email);
		


	}
}