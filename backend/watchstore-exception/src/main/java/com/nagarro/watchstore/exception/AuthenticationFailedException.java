package com.nagarro.watchstore.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationFailedException extends GenericException{
	private static final long serialVersionUID = 1L;
	private static final HttpStatus httpStatusCode = HttpStatus.UNAUTHORIZED;
	
	public AuthenticationFailedException(String fieldName, String message) {
		super(fieldName, httpStatusCode, String.format("%s", message));
	}

}
