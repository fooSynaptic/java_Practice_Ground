package com.itranswarp.learnjava;



public class UserNotFoundException extends BaseException{
	public UserNotFoundException() {
		super();
	}
	
	public UserNotFoundException(String cause) {
		super(cause);
	}
}
