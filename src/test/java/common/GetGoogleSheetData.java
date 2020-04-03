package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.httpclient.util.HttpURLConnection;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

public class GetGoogleSheetData {


	public static ArrayList<String> getsheetdata(String range_value) throws IOException {
		
		String range =  range_value;

		String url = "https://sheets.googleapis.com/v4/spreadsheets/1BH-e3-XSZ9LjsQqELjZLpZbnB4DmIhrPy2VDAZsP9KM/values/lead!"+range +"?key=AIzaSyDJRy73ru1BSLFCb9nknUF8SlZd4LxwJAc";

		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");
		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_8; en-US) AppleWebKit/532.5 (KHTML, like Gecko) Chrome/4.0.249.0 Safari/532.5");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		System.out.println(response.toString());
		//Read JSON response and print
		JSONObject myResponse = new JSONObject(response.toString());

		/*System.out.println("ipAddress- "+myResponse.getString("range"));
		System.out.println("MD- "+myResponse.getString("majorDimension"));
		System.out.println("Values- "+myResponse.getJSONArray("values"));*/

		ArrayList<String> leaddate = new ArrayList<String>(); 
		
		JSONArray jArray = myResponse.getJSONArray("values");
	
		if (jArray != null) { 
		
			for (int i=0;i<jArray.length();i++){ 
				
			
				JSONArray jArray2 = (jArray.getJSONArray(i)); 
				
				for (int j=0;j<jArray2.length();j++){ 
					
					leaddate.add(jArray2.getString(j));

				}

			} 
		} 

		//System.out.println("Lead Data : "  + leaddate);
		return leaddate;

	}




}
