package com.studentmam.constant;

/**
 * 常量定义区域
 * @author 杨伟豪
 *
 */
public interface Constants {
	public static final int DB_ERROR = 100;	//数据库错误
	public static final int SUCCESS = 0;	//正常
	
	public static final int PASS_ERROR = 2; //登录密码错误
	public static final int NULL_USER = 1;  //登录账户不存在
	public static final int INIT_STATE = -1;	//初始态
	public static final int DO_REGIST = 4;		//进行注册
	public static final int REGIST_ERROR = 3;	//注册用户已被注册
	
}
