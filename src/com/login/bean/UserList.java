package com.login.bean;
import java.io.Serializable;
import java.util.*;

public class UserList implements Serializable{
	private List<User> userList;
	
	public UserList() {
		this.userList = new ArrayList<>();
	}
	
	public UserList(User ...users) {
		this.userList = new ArrayList<>();
		for (User e : users) {
			userList.add(e);
		}
	}
	
	public void addUser(User user) {
		userList.add(user);
	}
	
	public User findUserById(String userName) {
		for (User user : userList) {
			if (user.getUserName().equals(userName.trim())) {
				return user;
			}
		}
		return null;
	}
	public int validateLogon(User user) {
		/*
		for (User elem : userList) {
			if (elem.equals(user))
				return true;
		}
		return false;
		*/
		boolean userExsit = false;
		for (User elem : userList) {
			if (elem == null) return -1;		//��̨����
			if (elem.getClass() != user.getClass()) return -1;	//��̨����
			if (elem.getUserName().equals(user.getUserName())) {
				userExsit = true;
				if (elem.getUserName() == null && user.getUserName() == null) {
					return 3;	//�û���Ϊ��
				}
				if (elem.getPassWord().equals(user.getPassWord())) {
					if(elem.getPassWord() == null && user.getPassWord() == null) {
						return 3;	//����Ϊ��
					}
					return 0;	//��¼�ɹ�
				}
			}
		}
		if (userExsit) {
			return 2;	//�������
		}else {
			return 1;	//�û���������
		}
	}
	public int validateLogon(String username, String password) {
		boolean userExsit = false;
		for (User elem : userList) {
			if (elem == null) return -1;		//��̨����
			if (elem.getUserName().equals(username.trim())) {
				userExsit = true;
				if (elem.getUserName() == null && username.trim() == null) {
					return 3;	//�û���Ϊ��
				}
				if (elem.getPassWord().equals(password.trim())) {
					if(elem.getPassWord() == null && password.trim() == null) {
						return 3;	//����Ϊ��
					}
					return 0;	//��¼�ɹ�
				}
			}
		}
		if (userExsit) {
			return 2;	//�������
		}else {
			return 1;	//�û���������
		}
	}
	
	public boolean isUserExsit(String username) {
		for (User user : userList) {
			if (user.getUserName().equals(username.trim())) {
				return true;
			}
		}
		return false;
	}
	public boolean isUserExsit(User user) {
		for (User elem : userList) {
			if (elem.getUserName().equals(user.getUserName().trim())) {
				return true;
			}
		}
		return false;
	}
	
	public List<User> getList(){
		return userList;
	}
}
