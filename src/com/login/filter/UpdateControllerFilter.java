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


@WebFilter("/update")
public class UpdateControllerFilter implements Filter {


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		doFilter((HttpServletRequest)request, (HttpServletResponse) response);
		chain.doFilter(request, response);
	}
	protected void doFilter(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		if (((Boolean)session.getAttribute("accessLoginPage")) == null){
			response.sendRedirect("login.jsp");
			return ;
		}
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
