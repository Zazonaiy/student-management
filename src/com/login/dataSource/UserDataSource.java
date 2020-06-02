package com.login.dataSource;

import java.sql.Connection;

public interface UserDataSource {
	public Connection getConnection();
	public void closeConnection(Connection con);
	public void closeDataSource();
}
