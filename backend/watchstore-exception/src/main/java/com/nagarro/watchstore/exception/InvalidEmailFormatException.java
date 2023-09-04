package com.nagarro.watchstore.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when an email address has an invalid format.
 */
public class InvalidEmailFormatException extends GenericException{
	private static final long serialVersionUID = 1L;
	private static final HttpStatus httpStatusCode = HttpStatus.BAD_REQUEST;
	/**
	 * Constructs a new InvalidEmailFormatException with the specified field name and message.
	 *
	 * @param fieldName The name of the field associated with the exception.
	 * @param message   The error message.
	 */
	public InvalidEmailFormatException(String fieldName, String message) {
		super(fieldName, httpStatusCode, String.format("%s", message));
	}
}
