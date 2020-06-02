package com.login.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 设置全局http请求编码的filter
 * @author 杨伟豪
 *
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		//验证用户 session
		HttpServletRequest req = (HttpServletRequest) request;
		String url = req.getRequestURI().toString();
		System.out.println(url);
		if (url.endsWith("student_ajax") || url.endsWith("student_man") || url.endsWith("home")) {
			HttpSession session = req.getSession();
			if (session.getAttribute("thisUser") == null) {
				((HttpServletResponse)response).sendRedirect("login-controller");
				return ;
			}
		}
		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
