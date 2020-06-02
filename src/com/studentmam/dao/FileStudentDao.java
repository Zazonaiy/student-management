package com.studentmam.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.studentmam.model.Student;
import com.alibaba.fastjson.JSONObject;
import com.studentmam.datasource.*;

@Deprecated
public class FileStudentDao /*implements StudentDao*/{
	private static final String ADDRESS = "./students.obj"; 
	private boolean ifReadFromFile = true;
	
	
	
	
	public synchronized Student addStudent(Student student) {
		StudentDataSource.STUDENT_LIST.add(student);
		Object writeElem = FileStudentDao.write(StudentDataSource.STUDENT_LIST, ADDRESS);
		if (writeElem != null) {
			return student;
		}
		return null;
	}

	
	public List<Student> getStudentList(String[] orderBy, String name, Integer[] pagging) {
		if (orderBy != null) {
			//TODO 添加排序
		}
		if (ifReadFromFile) {
			
			List<Student> stuList = (List<Student>)FileStudentDao.read("./students.obj");
			
			if (stuList == null) {	//初始化
				Student stu = new Student("1", "杨伟豪", "1999-06-12", 1);
				StudentDataSource.STUDENT_LIST.add(stu);
				FileStudentDao.write(StudentDataSource.STUDENT_LIST, ADDRESS);
			}
			StudentDataSource.STUDENT_LIST.addAll(stuList);
			System.out.println(stuList);
			ifReadFromFile = false;
			return stuList;
		}

		return StudentDataSource.STUDENT_LIST;
	}
	
	
	public List<Student> updStudent(){
		Object updObj = FileStudentDao.write(StudentDataSource.STUDENT_LIST, ADDRESS);
		if (updObj == null) {
			return null;
		}
		return (List<Student>) updObj;
	}
	
	
	public synchronized Student delStudent(String stuNum) {
		Iterator<Student> iter = StudentDataSource.STUDENT_LIST.iterator();
		while (iter.hasNext()) {
			Student stu = iter.next();
			if (stu.getStuNum().equals(stuNum)) {
				iter.remove();
				if (FileStudentDao.write(StudentDataSource.STUDENT_LIST, ADDRESS) == null) {
					return null;	//写入文件失败
				}
				return stu;	//成功并返回被删对象
			}
		}
		return null;	//没有找到对应num的对象,不过这儿不存在这种情况
	}
	
	public static Object write(Object obj, String address) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(address));
			oos.writeObject(obj);
			oos.close();
			return obj;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Object read(String address) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(address));
			Object result = ois.readObject();
			ois.close();
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public JSONObject findStudentByName(String[] orderBy, String name, Integer[] pagging) {
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
