package com.studentmam.controller.ajaxcon;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.studentmam.controller.DaoTypeController;
import com.studentmam.dao.FileStudentDao;
import com.studentmam.dao.MemoryStudentDao;
import com.studentmam.datasource.StudentDataSource;
import com.studentmam.exception.ObjectReadWriteException;
import com.studentmam.model.Student;
import com.studentmam.service.StudentService;
import com.studentmam.constant.Constants;;

/**
 * 学生信息管理类，用于响应ajax请求，包括查询、新增、修改、删除
 * @author 杨伟豪
 *
 */
@WebServlet(urlPatterns = "/student_man", loadOnStartup = 1)
public class StudentManagementControllerAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
    private StudentService studentService;

    //public StudentManagementControllerAjax() {
    //    super();
       // studentService = new StudentService();
       // studentService.setDao(DaoTypeController.chooseDao());
        //studentService.setDao(new FileStudentDao());
    //}
    
    /**
     * 查询学生信息，包括关键字搜索、排序、分页
     *  orderBy [0]排序依据 	[1]是否升序
     *  keywords 关键字
     *  pagging [0]当前页	[1]页面大小
     * @return
     * @throws IOException 
     * @throws ServletException 
     */
    public void queryStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	JSONObject json = new JSONObject();
    	PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		
		JSONArray studentList = null;
		String keywords = request.getParameter("keyword");
		String orderParam1;
		String orderParam2;
		if (request.getParameter("orderParam1").equals("") || request.getParameter("orderParam2").equals("")) {
			orderParam1 = "s_num";
			orderParam2 = "asc";
			
		}else {
			orderParam1 = (String)request.getParameter("orderParam1");
			orderParam2 = (String)request.getParameter("orderParam2");
		}
		Integer paggingParam1;
		Integer paggingParam2;
		int pageNo = 1;
		int lastPage = 1;
		int pageSize = 5;
		if (request.getParameter("paggingParam1").equals("") || request.getParameter("paggingParam2").equals("")) {
			System.out.println("++++++++++++++++");
			paggingParam1 = pageNo;
			paggingParam2 = pageSize;
			
		}else {
			System.out.println("--------------+--+-");
			paggingParam1 = Integer.valueOf(request.getParameter("paggingParam1"));
			paggingParam2 = Integer.valueOf(request.getParameter("paggingParam2"));
		}
		String[] orderBy = {orderParam1, orderParam2};
		Integer[] pagging = {paggingParam1, paggingParam2};
		
		
		String order = "";	// 返回被点的a标签的class
		if (orderBy != null) {
			order = "od-by-" + orderBy[0].replaceAll("s_", "") + "-" + orderBy[1];
			//String[] str = order.split("s_");
			//order
		}
		

		//if (pagging != null) {
		//	//pageNo = Integer.valueOf(request.getParameter("pageNo"));
		//	pageNo = pagging[0];
		//	pageSize = pagging[1];
		//	System.out.println("当前页面：-----------------" + pageNo);
		//}
		
		int studentCount = 0;
		try {
			/*
			if (pagging != null) {
				studentList = studentService.getStudents(orderBy, keywords, pagging);
			}else {
				Integer[] InitPagging = {pageNo, pageSize};
				studentList = studentService.getStudents(orderBy, keywords, InitPagging);
			}*/
			studentList = studentService.getStudents(orderBy, keywords, pagging);

			
			if (studentList != null) {
				studentCount = studentService.getAllStudentCount();
				
				//request.setAttribute("studentCount", size);// 获得学生个数，返回前端

				if (studentCount % pageSize != 0) {
					lastPage = studentCount / pageSize + 1;
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!! lastPage: " + lastPage);
				}else {
					lastPage = studentCount / pageSize;
					System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx lastPage: " + lastPage + " size: " + studentCount + " per: " + pageSize);
				}

				System.out.println("------------------------- lastPage: " + lastPage);
			}

		}catch (ObjectReadWriteException e) {
			//request.getRequestDispatcher("/WEB-INF/view/read_error.jsp").forward(request, response);
			json.put("status", Constants.DB_ERROR);
			renderJson(response, json);
			return;
		}
		/*
		String orderParam1;
		String orderParam2;
		if (orderBy == null) {
			orderParam1 = "s_num";
			orderParam2 = "asc";
		}else {
			orderParam1 = orderBy[0];
			orderParam2 = orderBy[1];
		}*/
		
		
		
		
		json.put("studentList", studentList);
		json.put("keywords", keywords);
		//json.put("pageNo", pageNo);
		//json.put("pageSize", pageSize);
		json.put("lastPage", lastPage);
		json.put("pageNo", paggingParam1);
		json.put("pageSize", paggingParam2);
		json.put("order", order);
		json.put("orderParam1", orderParam1);
		json.put("orderParam2", orderParam2);
		json.put("studentCount", studentCount);
		json.put("status", Constants.SUCCESS);
		System.out.println("! " + paggingParam1);
		System.out.println("/ " + paggingParam2);
		
		out.println(JSON.toJSONString(json));
    	out.flush();
    }
    
    public void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	String stuNum = request.getParameter("stuNum");
    	JSONObject json = new JSONObject();
    	if (stuNum == null) {
			stuNum = request.getParameter("stuNums");
			System.out.println("xxxxxxxxxxxxxxxxxxx ssssssss " + stuNum);
		}
    	String[] stuNums = stuNum.split(" ");
    	try {
    		for (String str : stuNums) {
				studentService.delStudent(str);
				System.out.println("已删除学号为 " + str + " 的学生");
			}
    		json.put("status", Constants.SUCCESS);
			renderJson(response, json);
    	}catch (ObjectReadWriteException e) {
			e.printStackTrace();
			//request.getRequestDispatcher("/WEB-INF/view/write_error.jsp").forward(request, response);
			json.put("status", Constants.DB_ERROR);
			renderJson(response, json);
		}
    	
    }
    
    public void updateStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	String stuNo = request.getParameter("stuNum");
		String stuName = request.getParameter("stuName");
		String birthDay = request.getParameter("birthDay");
		String sex = request.getParameter("sex");
		JSONObject json = new JSONObject();
		
		try {
			studentService.updateStudent(stuNo, stuName, birthDay, sex);
			System.out.println(stuNo + " 修改完成");
			json.put("status", Constants.SUCCESS);
		} catch (ObjectReadWriteException e) {
			e.printStackTrace();
			//request.getRequestDispatcher("/WEB-INF/view/write_error.jsp").forward(request, response);
			json.put("status", Constants.SUCCESS);
		}
		renderJson(response, json);
		
    }
    
    public void addStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String stuNum = request.getParameter("stuNum");
		String stuName = request.getParameter("stuName");
		String birthDay = request.getParameter("birthDay");
		String sex = request.getParameter("sex");
		Student stu = new Student(stuNum, stuName, birthDay, Integer.valueOf(sex));
		JSONObject json = new JSONObject();
		try {
			studentService.addStudent(stu);
			json.put("status", Constants.SUCCESS);
			renderJson(response, json);
		} catch (ObjectReadWriteException e){
			e.printStackTrace();
			//request.getRequestDispatcher("/WEB-INF/view/write_error.jsp").forward(request, response);
			//return ;
			json.put("status", Constants.DB_ERROR);
			renderJson(response, json);
		}
		
		System.out.println(stu);
		
    	
    }
    
    public void viewStudentInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// TODO
    	String s_num = request.getParameter("stuNum");
    	JSONObject json = new JSONObject();
    	PrintWriter out = response.getWriter();
    	try {
    		Student stu = studentService.findStudentByNum(s_num);
    		System.out.println("++++++++++++++++++++++++++++++++" + stu);
    		if (stu == null) {
    			json.put("status", Constants.DB_ERROR);
    			renderJson(response, json);
    			System.out.println("stu 是 null");
    			return ;
    		}
    		String stuNum = stu.getStuNum().toString();
    		String stuName = stu.getStuName();
    		String stuBirthday = stu.getBirthDay();
    		String stuSex = stu.getSex().toString();
    		json.put("stuNum", stuNum);
    		json.put("stuName", stuName);
    		json.put("stuBirthday", stuBirthday);
    		json.put("stuSex", stuSex);
    		json.put("status", Constants.SUCCESS);
    		System.out.println(stu);
    		renderJson(response, json);
    	}catch (Exception e) {
    		e.printStackTrace();
    		json.put("status", Constants.DB_ERROR);
    		renderJson(response, json);
    	}
    	
    }
    
    private void renderJson(HttpServletResponse response, JSONObject json) throws IOException {
    	response.setContentType("application/json");
    	PrintWriter out = response.getWriter();
    	out.println(json.toJSONString());
    	out.flush();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.getRequestDispatcher("/WEB-INF/view/student_ajax.jsp").forward(request, response);
		String action = request.getParameter("action");
		try {
			Method actionMethod = this.getClass().getMethod(action, HttpServletRequest.class, HttpServletResponse.class);
			actionMethod.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//queryStudent(request, response);
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException{
		super.init();
		//因为Autowired在servlet中不能直接生效，所以需要在Spring框架中加入支持Autowire的方法
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, servletConfig.getServletContext());
	}

}
