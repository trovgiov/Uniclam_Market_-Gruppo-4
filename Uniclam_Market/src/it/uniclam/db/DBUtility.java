package it.uniclam.db;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DBUtility {

 
	public static final String DRIVERNAME="com.mysql.jdbc.Driver";
	public static final String USER="sql497212";
	public static final String PASSWORD="SSzldHpRKz";
	public static final String URL="jdbc:mysql://sql4.freesqldatabase.com:3306/sql497212";
 
	static{
		
		try {
			Class.forName(DRIVERNAME);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//Insert
	public static Connection getDBConnection() {

		Connection dbConnection = null;

	

		try {

			dbConnection = (Connection) DriverManager.getConnection(
                            URL, USER,PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}
	
	public static Statement getStatement() throws SQLException{
		
		return (Statement) DriverManager.getConnection(URL, USER, PASSWORD).createStatement();
	}
	

  
}
