package common;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
	private Connection connection;
	private Statement statement;
	private PreparedStatement preparedStatement;
	
	public DB(){
		// load the sqlite-JDBC driver using the current class loader
	    try {
	    	Class.forName("org.sqlite.JDBC");
	    	// create a database connection
	    	connection = DriverManager.getConnection("jdbc:sqlite:desktopguide.db"); //so it does create a table but throws an error
	    	System.out.println("connection is established");
	    	this.statement = connection.createStatement();
	    	this.statement.setQueryTimeout(30);  // set timeout to 30 sec.
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int createGuide(String title, String author) {
		author = "pramod";
		int guideid =0;
		try {
			 // SQL statement for creating a new table
		
			String sql = "CREATE TABLE IF NOT EXISTS guideinfo(guideid INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, author TEXT)";
			//statement.executeQuery(sql); //this is not returning a statement throws errror i think this throws error because createTable does not return result
			statement.execute(sql); //this is not returning a statement throws errror i think this throws error because createTable does not return result

			sql = "INSERT INTO guideinfo(title,author) values (?,?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, title);
			preparedStatement.setString(2, author);
			preparedStatement.executeUpdate();
			String findGuideidQuery = "select * from guideinfo where title='"+ title+ "'"; // find the guide where title is whats passed in
			ResultSet  rs = statement.executeQuery(findGuideidQuery); 
			//this will check all 
			guideid = rs.getInt("guideid"); //only works for same title
			System.out.println("the guide id is" + guideid);
			//need to somehow get the guide id in guideinfo 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return guideid;
	}
	/*
	 * how to query   the guide info. 
	 * Querying with title now. Will need to change
	 * 
	 */
	public void queryGuideInfo(String title) {
		 /**
	     * select all rows in the warehouses table
	  	*/
	        String sql = "SELECT guideid, stepnumber, stepaction FROM guideinfo";
	        
	        try (   Statement stmt  = connection.createStatement();
	                ResultSet rs    = stmt.executeQuery(sql)){
		            // loop through the result set
		            while (rs.next()) {
		                System.out.println(rs.getInt("guideid") +  "\t" + 
		                                   rs.getInt("stepnumber") + "\t" +
		                                   rs.getString("stepaction"));
		            }
	         } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	}
	public void createStep(int guideid, int stepCount, String action) {
		try {
			String sql = "CREATE TABLE IF NOT EXISTS stepinfo (stepid INTEGER PRIMARY KEY AUTOINCREMENT, guideid INTEGER, stepnumber INTEGER, stepaction TEXT)";
			statement.execute(sql);
			
			sql = "INSERT INTO stepinfo(guideid,stepnumber,stepaction) values (?,?,?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, guideid);
			preparedStatement.setInt(2, stepCount);
			preparedStatement.setString(3, action);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}