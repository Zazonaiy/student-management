package com.studentmam.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ajax")
public class AjaxSlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.setContentType("text/html; encoding=utf-8");   //ajax不用设置
		String sNum = request.getParameter("s_num");
		String sName = request.getParameter("s_name");
		PrintWriter out = response.getWriter();
		
		try {
			Thread.sleep(3000);
		}catch (Exception e) {
			e.printStackTrace();
		}
		out.println("这是一段来自服务器的文字, s_num=" + sNum + " s_name=" + sName);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
