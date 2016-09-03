package com.telnetar.exceptions;

public class TelnetarException extends Exception {

	private static final long serialVersionUID = 1L;

	private String msg;

	public TelnetarException(String msg){
		this.msg = msg;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
