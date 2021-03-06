package com.rabo.bank.user.exception;

public class UserNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String message;
	public UserNotFoundException() {
		this("User Not Found");
	}

	public UserNotFoundException(String message) {
		super();
		this.message = message;
	}
	@Override
	public String getMessage() {
		return message;
	}

}
