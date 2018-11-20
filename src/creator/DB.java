package creator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
	Connection connection;
	Statement statement;
	PreparedStatement preparedStatement;
	
	DB(){
		// load the sqlite-JDBC driver using the current class loader
	    try {
	    	Class.forName("org.sqlite.JDBC");
	    	// create a database connection
	    	connection = DriverManager.getConnection("jdbc:sqlite:desktopguide.db");
	    	
	    	this.statement = connection.createStatement();
	    	this.statement.setQueryTimeout(30);  // set timeout to 30 sec.
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	void createGuide(String title, String author) {
		author = "pramod";
		try {
			String sql = "CREATE TABLE IF NOT EXISTS guideinfo (guideid INTEGER PRIMARY KEY AUTOINCREMENT, title STRING, author STRING)";
			statement.executeQuery(sql);
			
			sql = "INSERT INTO guideinfo(title,author) values (?,?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, title);
			preparedStatement.setString(2, author);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void createStep(int guideid, int stepCount, String action) {
		try {
			String sql = "CREATE TABLE IF NOT EXISTS stepinfo (stepid INTEGER PRIMARY KEY AUTOINCREMENT, guideid INTEGER, stepnumber INTEGER, stepaction STRING)";
			statement.executeQuery(sql);
			
			sql = "INSERT INTO guideinfo(guideid,stepnumber,stepaction) values (?,?,?)";
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