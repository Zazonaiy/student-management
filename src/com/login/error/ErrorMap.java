package com.login.error;

import java.util.HashMap;
import java.util.Map;

public class ErrorMap {
	private Map<Integer, String> errorMap;
	
	public ErrorMap() {
		this.errorMap = new HashMap<>();
		this.errorMap.put(-1, "��̨�������ʹ���");
		this.errorMap.put(3, "��̨���ݶ�ʧ");
	}
	
	public String getError(Integer errorCode) {
		return errorMap.get(errorCode);
	}
	
	public void setError(Integer errorCode, String error) {
		errorMap.put(errorCode, error);
	}
	
	
}
