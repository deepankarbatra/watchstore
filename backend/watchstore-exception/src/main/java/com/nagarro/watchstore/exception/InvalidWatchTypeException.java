package com.nagarro.watchstore.exception;

import org.springframework.http.HttpStatus;

public class InvalidWatchTypeException extends GenericException {

	private static final long serialVersionUID = 1L;
	
	public InvalidWatchTypeException(String fieldName,HttpStatus httpStatusCode, String message) {
		super(message,httpStatusCode, fieldName);
	}

}
