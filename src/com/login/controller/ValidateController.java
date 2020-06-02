package com.login.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;

import com.login.bean.User;
import com.login.service.LoginService;


@WebServlet("/validate")
public class ValidateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		String username = request.getParameter("username").trim();
		System.out.println(username);
		String password = request.getParameter("password").trim();
		User user = new User(username, password);
		LoginService server = LoginService.getInstance();
		JSONObject json = server.validateUser(user);
		if ((Boolean) json.getBoolean("isValidateSuccess")) {
			//登录成功
			session.setAttribute("thisUser", user);
			request.setAttribute("isLoginSuccess", 0);
			request.getRequestDispatcher("/WEB-INF/view/HomePage.jsp").forward(request, response);
			return ;
			
		}else {
			if (json.getString("information").equals("userNotExist")) {
				//账号不存在
				request.setAttribute("isLoginSuccess", 1);
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return ;
			}else if (json.getString("information").equals("SystemError")) {
				request.setAttribute("isLoginSuccess", -1);
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return ;
			}
			else if (json.getString("information").equals("passwordError")) {
				//账号密码错误
				request.setAttribute("isLoginSuccess", 2);;
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return ;
			}
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
