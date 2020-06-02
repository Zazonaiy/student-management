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


@WebServlet("/upd")
public class UpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username").trim();
		String oldpassword = request.getParameter("oldpassword").trim();
		String newpassword = request.getParameter("password").trim();
		System.out.println("old " + oldpassword);
		System.out.println("new " + newpassword);
		User user = new User(username, oldpassword);
		LoginService server = LoginService.getInstance();
		JSONObject json1 = server.validateUser(user);
		if ((Boolean) json1.getBoolean("isValidateSuccess")) {
			// 旧密码登录成功
			user.setPassWord(newpassword);;
			System.out.println("һ��");
		}else {
			if (json1.getString("information").equals("userNotExist")) {
				//账号不存在
				request.setAttribute("updateSuccess", 1);
				request.getRequestDispatcher("updateUser.jsp").forward(request, response);
				return ;
			}else if (json1.getString("information").equals("SystemError")) {
				request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);
				return ;
			}else if (json1.getString("information").equals("passwordError")) {
				//账号密码错误
				request.setAttribute("updateSuccess", 2);
				request.getRequestDispatcher("updateUser.jsp").forward(request, response);
				return ;
			}
		}
		JSONObject json2 = server.updateUser(user);
		if ((Boolean) json2.get("isSuccess")) {
			//密码修改成功
			request.setAttribute("updateSuccess", 0);
			request.getRequestDispatcher("updateUser.jsp").forward(request, response);
			return ;
		}else {
			//账号不存在
			request.setAttribute("updateSuccess", 1);
			request.getRequestDispatcher("updateUser.jsp").forward(request, response);
			return ;
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
