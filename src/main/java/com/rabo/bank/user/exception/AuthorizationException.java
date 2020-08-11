package com.rabo.bank.user.exception;

public class AuthorizationException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String message;
	public AuthorizationException() {
		this("Authorization Failed");
	}

	public AuthorizationException(String message) {
		super();
		this.message = message;
	}
	@Override
	public String getMessage() {
		return message;
	}
}
