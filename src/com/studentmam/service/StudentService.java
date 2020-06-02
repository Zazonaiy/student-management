package com.studentmam.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.studentmam.dao.StudentDao;
import com.studentmam.datasource.StudentDataSource;
import com.studentmam.exception.ObjectReadWriteException;
import com.studentmam.model.Student;

@Service
public class StudentService {
	@Autowired
	@Qualifier("databaseStudentDao2")
	private StudentDao dao;
	public void setDao(StudentDao dao) {
		this.dao = dao;
	}
	
	//	这是一个事务
	public void addStudents(List<Student> studentList) {
		for (Student student : studentList) {
			dao.addStudent(student);	// TODO 效率低
		}
	}

	public void addStudent(Student student) throws ObjectReadWriteException {
		System.out.println(new Date() + " 新增学生: " + student);
		if(dao.addStudent(student) == null) {	//	null为写异常
			throw new ObjectReadWriteException();
		}
	}
	
	public void delStudent(String stuNum) throws ObjectReadWriteException {
		System.out.println("需要删除学号为: " + stuNum + " 的学生");
		if (dao.delStudent(stuNum) == null) {
			//System.out.println("！！！！！！！！！！！！！！！！！！！要抛异常了");
			//throw new ObjectReadWriteException();
		}
	}
	
	public void updStudent() throws ObjectReadWriteException {
		System.out.println("学生信息正在同步更新. . .");
		if (dao.updStudent() == null) {
			throw new ObjectReadWriteException();
		}
	}
	public void updateStudent(String stuNo, String stuName, String birthDay, String sex) throws ObjectReadWriteException {
		System.out.println("正在更新学生信息. . .");
		dao.updStudent(stuNo, stuName, birthDay, sex);
	}
	
	public JSONArray getStudents(String[] orderBy, String name, Integer[] pagging) throws ObjectReadWriteException{
		/*（
		List<Student> studentList = dao.getStudentList();
		if (studentList == null) {
			throw new ObjectReadWriteException();
		}
		return studentList;	//	null为读异常
		*/
		//List<Student> stuList = dao.getStudentList(orderBy, null, null); TODO 
		JSONArray stuList = dao.getStudentList(orderBy, name, pagging);
		if (stuList== null) {
			System.out.println("...................");
			throw new ObjectReadWriteException();
		}
		return stuList;
	}
	
	public JSONArray findStudentByName(String[] orderBy, String name, Integer[] pagging){
		return dao.findStudentByName(orderBy, name, pagging);
	}
	
	public Student findStudentByNum(String stuNum) {
		Student stu = dao.findStudentByNum(stuNum);
		
		return stu;
	}
	
	public int getAllStudentCount() {
		return dao.getAllStudentCount();
	}
}
