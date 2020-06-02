package com.login.bean;

import java.io.Serializable;

public class User implements Serializable{
	private String userName;
	private String passWord;
	
	public User(String userName, String passWord) {
		this.userName = userName;
		this.passWord = passWord;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	@Override
	public String toString() {
		return "�û�: " + userName;
	}
	
	@Override
	public boolean equals(Object u) {
		if (u == null) return false;
		if (u.getClass() != this.getClass()) return false;
		if (((User)u).getUserName().equals(this.getUserName())) {
			if (((User)u).getUserName() != null && this.getUserName() != null) {
				if (((User)u).getPassWord().equals(this.getPassWord())) {
					if(((User)u).getPassWord() != null && this.getPassWord() != null) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
}
