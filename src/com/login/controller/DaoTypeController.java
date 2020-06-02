package com.login.controller;

import java.io.InputStream;
import java.util.Properties;

import com.login.dao.LoginDao;

public class DaoTypeController {
	public static LoginDao chooseDao() {
		try {
			InputStream is = DaoTypeController.class.getClassLoader().getResourceAsStream("dao_type.properties");
			Properties prop = new Properties();
			prop.load(is);
			String daoType = prop.getProperty("daotype");
			LoginDao dao = (LoginDao) Class.forName("login.dao." + daoType).newInstance();
			return dao;
		}catch (Exception e) {
			return null;
		}

	}
}
