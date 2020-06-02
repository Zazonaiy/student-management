package com.studentmam.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.studentmam.datasource.StudentDataSource;
import com.studentmam.exception.ObjectReadWriteException;
import com.studentmam.model.Student;
import com.studentmam.service.StudentService;

@WebServlet("/updstu")
public class UpdStudentController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private StudentService studentService;
	
	public UpdStudentController() {
		super();
		studentService = new StudentService();
		studentService.setDao(DaoTypeController.chooseDao());
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String stuNo = request.getParameter("stu_num");
		String stuName = request.getParameter("stu_name");
		String birthDay = request.getParameter("birth_day");
		Integer sex = Integer.valueOf(request.getParameter("sex"));
		//System.out.println(stuNo);	//TODO
		
		if (stuName == null || birthDay == null || sex == null) {
			System.out.println("!!!!");
			//request.getRequestDispatcher("/WEB-INF/view/stu_update_error.jsp").forward(request, response);
			response.sendRedirect("student");
			return;
		}
		
		/*
		System.out.println(StudentDataSource.STUDENT_LIST);
		for (Student stu : StudentDataSource.STUDENT_LIST) {
			System.out.println("----------------");
			if (stu.getStuNum().equals(stuNo)) {
				System.out.println("找到目标学生了");
				stu.setStuName(stuName);
				stu.setBirthDay(birthDay);
				stu.setSex(sex);
				
				try {
					studentService.updStudent();
					response.sendRedirect("student");
				}catch (ObjectReadWriteException e) {
					
					e.printStackTrace();
					request.getRequestDispatcher("/WEB-INF/view/write_error.jsp").forward(request, response);
				}
				System.out.println(stu);
				return ;
			}
		}*/
		
		//System.out.println("!!!");
		//request.getRequestDispatcher("/WEB-INF/view/stu_update_error.jsp").forward(request, response); 
		//response.sendRedirect("student");
		//return;
		try {
			studentService.updateStudent(stuNo, stuName, birthDay, sex.toString());
			response.sendRedirect("student");
			return;
		}catch (ObjectReadWriteException e) {
			
			e.printStackTrace();
			request.getRequestDispatcher("/WEB-INF/view/write_error.jsp").forward(request, response);
			return;
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
