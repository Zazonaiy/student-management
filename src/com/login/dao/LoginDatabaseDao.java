package com.login.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import com.login.bean.User;
import com.login.dataSource.DatabasePoolUserDataSource;
import com.login.dataSource.UserDataSource;

@Component("loginDatabaseDao")
public class LoginDatabaseDao implements LoginDao{
	private Connection getConnectionFromDataSource() {
		//有待改进，不符合开闭原则
		UserDataSource dataSource = DatabasePoolUserDataSource.getInstance();
		System.out.println(dataSource);
		return dataSource.getConnection();
	}
	private void closeConnectionFromDataSource(Connection con) {
		try {
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public JSONObject addUser(User user) {
		Connection con = getConnectionFromDataSource();
		JSONObject json = new JSONObject();
		String insertSql = "insert into t_user (u_name,u_pass) values (?,?)";
		try {
			PreparedStatement pst = con.prepareStatement(insertSql);
			pst.setString(1,user.getUserName());
			pst.setString(2, user.getPassWord());
			int insertResult = pst.executeUpdate();
			System.out.println("注册新用户 insert result = " + insertResult + " " + user.toString());
			
			pst.close();
			closeConnectionFromDataSource(con);
			
			json.put("user", user);
			json.put("isSuccess", true);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("user", user);
			json.put("isSuccess", false);
			return json;
		}

	}

	@Override
	public JSONObject updateUser(User user) {
		Connection con = getConnectionFromDataSource();
		JSONObject json = new JSONObject();
		String updateSql = "update t_user set u_pass=? where u_name=?";
		try {
			PreparedStatement pst = con.prepareStatement(updateSql);
			pst.setString(1, user.getPassWord());
			pst.setString(2, user.getUserName());
			int insertResult = pst.executeUpdate();
			System.out.println("修改用户密码 insert result = " + insertResult + " " + user.toString());
			pst.close();
			closeConnectionFromDataSource(con);
			
			json.put("user", user);
			json.put("isSuccess", true);
			return json;
		} catch (Exception e) {
			//e.printStackTrace();
			json.put("user", user);
			json.put("isSuccess", false);
			return json;
		}
		
	}

	@Override
	public JSONObject getUser(String username) {
		Connection con = getConnectionFromDataSource();
		JSONObject json = new JSONObject();
		String selectSql = "select * from t_user where u_name=?";
		try {
			PreparedStatement pst = con.prepareStatement(selectSql);
			pst.setString(1, username);
			
			ResultSet rs = pst.executeQuery();
			User user = null;
			while (rs.next()) {
				user = new User(rs.getString("u_name"), rs.getString("u_pass"));
				break;
			}
			rs.close();
			pst.close();
			closeConnectionFromDataSource(con);
			if (user == null) {
				json.put("user", user);
				json.put("isSuccess", false);
				json.put("userNotExist", true);
			}else {
				json.put("user", user);
				json.put("isSuccess", true);
				json.put("userNotExist", false);
			}
			System.out.println(json.getBoolean("isSuccess"));
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("user", null);
			json.put("isSuccess", false);
			json.put("userNotExist", true);
			System.out.println("error " + json.getBoolean("isSuccess"));
			return json;
		}

	}

}
