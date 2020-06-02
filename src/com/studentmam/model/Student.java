package com.studentmam.model;

import java.io.Serializable;

public class Student implements Serializable{
	private String stuNum;
	private String stuName;
	private String birthDay;
	private Integer sex;
	
	public Student(String stuNum, String stuName, String birthday, Integer sex) {
		this.birthDay = birthday;
		this.sex = sex;
		this.stuName = stuName;
		this.stuNum = stuNum;
	}
	public String getStuNum() {
		return stuNum;
	}
	public void setStuNum(String stuNum) {
		this.stuNum = stuNum;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	@Override
	public String toString() {
		return stuNum + " " +  stuName + " " + birthDay + " " + sex;
	}
}
