package common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import io.github.bonigarcia.wdm.ChromeDriverManager;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.mysql.jdbc.Statement;

import junit.framework.Assert;

public class TestBase {

	public static WebDriver dvr;
	public static Properties propConfig, propObjctRepo;
	public static Logger log;



	private static final String APPLICATION_NAME = "Caroobi Automation Data Sheet";

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
			".credentials/sheets.googleapis.com-java-quickstart");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	@SuppressWarnings("deprecation")
	public void initBrowser(String browser) {


		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 

		System.out.println("==============================================" );
		System.out.println("Test started at :  "+ dtf.format(now));
		System.out.println("==============================================" );


		String OS = TestBase.OSDetector();

		if (browser.equalsIgnoreCase("chrome") && OS == "Linux") {

			System.out.println("OS Detected : Linux , Browser Launched : Chrome" );
			System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
			ChromeOptions options = new ChromeOptions();
			/*options.addArguments("start-maximized"); // open Browser in maximized mode
			options.addArguments("disable-infobars"); // disabling infobars
			options.addArguments("--disable-extensions"); // disabling extensions
			options.addArguments("--disable-gpu"); // applicable to windows os only
			options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems */
			//options.addArguments("--no-sandbox"); // Bypass OS security model
			options.addArguments("--headless");
			dvr = new ChromeDriver(options);
			dvr.manage().window().fullscreen();
			//dvr = new ChromeDriver();


		}

		else if (browser.equalsIgnoreCase("chrome") && OS == "Mac") {

			System.out.println("==============================================" );
			System.out.println("OS Detected : MAC , Browser Launched : Chrome" );
			System.out.println("==============================================" );

			System.setProperty("Webdriver.chrome.driver", "./lib/chromedriver");

			Boolean headlesschrome = false;

			if (headlesschrome==true) {

				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("--headless");
				chromeOptions.addArguments("--start-maximized");
				chromeOptions.addArguments("--window-size=1200,800");
				dvr = new ChromeDriver(chromeOptions);
				//dvr.manage().window().fullscreen();
				
			}else if (headlesschrome==false)

			{
				dvr = new ChromeDriver();
				dvr.manage().window().fullscreen();
			}

		}

		else if (browser.equalsIgnoreCase("firefox") && OS == "Windows") {

			System.out.println("OS Detected : Windows , Browser Launched : Firefox" );
			System.setProperty("webdriver.gecko.driver", "./lib/geckodriver.exe");
			FirefoxProfile profile = new FirefoxProfile();
			profile.setAcceptUntrustedCertificates(true);
			profile.setAssumeUntrustedCertificateIssuer(false);
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability(FirefoxDriver.PROFILE, profile);
			capabilities.setCapability("marionette", true);
			dvr = new FirefoxDriver(capabilities);
		} else {
			throw new IllegalArgumentException("Error with launching browser driver.....");
		}

		// dvr.manage().window().maximize();
		dvr.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	public static String OSDetector() {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win")) {
			return "Windows";
		} else if (os.contains("nux") || os.contains("nix")) {
			return "Linux";
		} else if (os.contains("mac")) {
			return "Mac";
		} else if (os.contains("sunos")) {
			return "Solaris";
		} else {
			return "Other";
		}

	}

	public void loadPropertiesFile() throws FileNotFoundException, IOException {

		propConfig = new Properties();
		propConfig.load(new FileInputStream("./object_repo/config.properties"));
		propObjctRepo = new Properties();
		propObjctRepo.load(new FileInputStream("./object_repo/objects.properties"));
	}


	/**
	 * @throws InterruptedException
	 */
	public void loginGmail() throws InterruptedException {

		System.out.println("Authorizing google credentials to login into starfleet application... ");

		String gmail_url = "https://accounts.google.com/signin/v2/identifier?continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&service=mail&sacu=1&rip=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin";
		String user_name = "bhupendra@caroobi.com";
		String password = "9411758462";


		//	dvr.get(gmail_url);

		System.out.println("Attempting  : Gmail login");

		try {

			/*dvr.findElement(By.id("identifierId")).click();
		dvr.findElement(By.id("identifierId")).clear();
		dvr.findElement(By.id("identifierId")).sendKeys(user_name);
		System.out.println("Entered Gmail User Name.");


		Thread.sleep(2000);
		dvr.findElement(By.xpath("//div/span/span[@class=\"RveJvd snByac\"]")).click();
		Thread.sleep(3000);
		dvr.findElement(By.xpath("//input[@type='password']")).click();
	    dvr.findElement(By.xpath("//input[@type='password']")).clear();
     	dvr.findElement(By.xpath("//input[@type='password']")).sendKeys(password);
     	System.out.println("Entered Gmail User Name.");
     	Thread.sleep(2000);
		dvr.findElement(By.xpath("//span/span[@class='RveJvd snByac']")).click();
		Thread.sleep(5000);*/


			/*	dvr.get("https://accounts.google.com/signin/v2/identifier?service=mail&passive=true&rm=false&continue=https%3A%2F%2Fmail.google.com%2Fmail%2Fh%2F141icwbpdm6lq%2F&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin");
		    dvr.findElement(By.id("identifierId")).click();
		    dvr.findElement(By.id("identifierId")).clear();
		    dvr.findElement(By.id("identifierId")).sendKeys("bhupendra@caroobi.com");
		    dvr.findElement(By.xpath("//*[@id=\"identifierNext\"]/div[2]")).click();
		    Thread.sleep(1000);
		    dvr.findElement(By.xpath("//*[@id='password']/div[1]/div/div[1]/input")).clear();
		    Thread.sleep(1000);
		    dvr.findElement(By.xpath("//*[@id='password']/div[1]/div/div[1]/input")).sendKeys("9411758462");
		    Thread.sleep(1000);
		    dvr.findElement(By.xpath("//*[@id=\"passwordNext\"]/span/span")).click();
		    Thread.sleep(3000);
		  //*[@id="password"]/div[1]/div/div[1]/input 


			/* dvr.get("http://www.gmail.com");
			// **** checking if url is of new then use if code otherwise else code.
			if(dvr.getCurrentUrl().contains("https://accounts.google.com/signin/v2/identifier?")){
				dvr.findElement(By.id("identifierId")).sendKeys(user_name);
				dvr.findElement(By.id("identifierNext")).click();

			        WebDriverWait wait = new WebDriverWait(dvr, 20);
			        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password"))); 
			        dvr.findElement(By.name("password")).sendKeys(password);
			        dvr.findElement(By.id("passwordNext")).click();
			    }
			    else{
			    	dvr.findElement(By.name("Email")).sendKeys(user_name);
			    	dvr.findElement(By.id("next")).click();
			        WebDriverWait wait = new WebDriverWait(dvr, 20);
			        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Passwd")));
			        dvr.findElement(By.name("Passwd")).sendKeys(password);
			        dvr.findElement(By.name("signIn")).click();
			    }

			 */

			By usernameInput = By.xpath("//input[@type='email']");
			By passwordInput = By.xpath("//input[@type='password']");
			By nextButton = By.xpath("//span[contains(text(),'Next')]");


			try {
				dvr.get("https://accounts.google.com/signin/v2/identifier?service=mail&passive=true&rm=false&continue=https%3A%2F%2Fmail.google.com%2Fmail%2Fh%2F141icwbpdm6lq%2F&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin");
				dvr.findElement(usernameInput).sendKeys("bhupendra@caroobi.com");
				dvr.findElement(nextButton).click();
				Thread.sleep(2000);

				dvr.findElement(passwordInput).sendKeys("9411758462");
				dvr.findElement(nextButton).click();
				Thread.sleep(2000);

			} catch (Exception e) {

			}


			System.out.println("Successfully logged in to google.");

			System.out.println("====================================");


		}

		catch (Exception e)

		{
			System.out.println("Google is not allowing to login from here...!!! Google Login failed");
			System.out.println(e);



		}

	}


	public int randomNumber() 
	{ 

		Random rand = new Random();
		int rand_int = rand.nextInt(100000);
		System.out.println("Random Integers: "+rand_int);
		return rand_int;

	}

	public void openURL(String url) {

		dvr.get(url);
		System.out.println("Opening : " + url);

	}

	protected void addlogs(String Message) throws IOException {
		log.info(Message);

	}

	protected void addErrorlogs(Exception e, String errormsg) throws IOException {

	}

	public void mouseHover(By by) {
		Actions act = new Actions(dvr);
		act.moveToElement(dvr.findElement(by)).build().perform();

	}

	public void explicit_wait(By by, long time) {
		WebDriverWait wd = new WebDriverWait(dvr, time);
		// wd.until(ExpectedElementConditions.isVisible())

		wd.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public void explicit_wait_presence(By by, long time) {
		WebDriverWait wd = new WebDriverWait(dvr, time);
		// wd.until(ExpectedElementConditions.isVisible())

		wd.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	public void explicit_wait_click(By by, long time) {
		WebDriverWait wd = new WebDriverWait(dvr, time);

		wd.until(ExpectedConditions.elementToBeClickable(by));
	}

	public void scrolltoTop() {
		((JavascriptExecutor) dvr).executeScript("window.scroll(0,0);");
	}

	public void handleAlert() {

		// System.out.println("Test inside alert");
		WebDriverWait wait = new WebDriverWait(dvr, 5);

		wait.until(ExpectedConditions.alertIsPresent());
		System.out.println("Test inside alert");
		Alert alert = dvr.switchTo().alert();
		alert.accept();

	}

	public void type(By element, String value) {

		Assert.assertTrue(dvr.findElements(element).size()>0);
		dvr.findElement(element).sendKeys(value);


	}


	public void enter(By element) {

		dvr.findElement(element).sendKeys(Keys.ENTER);



	}


	public void clickjs(By element) {

		((JavascriptExecutor) dvr).executeScript("arguments[0].click();", element);

	}




	public void typejs(By element, String value) {

		((JavascriptExecutor) dvr).executeScript("arguments[0].value='" + value + "';", dvr.findElement(element));

	}

	public void clear(By element) {

		dvr.findElement(element).clear();
		Assert.assertTrue(dvr.findElements(element).size()>0);

	}



	public String getCurrentUrl() {

		String currenturl= dvr.getCurrentUrl();
		return currenturl;

	}

	public void refreshpage() {

		dvr.navigate().refresh();

	}


	public boolean isSFleadPresentonStarfleet(By element, String sf_id) {

		List<WebElement> list = dvr.findElements(By.xpath("//*[contains(text(),'" + sf_id + "')]"));

		if (list.size() > 0)

		{ return true;}

		else { return false;}

	}

	public String getText(By element, String sf_id) {

		String text=(new WebDriverWait(dvr, 20).until(ExpectedConditions.visibilityOfElementLocated(element)).getAttribute("innerHTML"));

		return text;


	}



	private boolean Assert(String string, boolean b) {
		// TODO Auto-generated method stub
		return false;
	}

	public void click(By element) {

		Assert.assertTrue(dvr.findElements(element).size()>0);
		dvr.findElement(element).click();

	}

	public void selectValueDropdown(By element, String text) {

		Select sel = new Select(dvr.findElement(element));
		sel.selectByVisibleText(text);

	}

	public void verifyElement(By element) throws Exception {
		try {
			Assert.assertTrue(dvr.findElement(element).isDisplayed());
			addlogs("Element " + element + " found on page");
			System.out.println("Element " + element + " found on page");
		} catch (AssertionError e) {
			System.out.println("Element " + element + " not found on page");
			throw new Exception("element " + element + " not found on page");
		} catch (Exception e) {
			System.out.println("Element " + element + " not found on page");
			throw new Exception("element " + element + " not found on page");
		}

	}

	public boolean isElementDisplayed(By element) throws Exception {
		try {
			Assert.assertTrue(dvr.findElement(element).isDisplayed());
			addlogs("Element " + element + " found on page");
			System.out.println("Element " + element + " found on page");
			return true;
		} catch (AssertionError e) {
			System.out.println("Element " + element + " not found on page");
			return false;
		} catch (Exception e) {
			System.out.println("Element " + element + " not found on page");
			return false;
		}

	}

	public void verifyElementPresence(By element) throws Exception {
		try {
			Assert.assertTrue(dvr.findElement(element).isEnabled());
			addlogs("Element " + element + " found on page");
			System.out.println("Element " + element + " found on page");
		} catch (AssertionError e) {
			System.out.println("Element " + element + " not found on page");
			throw new Exception("element " + element + " not found on page");
		} catch (Exception e) {
			System.out.println("Element " + element + " not found on page");
			throw new Exception("element " + element + " not found on page");
		}

	}

	public String getAttribute(By element, String attribute) {
		try {
			String attributeval = dvr.findElement(element).getAttribute(attribute);
			return attributeval;
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
			return null;
		}
	}

	protected void scrolltoElement(By element) {
		((JavascriptExecutor) dvr).executeScript("arguments[0].scrollIntoView();", dvr.findElement(element));
	}

	protected List<WebElement> getMultipleWebElement(By element) {
		List<WebElement> elements = dvr.findElements(element);
		return elements;
	}

	public void getLatestLead(String host, String dbUser, String dbPassword, String database, String query)

	{
		Connection dbConnection = null;

		try {

			System.out.println("inside connections");
			String url = host;
			Properties info = new Properties();
			info.put("user", dbUser);
			info.put("password", dbPassword);

			dbConnection = DriverManager.getConnection(url, info);

			if (dbConnection != null) {
				System.out.println("Successfully connected to database.");
				Statement st = (Statement) dbConnection.createStatement();
				st.executeQuery("use " + database);
				System.out.println("Executing query : > " + query);
				ResultSet rs = st.executeQuery(query);

				while (rs.next()) {
					String id = rs.getString("id");
					System.out.println(id);

				}

			}

		} catch (SQLException ex) {
			System.out.println("An error occurred while connecting MySQL databse.");
			ex.printStackTrace();
		}
	}



	private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY);

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	public static Credential authorize() throws IOException {
		// Load client secrets.
		InputStream in = TestBase.class.getResourceAsStream("/client_secret.json");
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		return credential;
	}



	public static Sheets getSheetsService() throws IOException {
		Credential credential = authorize();
		return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME)
				.build();
	}

	public static String[] getleaddata(String sheetid, String range_value) throws IOException {

		String name = null;
		String email = null;
		String phone = null;
		String fin = null;
		String address = null;
		String car_registraion = null;
		String question = null;
		String pin = null;
		String car_registraion_date = null;


		Sheets service = getSheetsService();

		String spreadsheetId = sheetid;
		String range = range_value;
		ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
		List<List<Object>> values = response.getValues();
		if (values == null || values.size() == 0) {
			System.out.println("No data found.");
		} else {

			for (List<?> row : values) {

				name = row.get(0).toString();
				email = row.get(1).toString();
				phone = row.get(2).toString();
				fin = row.get(3).toString();
				address = row.get(4).toString();
				car_registraion = row.get(5).toString();
				question = row.get(6).toString();
				pin = row.get(7).toString();
				car_registraion_date = row.get(8).toString();



				String[] ret = new String[9];
				ret[0]=name;
				ret[1]=email;
				ret[2]=phone;
				ret[3]=fin;
				ret[4]=address;
				ret[5]=car_registraion;
				ret[6]=question;
				ret[7]=pin;
				ret[8]= car_registraion_date;


				return ret;


			}



		}
		return null;


	}

}
