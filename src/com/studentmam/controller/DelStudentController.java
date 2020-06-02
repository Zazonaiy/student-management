package com.studentmam.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.studentmam.exception.ObjectReadWriteException;
import com.studentmam.service.StudentService;


@WebServlet("/delstu")
public class DelStudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentService studentService;
	

    public DelStudentController() {
        super();
        studentService = new StudentService();
        studentService.setDao(DaoTypeController.chooseDao());
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
		String stuNum = request.getParameter("stuNum");
		if (stuNum == null) {
			stuNum = request.getParameter("stuNums");
			
		}
		String[] stuNums = stuNum.split(" ");
		try {
			for (String str : stuNums) {
				studentService.delStudent(str);
				System.out.println("已删除学号为 " + str + " 的学生");
			}
			//studentService.delStudent(stuNum);
		} catch (ObjectReadWriteException e) {
			e.printStackTrace();
			request.getRequestDispatcher("/WEB-INF/view/write_error.jsp").forward(request, response);
		}
		//System.out.println("已删除学号为 " + stuNum + " 的学生");
		response.sendRedirect("student");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
