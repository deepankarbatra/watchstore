package com.nagarro.watchstore.exception;

import org.springframework.http.HttpStatus;
/**
 * Exception thrown when a user already exists in the system.
 * @author vishal deswal
 * @version 1.0
 */
public class UserAlreadyExistException extends GenericException {
	
	private static final long serialVersionUID = 1L;
	private static final HttpStatus httpStatusCode= HttpStatus.CONFLICT;

	/**
	 * Constructs a new UserAlreadyExistException with the specified field name and message.
	 *
	 * @param fieldName The name of the field that caused the exception.
	 * @param message   The detailed message describing the exception.
	 */
	public UserAlreadyExistException(final String fieldName,final String message) {
		super(fieldName, httpStatusCode ,String.format("%s", message));
	}

}
