package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.mysql.jdbc.Statement;

import caroobi.CreateLead;

public class dbConnections extends TestBase {
	
	static Connection con=null;
	
	
	public void chandarayanConnection() throws JSchException, SQLException {

		String jumpserverHost = propConfig.getProperty("chandrayan_sss_host");
		String jumpserverUsername = propConfig.getProperty("chandrayan_ssh_user");

		String databaseHost = propConfig.getProperty("chandrayan_mysqlhost");
		int databasePort = Integer.parseInt(propConfig.getProperty("chandrayan_databasePort"));
		String databaseUsername = propConfig.getProperty("chandrayan_dbUser");
		String databasePassword = propConfig.getProperty("chandrayan_dbPassword");

		JSch jsch = new JSch();

		jsch.addIdentity(propConfig.getProperty("chandrayan_ssh_rsa_path"));

		Session session = jsch.getSession(jumpserverUsername, jumpserverHost);

		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();

		int forwardedPort = session.setPortForwardingL(0, databaseHost, databasePort);

		String url = propConfig.getProperty("chandrayan_jdbc_url") + forwardedPort;
	    con = DriverManager.getConnection(url, databaseUsername, databasePassword);
		System.out.println("DB conenction created !");
		System.out.println("=====================================");

		Statement st = (Statement) con.createStatement();
		st.executeQuery("use caroobi_chandrayan");

}
	
	
	public void rocketConnection() throws JSchException

	{
		
		String jumpserverHost = propConfig.getProperty("rocket_jumpserverHost");
		String jumpserverUsername = propConfig.getProperty("rocket_jumpserverUsername");

		String databaseHost = propConfig.getProperty("rocket_databaseHost");
		int databasePort = Integer.parseInt(propConfig.getProperty("rocket_databasePort"));
		String databaseUsername = propConfig.getProperty("rocket_databaseUsername");
		String databasePassword = propConfig.getProperty("rocket_databasePassword");
		String rocket_jdbc_url = propConfig.getProperty("rocket_rocket_jdbc_url");
		String rocket_ssh_rsa_path = propConfig.getProperty("rocket_ssh_rsa_path");


		try {
			
		
			JSch jsch = new JSch();

			jsch.addIdentity(rocket_ssh_rsa_path);

			Session session = jsch.getSession(jumpserverUsername, jumpserverHost);

			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();

			int forwardedPort = session.setPortForwardingL(0, databaseHost, databasePort);

			String url = rocket_jdbc_url + forwardedPort;
			con = DriverManager.getConnection(url, databaseUsername, databasePassword);
			System.out.println("Database connections created successfully !");
			System.out.println("===========================================");
			
			
			//System.out.println(con.isClosed());
			
			}
	 catch (SQLException ex) {
			System.out.println("An error occurred while connecting rocket MySQL database.");
			ex.printStackTrace();
		}
	}
	
	
	
	public void starkConnection() throws JSchException

	{
		
		String jumpserverHost = propConfig.getProperty("stark_jumpserverHost");
		String jumpserverUsername = propConfig.getProperty("stark_jumpserverUsername");

		String databaseHost = propConfig.getProperty("stark_databaseHost");
		int databasePort = Integer.parseInt(propConfig.getProperty("stark_databasePort"));
		String databaseUsername = propConfig.getProperty("stark_databaseUsername");
		String databasePassword = propConfig.getProperty("stark_databasePassword");
		String rocket_jdbc_url = propConfig.getProperty("stark_rocket_jdbc_url");
		String rocket_ssh_rsa_path = propConfig.getProperty("stark_ssh_rsa_path");


		try {
			
		
			JSch jsch = new JSch();

			jsch.addIdentity(rocket_ssh_rsa_path);

			Session session = jsch.getSession(jumpserverUsername, jumpserverHost);

			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();

			int forwardedPort = session.setPortForwardingL(0, databaseHost, databasePort);

			String url = rocket_jdbc_url + forwardedPort;
			con = DriverManager.getConnection(url, databaseUsername, databasePassword);
			System.out.println("Stark Database connections created successfully !");
			System.out.println("===========================================");
			
			
			//System.out.println(con.isClosed());
			
			}
	 catch (SQLException ex) {
			System.out.println("An error occurred while connecting stark MySQL database.");
			ex.printStackTrace();
		}
	}
	
	

	

	
	public void getlatestlead(String email) throws SQLException, InterruptedException 

	{ 
		
		System.out.println("Executed command to use caroobi_rocket database...");

		Statement st = (Statement) con.createStatement();
		st.executeQuery("use caroobi_rocket");
		

		String query = "select * from users where email = '" + email
				+ "'";
		
		System.out.println("Running query to get user: > ");
		System.out.println("Executing... :  > "+query);

		// executing query to get the user id

		
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			String user_id = rs.getString("id");
			String user_name = rs.getString("last_name");
			String user_email = rs.getString("email");
			String User_mobile = rs.getString("unverified_number");
			
			System.out.println("=====================================");
			System.out.println("Latest created user's ID : " + user_id);
			System.out.println("User Name : " + user_name);
			System.out.println("User Email : " + user_email);
			System.out.println("User Mobile : " + User_mobile);
			System.out.println("=====================================");
			

			// executing query to get latest lead
			Statement st1 = (Statement) con.createStatement();
            Thread.sleep(30000);
			String query2 = "select id, sf_lead_id from submissions where user_id='" + user_id + "'" + "order by id desc limit 1";
			System.out.println("Executing query to get lead: > ");
			ResultSet rs1 = st1.executeQuery(query2);

			while (rs1.next()) {
				String lead_id = rs1.getString("id");
				String sf_id = rs1.getString("sf_lead_id");
				
				System.out.println("Latest created Lead Id : " + lead_id);
				System.out.println("Latest created SF Lead ID : " + sf_id);
				
				System.out.println("=====================================");
				
			CreateLead.globalLeadId=Integer.parseInt(lead_id);
			CreateLead.globalSFLeadId=(String) sf_id;
			
			

			}
		}
		
		
	}
	
	
	public void useDatabase(String database) throws SQLException, InterruptedException 

	{ 
		
		String bdname = database;
		
		System.out.println("Using Database :  " + bdname);

		Statement st = (Statement) con.createStatement();
		st.executeQuery("use "+ bdname);
		
	}
	
	
}
