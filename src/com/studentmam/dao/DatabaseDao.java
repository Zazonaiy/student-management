package com.studentmam.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.studentmam.datasource.DatabaseStudentDataSource;
import com.studentmam.datasource.StudentDataSource;
import com.studentmam.model.Student;


public class DatabaseDao implements StudentDao{
	private static final String URL = "jdbc:mysql://localhost:3306/student-management?useSSL=false&serverTimezoneCST";
	private Connection con;
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
	
	public DatabaseDao() throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection(URL, "root", "aiywh3");
	}

	@Override
	public Student addStudent(Student student) {
		String insertSql = "insert into t_student (s_num,s_name,s_birthday,s_sex) values (?,?,?,?)";
		try {
			PreparedStatement pst = con.prepareStatement(insertSql);
			pst.setInt(1, Integer.valueOf(student.getStuNum()));
			pst.setString(2, student.getStuName());
			java.sql.Date sqlDate = new java.sql.Date(SDF.parse(student.getBirthDay()).getTime());
			pst.setDate(3, sqlDate);
			pst.setString(4, String.valueOf(student.getSex()));
			
			
			int insertResult = pst.executeUpdate();
			System.out.println("insert result = " + insertResult + student.getSex());
			pst.close();
			
			StudentDataSource.STUDENT_LIST.add(student);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return student;
	}

	@Override
	public Student delStudent(String stuNum) {
		String deleteSql = "delete from t_student where s_num=?";
		//String selectSql = "select * from t_student where s_num=?";
		try {
			/*
			PreparedStatement pst = con.prepareStatement(selectSql);
			pst.setInt(1, Integer.valueOf(stuNum));
			ResultSet rs = pst.executeQuery();
			String s_num = String.valueOf(rs.getInt("s_num"));
			String s_name = rs.getString("s_name");
			//Date s_birthday = rs.getDate("s_birthday");
			String s_birthday = SDF.format(rs.getDate("s_birthday"));
			Integer s_sex = rs.getInt("s_sex");
			Student stu = new Student(s_num, s_name, s_birthday, s_sex);
			*/
			System.out.println("----------------");
			Student resStu = null;
			
			//从内存中删除
			Iterator<Student> iter = StudentDataSource.STUDENT_LIST.iterator();
			while (iter.hasNext()) {
				Student stu = iter.next();
				if (stu.getStuNum().equals(stuNum)) {
					System.out.println("找到了");
					resStu = stu;
					iter.remove();
				}
			}
			System.out.println(resStu);
			
			//从数据库中删除
			PreparedStatement pst = con.prepareStatement(deleteSql);
			pst.setInt(1, Integer.valueOf(stuNum));
			int deleteResult = pst.executeUpdate();
			System.out.println("delete result = " + deleteResult);
			pst.close();
			
			
			return resStu;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	//	将内存同步到数据库
	@Override
	public List<Student> updStudent() {
		String updateSql = "update t_student set s_name=?, s_birthday=?, s_sex=? where s_num=?";
		try {
			//con.setAutoCommit(false);
			PreparedStatement pst = con.prepareStatement(updateSql);
			for (Student student : StudentDataSource.STUDENT_LIST) {
				pst.setString(1, student.getStuName());
				pst.setDate(2, new java.sql.Date(SDF.parse(student.getBirthDay()).getTime()));
				pst.setString(3, String.valueOf(student.getSex()));
				pst.setInt(4,Integer.valueOf(student.getStuNum()));
				
				pst.addBatch();
				
			}
			pst.executeBatch();
			//ResultSet keyRs = pst.getGeneratedKeys();
			//while (keyRs.next()) {
			//	System.out.println("s_num: " + keyRs.getMetaData().getColumnClassName(1) + " 已更新 . . .");
			//}
			
			
			System.out.println("更新完成");
			pst.close();
			//con.commit();
			//con.setAutoCommit(true);
			
			
		}catch (Exception e) {
			System.out.println("yichang");
			e.printStackTrace();
			return null;
		}
		
		return StudentDataSource.STUDENT_LIST;
	}
	@Override
	public void updStudent(String stuNo, String stuName, String birthDay, String sex) {
		
		String updateSql = "update t_student set s_name=?, s_birthday=?, s_sex=? where s_num=?";
		try {
			PreparedStatement pst = con.prepareStatement(updateSql);
			pst.setString(1, stuName);
			pst.setDate(2, new java.sql.Date(DatabaseStudentDataSource.SDF.parse(birthDay).getTime()));
			pst.setString(3, String.valueOf(sex));
			pst.setInt(4,Integer.valueOf(stuNo));			
			pst.executeUpdate();
			
			System.out.println("更新完成");
			pst.close();

		}catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
	// 读取数据库全部内容
	@Override
	public JSONArray getStudentList(String[] orderBy, String name, Integer[] pagging) {
		String orderWay = "";
		String byName = "";
		JSONArray studentList = new JSONArray();
		if (orderBy != null) {
			orderWay = " order by " + orderBy[0] + " " + orderBy[1];
		}
		if (name != null ) {
			 byName = "where s_name like '%" + name + "%'";
		}
		
		String selectSql = "select * from t_student" + byName + orderWay;
		System.out.println(selectSql);
		
		try {
			PreparedStatement pst = con.prepareStatement(selectSql);
			ResultSet rs = pst.executeQuery();
			//if (!StudentDataSource.STUDENT_LIST.isEmpty()) {
			//	//	从内存获取列表
			//	System.out.println("从内存读取 " + StudentDataSource.STUDENT_LIST);
			//	return StudentDataSource.STUDENT_LIST;
			//}else {
				// 从数据库获取列表并初始化内存列表
				//List<Student> studentList = new ArrayList<>();
				while (rs.next()) {
					String s_num = String.valueOf(rs.getInt("s_num"));
					String s_name = rs.getString("s_name");
					String s_birthday = SDF.format(rs.getDate("s_birthday"));
					Integer s_sex = rs.getInt("s_sex");
					//Student stu = new Student(s_num, s_name, s_birthday, s_sex);
					//studentList.add(stu);
					//StudentDataSource.STUDENT_LIST.add(stu);
					
					JSONObject stuJson = new JSONObject();
					stuJson.put("s_num", s_num);
					stuJson.put("s_name", s_name);
					stuJson.put("s_birthday", s_birthday);
					stuJson.put("s_sex", s_sex);
					studentList.add(stuJson);
				}
				pst.close();
				System.out.println(studentList);
				return studentList;
			//}
			
		}catch (Exception e) {
			return null;
		}
	}

	@Override
	public JSONArray findStudentByName(String[] orderBy, String name, Integer[] pagging) {
		//JSONObject json = new JSONObject();
		//List<Student> result = StudentDataSource.STUDENT_LIST.stream().filter(student -> student.getStuName().contains(name))
		//		.collect(Collectors.toList());
		JSONArray result = getStudentList(orderBy ,name, pagging);
		//json.put("studentList", result);
		return result;
	}

	@Override
	public Integer getAllStudentCount() {
		String selectSql = "select count(*) from t_student";
		try {
			PreparedStatement pst = con.prepareStatement(selectSql);
			ResultSet rs = pst.executeQuery();
			Integer count = rs.getInt(1);
			return count;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Student findStudentByNum(String stuNum) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
