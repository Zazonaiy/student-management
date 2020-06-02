package com.studentmam.datasource;

import java.sql.Connection;

public interface IStudentDataSource {
	public Connection getConnection();
	public void closeConnection(Connection con);
	public void closeDataSource();
}
