package com.login.dao;

import com.alibaba.fastjson.JSONObject;

import com.login.bean.User;


public interface LoginDao {
	public JSONObject addUser(User user);
	public JSONObject updateUser(User user);
	public JSONObject getUser(String username);
}
