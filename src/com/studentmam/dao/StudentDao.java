package com.studentmam.dao;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.studentmam.model.Student;

public interface StudentDao {
	//	dao 直接进行最细的原子操作
	public Student addStudent(Student student);
	public Student delStudent(String stuNum);
	@Deprecated
	public List<Student> updStudent();
	public void updStudent(String stuNo, String stuName, String birthDay, String sex);
	
	public JSONArray getStudentList(String[] orderBy, String name, Integer[] pagging);
	public JSONArray findStudentByName(String[] orderBy, String name, Integer[] pagging);
	public Student findStudentByNum(String stuNum);
	//public List<Student> getStudentListByPagingNo(Integer pageNo, Integer pageSize);
	public Integer getAllStudentCount();
}
