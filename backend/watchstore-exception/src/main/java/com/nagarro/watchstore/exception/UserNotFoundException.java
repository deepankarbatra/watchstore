package com.nagarro.watchstore.exception;

/**
 * Exception thrown when a user is not found in the system.
 * @author vishal deswal
 * @version 1.0
 */
public class UserNotFoundException extends NotFoundException{
	
	private static final long serialVersionUID = 1L;
//	private static final HttpStatus httpStatusCode= HttpStatus.NOT_FOUND;

	/**
	 * Constructs a new UserNotFoundException with the specified field name and message.
	 *
	 * @param fieldName The name of the field that caused the exception.
	 * @param message   The detailed message describing the exception.
	 */
	public UserNotFoundException(final String fieldName,final String message) {
//		super(fieldName, httpStatusCode ,String.format("%s", message));
		super(fieldName,String.format("%s", message));

	}

}
