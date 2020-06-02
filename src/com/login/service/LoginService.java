package com.login.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import com.login.bean.User;
import com.login.controller.DaoTypeController;
import com.login.dao.LoginDao;
import com.login.dao.LoginDatabaseDao;

@Service
public class LoginService {
	public static LoginService instance = new LoginService();
	@Autowired
	@Qualifier("loginDatabaseDao")
	private LoginDao loginDao;
	
	private LoginService() {
		//loginDao = DaoTypeController.chooseDao();
		loginDao = new LoginDatabaseDao();
	}
	
	public JSONObject addUser(User user) {
		return loginDao.addUser(user);
	}
	
	public JSONObject updateUser(User user) {
		return loginDao.updateUser(user);
	}
	
	public JSONObject validateUser(User user) {
		JSONObject json = loginDao.getUser(user.getUserName());
		JSONObject resultJson = new JSONObject();
		if ((Boolean)json.get("isSuccess")) {
			//从数据库获取数据成功
			User resultUser = (User) json.get("user");
			if (user.getPassWord().equals(resultUser.getPassWord())) {
				//密码正确
				resultJson.put("user", user);
				resultJson.put("isValidateSuccess", true);
				resultJson.put("information", "welcome");
			}else {
				//密码错误
				resultJson.put("user", user);
				resultJson.put("isValidateSuccess", false);
				resultJson.put("information", "passwordError");
			}
		}else {
			//从数据库获取数据失败
			if (json.get("user") == null) {
				//用户不存在
				resultJson.put("user", user);
				resultJson.put("isValidateSuccess", false);
				resultJson.put("information", "userNotExist");
			}else {
				//后台系统错误
				resultJson.put("user", user);
				resultJson.put("isValidateSuccess", false);
				resultJson.put("information", "SystemError");
			}
		}
		return resultJson;
	}
	
	
	public static LoginService getInstance() {
		return instance;
	}
}
