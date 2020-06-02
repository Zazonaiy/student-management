package com.studentmam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.studentmam.datasource.DatabaseDruidPoolStudentDataSource;
import com.studentmam.datasource.DatabasePoolStudentDataSource;
import com.studentmam.datasource.DatabaseStudentDataSource;
import com.studentmam.datasource.IStudentDataSource;
import com.studentmam.datasource.StudentDataSource;
import com.studentmam.model.Student;

@Component("databaseStudentDao2")
public class DatabaseStudentDao2 implements StudentDao{

	/**
	 * 从数据源中获取连接
	 * @return
	 */
	private Connection getConnectionFromDataSource() {
		//从数据库连接池中获取连接
		//DatabasePoolStudentDataSource dataSource = DatabasePoolStudentDataSource.getInstance();
		IStudentDataSource dataSource = DatabaseDruidPoolStudentDataSource.getInstance();
		return dataSource.getConnection();
	}
	/**
	 * 关闭连接
	 * 为了方便修改代码单独提出来作为一个方法
	 * @param con
	 */
	private void closeConnectionFromDataSource(Connection con) {
		try {
			//DatabasePoolStudentDataSource.getInstance().closeConnection(con);
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public Student addStudent(Student student) {
		//DatabaseStudentDataSource dataSource = DatabaseStudentDataSource.getInstance();
		//Connection con = dataSource.getConnection();
		Connection con = getConnectionFromDataSource();
		String insertSql = "insert into t_student (s_num,s_name,s_birthday,s_sex) values (?,?,?,?)";
		try {
			PreparedStatement pst = con.prepareStatement(insertSql);
			pst.setInt(1, Integer.valueOf(student.getStuNum()));
			pst.setString(2, student.getStuName());
			java.sql.Date sqlDate = new java.sql.Date(DatabaseStudentDataSource.SDF.parse(student.getBirthDay()).getTime());
			pst.setDate(3, sqlDate);
			pst.setString(4, String.valueOf(student.getSex()));
			
			
			int insertResult = pst.executeUpdate();
			System.out.println("insert result = " + insertResult + student.getSex());
			pst.close();
			//dataSource.closeConnection(con);
			closeConnectionFromDataSource(con);
			
			//StudentDataSource.STUDENT_LIST.add(student);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return student;
	}

	@Override
	public Student delStudent(String stuNum) {
		//DatabaseStudentDataSource dataSource = DatabaseStudentDataSource.getInstance();
		//Connection con = dataSource.getConnection();
		Connection con = getConnectionFromDataSource();
		String deleteSql = "delete from t_student where s_num=?";
		try {
			
			Student resStu = null;
			
			//从内存中删除
			/*
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
			*/
			
			//从数据库中删除
			PreparedStatement pst = con.prepareStatement(deleteSql);
			pst.setInt(1, Integer.valueOf(stuNum));
			int deleteResult = pst.executeUpdate();
			System.out.println("delete result = " + deleteResult);
			pst.close();
			//dataSource.closeConnection(con);
			closeConnectionFromDataSource(con);
			
			return resStu;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Deprecated
	@Override
	public List<Student> updStudent() {
		//DatabaseStudentDataSource dataSource = DatabaseStudentDataSource.getInstance();
		//Connection con = dataSource.getConnection();
		Connection con = getConnectionFromDataSource();
		String updateSql = "update t_student set s_name=?, s_birthday=?, s_sex=? where s_num=?";
		try {
			PreparedStatement pst = con.prepareStatement(updateSql);
			for (Student student : StudentDataSource.STUDENT_LIST) {
				pst.setString(1, student.getStuName());
				pst.setDate(2, new java.sql.Date(DatabaseStudentDataSource.SDF.parse(student.getBirthDay()).getTime()));
				pst.setString(3, String.valueOf(student.getSex()));
				pst.setInt(4,Integer.valueOf(student.getStuNum()));
				
				pst.addBatch();
				
			}
			pst.executeBatch();
			
			
			System.out.println("更新完成");
			pst.close();
			//dataSource.closeConnection(con);
			closeConnectionFromDataSource(con);
			
		}catch (Exception e) {
			//System.out.println("yichang");
			e.printStackTrace();
			return null;
		}
		
		return StudentDataSource.STUDENT_LIST;
	}
	@Override
	public void updStudent(String stuNo, String stuName, String birthDay, String sex) {
		Connection con = getConnectionFromDataSource();
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
			closeConnectionFromDataSource(con);
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
	/**
	 * 
	 * @param pageNo null 1为初始页
	 */
	@Override
	public JSONArray getStudentList(String[] orderBy, String name, Integer[] pagging) {
		String orderWay = " order by s_num asc";
		String byName = "";
		JSONArray studentList = new JSONArray();
		//Integer pageNo = pagging[0];
		//Integer pageSize = pagging[1];
		
		String offsetSQLAdd = "";
		if (orderBy != null) {
			orderWay = " order by " + orderBy[0] + " " + orderBy[1];
		}
		if (name != null ) {
			 byName = " where s_name like '%" + name + "%'";
		}
		if (pagging != null) {
			Integer offset = (pagging[0] - 1) * pagging[1];
			offsetSQLAdd = " limit " + offset + ", " + pagging[1];
			System.out.println(offsetSQLAdd);
			
		}else {
			offsetSQLAdd = " limit 0, 10";
		}
		
		//DatabaseStudentDataSource dataSource = DatabaseStudentDataSource.getInstance();
		//Connection con = dataSource.getConnection();
		Connection con = getConnectionFromDataSource();
		String selectSql = "select * from t_student" + byName + orderWay + offsetSQLAdd;
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
					String s_birthday = DatabaseStudentDataSource.SDF.format(rs.getDate("s_birthday"));
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
				//dataSource.closeConnection(con);
				closeConnectionFromDataSource(con);
				System.out.println(studentList);
				
				return studentList;
			//}
			
		}catch (Exception e) {
			return null;
		}

	}

	@Override
	public JSONArray findStudentByName(String[] orderBy, String name, Integer[] pagging) {
		//JSONObject stuArray = new JSONObject();
		//List<Student> result = StudentDataSource.STUDENT_LIST.stream().filter(student -> student.getStuName().contains(name))
		//		.collect(Collectors.toList());
		JSONArray result = getStudentList(orderBy ,name, pagging);
		//json.put("studentList", result);
		return result;
	}
	
	@Override
	public Student findStudentByNum(String stuNum) {
		Connection con = getConnectionFromDataSource();
		String sql = "select * from t_student where s_num=?";
		Student stu = null;
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, Integer.valueOf(stuNum));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				String s_num = String.valueOf(rs.getInt("s_num"));
				String s_name = rs.getString("s_name");
				String s_birthday = DatabaseStudentDataSource.SDF.format(rs.getDate("s_birthday"));
				Integer s_sex = rs.getInt("s_sex");
				stu = new Student(s_num, s_name, s_birthday, s_sex);
			}
			
			return stu;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Integer getAllStudentCount() {
		Connection con = getConnectionFromDataSource();
		String sql = "select count(*) from t_student";
		
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			Integer count = 0;
			while(rs.next()) {
				count = rs.getInt(1);
				System.out.println("数据个数: " + count);
				
			}
			rs.close();
			pst.close();
			closeConnectionFromDataSource(con);
			return count;
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
