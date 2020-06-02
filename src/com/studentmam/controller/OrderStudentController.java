package com.studentmam.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/orderstu")
public class OrderStudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public OrderStudentController() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		String param = request.getParameter("orderWay");
		String[] args = param.split("-");
		//System.out.println(args[2] + " " + args[3]);
		String[] orderBy = {"s_" + args[2], args[3]};
		request.setAttribute("orderBy", orderBy);
		request.getRequestDispatcher("student").forward(request, response);
		return ;
		*/
		
		String[] orderBy = {request.getParameter("orderBy"), request.getParameter("orderWay")};

		Integer[] pagging = {Integer.valueOf(request.getParameter("pageNo")), Integer.valueOf(request.getParameter("pageSize"))};
		System.out.println(orderBy[0] + " " + orderBy[1]);
		System.out.println(pagging[0] + " " + pagging[1]);
		request.setAttribute("orderBy", orderBy);
		request.setAttribute("pagging", pagging);
		request.getRequestDispatcher("student").forward(request, response);
		return;
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
