package com.nagarro.watchstore.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception class to represent an invalid order status.
 * Extends the GenericException class.
 */
public class InvalidOrderStatusException extends GenericException {

	private static final long serialVersionUID = 1095151889270537555L;

	public InvalidOrderStatusException(String fieldName, String message)
	{
		super(fieldName,HttpStatus.BAD_REQUEST,message);
	}
}
