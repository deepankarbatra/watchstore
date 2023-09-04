package com.nagarro.watchstore.exception;

import org.springframework.http.HttpStatus;

/**
 * The NotFoundException class represents an exception that is thrown when a
 * resource is not found.
 */
public class NotFoundException extends GenericException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(String fieldName, String message) {
		super(fieldName, HttpStatus.NOT_FOUND, message);
	}

}
