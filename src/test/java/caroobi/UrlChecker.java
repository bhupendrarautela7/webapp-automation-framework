package caroobi;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.net.ssl.HttpsURLConnection;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class UrlChecker {

	public static void main(String[] args) throws IOException {
		FileInputStream newFile1 = new FileInputStream("./object_repo/all_endpoint_urls.xls");
		HSSFWorkbook workbook = new HSSFWorkbook(newFile1);
		HSSFSheet sheet = workbook.getSheetAt(0);
		System.out.println("==================================================");
		System.out.println(" -----Processing URL's to health checkup-----");
		System.out.println("==================================================");
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			String URLs = "https://chandrayan.oleo.io" + sheet.getRow(i).getCell((short) 0).getStringCellValue();
			try {
		
				URL url = new URL(URLs);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
				con.setRequestProperty("Connection", "close");
				con.connect();
				if (con.getResponseCode() == 200) {
					System.out.println(i + ":" + URLs + "  " + con.getResponseCode() + "  [OK]");
					System.out.println("------------------------------------------------------------");
 
				}
				if (con.getResponseCode() != 200)
					System.err.println(i + ":" + URLs + "  " + con.getResponseCode() + " [Not OK]");
				System.out.println("------------------------------------------------------------");

				
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (UnknownHostException unknownHostException) {
				System.err.println(i + ":"+URLs + " : This Url is not correct: " + unknownHostException);
				System.out.println("------------------------------------------------------------");

			} catch (NullPointerException ex) {
				System.err.println(i + ":"+ URLs + " : This Url is not correct: " + ex);
				System.out.println("------------------------------------------------------------");

			}
		}
	}
}
