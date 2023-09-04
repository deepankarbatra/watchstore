package com.nagarro.watchstore.exception;

import org.springframework.http.HttpStatus;

/**
 * The BadRequestException class represents an exception that is thrown when a
 * bad request occurs.
 */
public class BadRequestException extends GenericException {

	private static final long serialVersionUID = -4308501396134830301L;

	public BadRequestException(String fieldName, String message) {
		super(fieldName, HttpStatus.BAD_REQUEST, message);
	}
}
