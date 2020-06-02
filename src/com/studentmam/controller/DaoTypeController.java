package com.studentmam.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.studentmam.dao.StudentDao;

public class DaoTypeController {
	
	public static StudentDao chooseDao() {
		
		try {
			InputStream is = DaoTypeController.class.getClassLoader().getResourceAsStream("dao_type.properties");
			Properties prop = new Properties();
			prop.load(is);
			String daoType = prop.getProperty("mam_daotype");
			StudentDao dao = (StudentDao) Class.forName("com.studentmam.dao." + daoType).newInstance();
			return dao;
		}catch (Exception e) {
			return null;
		}
		
		
	}
}
