package com.studentmam.dao;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.studentmam.datasource.StudentDataSource;
import com.studentmam.model.Student;

/**
 * 内存版的Student DAO
 * @author 杨伟豪
 *
 */
@Deprecated
public class MemoryStudentDao /*implements StudentDao*/{
	public synchronized Student addStudent(Student student) {
		StudentDataSource.STUDENT_LIST.add(student);
		return student;
	}

	
	public synchronized List<Student> getStudentList(String[] orderBy, String name, Integer[] pagging) {
		if (orderBy == null) {
			return StudentDataSource.STUDENT_LIST;
		}else {
			//TODO 添加排序
			return StudentDataSource.STUDENT_LIST;
		}
		
	}

	
	public Student delStudent(String stuNum) {
		Iterator<Student> iter = StudentDataSource.STUDENT_LIST.iterator();
		while (iter.hasNext()) {
			Student stu = iter.next();
			if (stu.getStuNum().equals(stuNum)) {
				iter.remove();
				return stu;
			}
		}
		return null;
	}
	
	
	public List<Student> updStudent() {
		return StudentDataSource.STUDENT_LIST;
	}

	
	public JSONObject findStudentByName(String[] orderBy, String name, Integer[] pagging) {
		//public JSONObject findStudentByName(String[] orderBy, String name, Integer[] pagging)
		JSONObject json = new JSONObject();
		List<Student> result = StudentDataSource.STUDENT_LIST.stream().filter(student -> student.getStuName().contains(name))
				.collect(Collectors.toList());
		json.put("studentList", result);
		return json;
	}

	
	public Integer getAllStudentCount() {
		
		return StudentDataSource.STUDENT_LIST.size();
	}
}
