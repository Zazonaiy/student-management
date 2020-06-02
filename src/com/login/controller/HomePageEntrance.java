package com.login.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;

import com.login.bean.User;


@WebServlet("/home")
public class HomePageEntrance extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("thisUser");
		JSONObject json = new JSONObject();
		json.put("username", user.getUserName());
		renderJson(response, json);
		return ;
		
	}
	
	private void renderJson(HttpServletResponse response, JSONObject json) throws IOException {
    	response.setContentType("application/json");
    	PrintWriter out = response.getWriter();
    	out.println(json.toJSONString());
    	out.flush();
    }
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if ("getUserInfo".equals(action)) {
			getUserInfo(request, response);
			return ;
		}
		
		request.getRequestDispatcher("/WEB-INF/view/HomePage.jsp").forward(request, response);
		return ;
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
