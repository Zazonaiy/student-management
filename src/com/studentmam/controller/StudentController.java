package com.studentmam.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.studentmam.dao.FileStudentDao;
import com.studentmam.dao.MemoryStudentDao;
import com.studentmam.datasource.StudentDataSource;
import com.studentmam.exception.ObjectReadWriteException;
import com.studentmam.model.Student;
import com.studentmam.service.StudentService;


@WebServlet("/student")
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private StudentService studentService;

    public StudentController() {
        super();
        studentService = new StudentService();
        studentService.setDao(DaoTypeController.chooseDao());
        //studentService.setDao(new FileStudentDao());
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Student> studentList = new ArrayList();
		String keywords = request.getParameter("keyword");
		String[] orderBy = (String[]) request.getAttribute("orderBy");
		String order = "";	// 返回被点的a标签的class
		Integer[] pagging = (Integer[]) request.getAttribute("pagging");
		/*
		if (keywords != null) {
			JSONObject studentJson = studentService.findStudentByName(keywords.trim(), order, pagging);
			List<Student> studentList = (List<Student>) studentJson.get("studentList");
			//System.out.println(keywords);
		}else if(orderBy != null) {
			try {
				studentList = studentService.getStudents(orderBy);
				order = "od-by-" + orderBy[0] + "-" + orderBy[1];
				order = order.replace("s_", "");
				System.out.println("以获取排序后的列表 " + studentList + "\n" + "排序方式： order by " + orderBy[0] + " " + orderBy[1]);
			}catch (ObjectReadWriteException e) {
				studentList = null;
				request.getRequestDispatcher("/WEB-INF/view/read_error.jsp").forward(request, response);
				return ;
			}
			
		}else {
			try {
				studentList = studentService.getStudents(null);
				System.out.println("!!!" + StudentDataSource.STUDENT_LIST);
				
			} catch (ObjectReadWriteException e) {
				// TODO Auto-generated catch block
				studentList = null;
				request.getRequestDispatcher("/WEB-INF/view/read_error.jsp").forward(request, response);
				return ;
			}
		}
		*/
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
				JSONArray jsonArray = studentService.getStudents(orderBy, keywords, pagging);
				for (Object obj : jsonArray) {
					JSONObject json = (JSONObject) obj;
					String s_num = json.getString("s_num");
					String s_name = json.getString("s_name");
					String s_birthday = json.getString("s_birthday");
					Integer s_sex = json.getInteger("s_sex");
					studentList.add(new Student(s_num,s_name,s_birthday,s_sex));
				}

			}else {
				Integer[] InitPagging = {pageNo, pageSize};
				//studentList = studentService.getStudents(orderBy, keywords, InitPagging);
				JSONArray jsonArray = studentService.getStudents(orderBy, keywords, InitPagging);
				for (Object obj : jsonArray) {
					JSONObject json = (JSONObject) obj;
					String s_num = json.getString("s_num");
					String s_name = json.getString("s_name");
					String s_birthday = json.getString("s_birthday");
					Integer s_sex = json.getInteger("s_sex");
					studentList.add(new Student(s_num,s_name,s_birthday,s_sex));
				}
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
		
		
		
		request.setAttribute("studentList", studentList);
		request.setAttribute("keywords", keywords);
		request.setAttribute("order", order);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("orderParam1", orderParam1);
		request.setAttribute("orderParam2", orderParam2);
		request.setAttribute("lastPage", lastPage);

		request.getRequestDispatcher("/WEB-INF/view/student.jsp").forward(request, response);
		return ;
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	


}
