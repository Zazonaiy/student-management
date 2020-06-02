package com.studentmam.datasource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;


public class DatabaseDruidPoolStudentDataSource implements IStudentDataSource{
	private DruidDataSource dataSource;
	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
	private static DatabaseDruidPoolStudentDataSource instance = new DatabaseDruidPoolStudentDataSource();
	
	/**
	 * 构造方法，初始化Druid连接池
	 */
	private DatabaseDruidPoolStudentDataSource() {
		try {
			Properties prop = new Properties();
			InputStream is = DatabaseDruidPoolStudentDataSource.class.getClassLoader().getResourceAsStream("druid.properties");
			prop.load(is);
			is.close();
			
			//生成DruidDataSource实例
			dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(prop);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public Connection getConnection() {
		try {
			Connection con = dataSource.getConnection();
			System.out.println("获取连接成功! con=" + con);
			return con;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	@Override
	public void closeConnection(Connection con) {
		try {
			con.close();
			System.out.println("连接关闭成功");
		} catch (SQLException e) {
			System.out.println("连接关闭失败");
			e.printStackTrace();
		}
		
	}
	@Override
	public void closeDataSource() {
		this.dataSource.close();
		
	}
	
	public static DatabaseDruidPoolStudentDataSource getInstance() {
		return instance;
	}
}
