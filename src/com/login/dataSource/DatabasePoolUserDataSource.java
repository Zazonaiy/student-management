package com.login.dataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

/**
 * 使用druid连接池的数据源
 * @return
 */

public class DatabasePoolUserDataSource implements UserDataSource{
	private static DatabasePoolUserDataSource instance = new DatabasePoolUserDataSource();
	private DruidDataSource dataSource;
	
	public DatabasePoolUserDataSource() {
		try {
			Properties prop = new Properties();
			InputStream is = DatabasePoolUserDataSource.class.getClassLoader().getResourceAsStream("druid.properties");
			prop.load(is);
			is.close();
			
			//生成DruidDataSource实例
			dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public Connection getConnection() {
		try {
			Connection con = dataSource.getConnection();
			System.out.println("获取连接成功! con=" + con);
			return con;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
	
	
	public static DatabasePoolUserDataSource getInstance() {
		return instance;
	}

	
}
