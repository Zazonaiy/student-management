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
import com.login.service.LoginService;


@WebServlet("/register")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		User user = new User(username, password);
		LoginService server = LoginService.getInstance();
		JSONObject json = server.addUser(user);
		if ((Boolean)json.get("isSuccess")) {
			session.setAttribute("secondaryReg", true);
			request.setAttribute("registSuccess", 0);
			//request.getRequestDispatcher("Register.jsp").forward(request, response);
			//System.out.println("!!!!");
			//return ;
		}else {
			session.setAttribute("secondaryReg", true);
			request.setAttribute("registSuccess", 1);
			System.out.println(request.getAttribute("registSuccess"));
			//request.getRequestDispatcher("Register.jsp").forward(request, response);
			//return ;
		}
		request.getRequestDispatcher("Register.jsp").forward(request, response);
		return;
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
