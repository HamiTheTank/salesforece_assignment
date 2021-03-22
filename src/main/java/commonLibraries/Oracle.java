package commonLibraries;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.ibatis.jdbc.ScriptRunner;


public class Oracle {
	
	
	private static Connection conn = null;
	private static Statement stmt  = null;
	
	static final String JDBC_DRIVER    = "oracle.jdbc.driver.OracleDriver";
	static final String DB_IP          = "ip address";
	static final String DB_URL_WINDOWS = "url";
	static final String DB_URL_LINUX   = "url";
	static final String DB_URL_LINUX2  = "url";
	static final String USER           = "username";
	static final String PASS           = "password";
	static final String SQL            = "query";
	 
	
		
	
	public static void connectDB() {
		
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			
			if (TestEnvironment.os.contains("win")) {
				System.out.println("DB URL: " + DB_URL_WINDOWS);
				conn = DriverManager.getConnection(DB_URL_WINDOWS, USER ,PASS);		
				conn.setAutoCommit(false);
			} else {
				System.out.println("DB URL: " + DB_URL_LINUX);
				conn = DriverManager.getConnection(DB_URL_LINUX, USER ,PASS);
				conn.setAutoCommit(false);
			}
			
			System.out.println("Database connected successfully");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		
	}
	
	
	public static void closeDBConnection() {
		
		try {
			if (conn != null)
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	public static ResultSet runQuery(String sql) {
		
		ResultSet rs = null;
		
		try {
			System.out.println("Creating statement...");
		    stmt = conn.createStatement();
		    System.out.println("Executing SQL...");
			rs = stmt.executeQuery(sql);
			System.out.println("Done");
		} catch (SQLException se) {
			se.printStackTrace();
		}
		
		return rs;
		
	}	
	
	
	public static void runScript(String sqlPath) {
		
		try {
		System.out.println("Running script: " + sqlPath);
		ScriptRunner sr = new ScriptRunner(conn);
		Reader reader = new BufferedReader(new FileReader(sqlPath));
		sr.runScript(reader);
		System.out.println("SUCCESS");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}	


}
