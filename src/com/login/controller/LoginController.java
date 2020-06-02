package com.login.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.alibaba.fastjson.JSONObject;
import com.login.bean.User;
import com.login.service.LoginService;
import com.studentmam.constant.Constants;

/**
 * Servlet implementation class loginSlet
 */
@Controller
@Scope("prototype")
@WebServlet(urlPatterns = "/login-controller", loadOnStartup = 1)
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private LoginService service;

	public void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		System.out.println(username + " xx " + password);
		User user = new User(username, password);
		System.out.println(user.getUserName()+"   adadadad");
		HttpSession session = request.getSession();
		JSONObject json = service.validateUser(user);
		JSONObject result = new JSONObject();
		if ((Boolean) json.getBoolean("isValidateSuccess")) {
			//登录成功
			result.put("username", user.getUserName());
			result.put("password", user.getPassWord());
			result.put("status", Constants.SUCCESS);
			session.setAttribute("thisUser", user);
			renderJson(response, result);
			//request.setAttribute("isLoginSuccess", 0);
			//request.getRequestDispatcher("/WEB-INF/view/HomePage.jsp").forward(request, response);
			return ;
			
		}else {
			if (json.getString("information").equals("userNotExist")) {
				//账号不存在
				//request.setAttribute("isLoginSuccess", 1);
				//request.getRequestDispatcher("login.jsp").forward(request, response);
				result.put("status", Constants.NULL_USER);
				renderJson(response, result);
				return ;
			}else if (json.getString("information").equals("SystemError")) {
				//数据库错误
				//request.setAttribute("isLoginSuccess", -1);
				//request.getRequestDispatcher("login.jsp").forward(request, response);
				result.put("status", Constants.DB_ERROR);
				renderJson(response, result);
				return ;
			}
			else if (json.getString("information").equals("passwordError")) {
				//账号密码错误
				//request.setAttribute("isLoginSuccess", 2);;
				//request.getRequestDispatcher("login.jsp").forward(request, response);
				result.put("status", Constants.PASS_ERROR);
				renderJson(response, result);
				return ;
			}
		}
	}

	public void doRegiste(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int status = Integer.valueOf(request.getParameter("registeStatus"));
		//如果是进入注册窗口请求，则不进行注册逻辑，而进行转发
		if (Constants.INIT_STATE == status) {
			request.getRequestDispatcher("/WEB-INF/view/Register.jsp").forward(request, response);
			return ;
		}
		
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		User user = new User(username, password);
		JSONObject json = service.addUser(user);
		JSONObject result = new JSONObject();
		if ((Boolean)json.get("isSuccess")) {
			result.put("status", Constants.SUCCESS);
			renderJson(response, result);
			return ;
		}else {
			result.put("status", Constants.REGIST_ERROR);
			renderJson(response, result);
			return ;
		}
		
		
	}

	public void doUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int status = Integer.valueOf(request.getParameter("updateStatus"));
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx   " + status);
		//如果是进入修改窗口请求，则不进行注册逻辑，而进行转发
		if (Constants.INIT_STATE == status) {
			
			request.getRequestDispatcher("/WEB-INF/view/updateUser.jsp").forward(request, response);
			return ;
		}
		
		System.out.println("------------------------");
		String username = request.getParameter("username").trim();
		String oldpassword = request.getParameter("oldpassword").trim();
		String newpassword = request.getParameter("newpassword").trim();
		System.out.println("old " + oldpassword);
		System.out.println("new " + newpassword);
		User user = new User(username, oldpassword);
		JSONObject json = service.validateUser(user);
		JSONObject result = new JSONObject();
		
		if ((Boolean) json.getBoolean("isValidateSuccess")) {
			// 旧密码登录成功
			user.setPassWord(newpassword);;
			System.out.println("用户认证成功，可以修改密码");
		}else {
			if (json.getString("information").equals("userNotExist")) {
				//账号不存在
				result.put("status", Constants.NULL_USER);
				renderJson(response, result);
				return ;
			}else if (json.getString("information").equals("SystemError")) {
				//后台数据库错误
				result.put("status", Constants.DB_ERROR);
				renderJson(response, result);
				return ;
			}else if (json.getString("information").equals("passwordError")) {
				//账号密码错误
				result.put("status", Constants.PASS_ERROR);
				renderJson(response, result);
				return ;
			}
		}
		
		JSONObject json2 = service.updateUser(user);
		if ((Boolean) json2.get("isSuccess")) {
			//密码修改成功
			result.put("status", Constants.SUCCESS);
			System.out.println("aaaaaaaaaaaaa  ");
			renderJson(response, result);
			return ;
		}else {
			//账号不存在
			result.put("status", Constants.NULL_USER);
			renderJson(response, result);
			return ;
		}
		
	}
	
	private void renderJson(HttpServletResponse response, JSONObject json) throws IOException {
    	response.setContentType("application/json");
    	PrintWriter out = response.getWriter();
    	out.println(json.toJSONString());
    	out.flush();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if ("doLogin".equals(action)) {
			doLogin(request, response);
			return;
		} else if ("doRegiste".equals(action)) {
			doRegiste(request, response);
			return;
		} else if ("doUpdate".equals(action)) {
			doUpdate(request, response);
			return;
		}
		request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
		return ;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException{
		super.init();
		//因为Autowired在servlet中不能直接生效，所以需要在Spring框架中加入支持Autowire的方法
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, servletConfig.getServletContext());
	}
}
