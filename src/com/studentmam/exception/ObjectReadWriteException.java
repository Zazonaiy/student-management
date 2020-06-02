package com.studentmam.exception;

public class ObjectReadWriteException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public ObjectReadWriteException() {
		super();
	}

	@Override
	public String getMessage() {
		return "文件读取或写入错误";
	}
}
