package com.login.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.login.bean.User;
import com.login.bean.UserList;

public class Test {
	public static void main(String[] args) {
		UserList userList = new UserList(new User("aaa", "111"), new User("bbb", "222"));
		//UserIO.write(userList, "./user.obj");
		
		//UserList userList = (UserList) UserIO.read("user.obj");
		//for (User user : userList.getList()) {
		//	System.out.println(user.getUserName());
		//}
		System.out.println(userList.isUserExsit(new User("ccc", "111")));
		UserIO.write(new User("aaa", "111"), "./test.obj");
	}
}
