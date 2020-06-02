package com.studentmam.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.studentmam.dao.FileStudentDao;
import com.studentmam.dao.MemoryStudentDao;
import com.studentmam.datasource.StudentDataSource;
import com.studentmam.exception.ObjectReadWriteException;
import com.studentmam.model.Student;
import com.studentmam.service.StudentService;


@WebServlet("/addstu")
public class AddStudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private StudentService studentService;
 
    public AddStudentController() {
        super();
        studentService = new StudentService();
        studentService.setDao(DaoTypeController.chooseDao());
        //studentService.setDao(new FileStudentDao());
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 处理新增学生的处理逻辑
		String stuNo = request.getParameter("stu_num");
		for (Student stu : StudentDataSource.STUDENT_LIST) {
			if (stu.getStuNum().equals(stuNo)) {
				request.getRequestDispatcher("/WEB-INF/view/stu_existed_error.jsp").forward(request, response);
				return;
			}
		}
		String stuName = request.getParameter("stu_name");
		String birthDay = request.getParameter("birth_day");
		Integer sex = Integer.valueOf(request.getParameter("sex"));
		
		Student stu = new Student(stuNo, stuName, birthDay, sex);
		try {
			studentService.addStudent(stu);
		} catch (ObjectReadWriteException e) {
			e.printStackTrace();
			request.getRequestDispatcher("/WEB-INF/view/write_error.jsp").forward(request, response);
			return ;
		}
		
		System.out.println(stu);
		
		response.sendRedirect("student");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
