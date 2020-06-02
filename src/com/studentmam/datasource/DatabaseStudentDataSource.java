package com.studentmam.datasource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class DatabaseStudentDataSource {
	private String dbUrl;
	private String dbUser;
	private String dbPass;
	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
	
	private static DatabaseStudentDataSource instance = new DatabaseStudentDataSource();
	
	
	private DatabaseStudentDataSource() {
		try {
			InputStream is = DatabaseStudentDataSource.class.getClassLoader().getResourceAsStream("database.properties");
			Properties prop = new Properties();
			prop.load(is);
			dbUrl = prop.getProperty("db_url");
			dbUser = prop.getProperty("db_user");
			dbPass = prop.getProperty("db_pass");
			is.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static DatabaseStudentDataSource getInstance() {
		return instance;
	}
	

	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			return con;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		DatabaseStudentDataSource dataSource = DatabaseStudentDataSource.getInstance();
		Connection con = dataSource.getConnection();
		System.out.println(con);
		dataSource.closeConnection(con);
	}
}
