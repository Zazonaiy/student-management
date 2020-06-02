package com.studentmam.controller.ajaxcon;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.studentmam.controller.DaoTypeController;
import com.studentmam.dao.FileStudentDao;
import com.studentmam.dao.MemoryStudentDao;
import com.studentmam.datasource.StudentDataSource;
import com.studentmam.exception.ObjectReadWriteException;
import com.studentmam.model.Student;
import com.studentmam.service.StudentService;


@WebServlet("/student_ajax")
public class StudentControllerAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;
   

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/student_ajax.jsp").forward(request, response);
		
		
		/*
		List<Student> studentList = null;
		String keywords = request.getParameter("keyword");
		String[] orderBy = (String[]) request.getAttribute("orderBy");
		String order = "";	// 返回被点的a标签的class
		Integer[] pagging = (Integer[]) request.getAttribute("pagging");
		
		if (orderBy != null) {
			order = "od-by-" + orderBy[0].replaceAll("s_", "") + "-" + orderBy[1];
			//String[] str = order.split("s_");
			//order
		}
		
		int pageNo = 1;
		int lastPage = 1;
		int pageSize = 5;

		if (pagging != null) {
			//pageNo = Integer.valueOf(request.getParameter("pageNo"));
			pageNo = pagging[0];
			pageSize = pagging[1];
			System.out.println("当前页面：-----------------" + pageNo);
		}
		
		try {
			
			if (pagging != null) {
				studentList = studentService.getStudents(orderBy, keywords, pagging);
			}else {
				Integer[] InitPagging = {pageNo, pageSize};
				studentList = studentService.getStudents(orderBy, keywords, InitPagging);
			}

			
			if (studentList != null) {
				int size = studentService.getAllStudentCount();
				
				request.setAttribute("studentCount", size);// 获得学生个数，返回前端

				if (size % pageSize != 0) {
					lastPage = size / pageSize + 1;
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!! lastPage: " + lastPage);
				}else {
					lastPage = size / pageSize;
					System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx lastPage: " + lastPage + " size: " + size + " per: " + pageSize);
				}

				System.out.println("------------------------- lastPage: " + lastPage);
			}

		}catch (ObjectReadWriteException e) {
			request.getRequestDispatcher("/WEB-INF/view/read_error.jsp").forward(request, response);
			return ;
		}
		String orderParam1;
		String orderParam2;
		if (orderBy == null) {
			orderParam1 = "s_num";
			orderParam2 = "asc";
		}else {
			orderParam1 = orderBy[0];
			orderParam2 = orderBy[1];
		}
		
		
		PrintWriter writer = response.getWriter();
		
		JSONObject json = new JSONObject();
		json.put("studentList", studentList);
		json.put("keywords", keywords);
		json.put("pageNo", pageNo);
		json.put("pageSize", pageSize);
		json.put("lastPage", lastPage);
		json.put("order", order);
		json.put("orderParam1", orderParam1);
		json.put("orderParam2", orderParam2);
		
		writer.write(JSON.toJSONString(json));
		*/
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
