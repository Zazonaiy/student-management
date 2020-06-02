package com.studentmam.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class StudentListener
 *
 */
@WebListener
public class StudentListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce)  { 
        System.out.println("服务器已启动...");
    }
	
    public void contextDestroyed(ServletContextEvent sce)  { 
    	System.out.println("服务器已停止...");
    }

	
    
	
}
