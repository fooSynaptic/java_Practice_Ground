package com.itranswarp.learnjava;

public class LoginFailedException extends BaseException{
	public LoginFailedException() {
		super();
	}
	
	public LoginFailedException(String cause) {
		super(cause);
	}
}