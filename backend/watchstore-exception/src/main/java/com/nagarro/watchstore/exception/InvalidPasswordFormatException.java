package com.nagarro.watchstore.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when an password has failed one of the constraints.
 * 
 */
public class InvalidPasswordFormatException extends GenericException{
	
	private static final long serialVersionUID = 1L;
	private static final HttpStatus httpStatusCode = HttpStatus.BAD_REQUEST;
	
	public InvalidPasswordFormatException(String fieldName, String message) {
		super(fieldName, httpStatusCode, String.format("%s", message));
	}
}
