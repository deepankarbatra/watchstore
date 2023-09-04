package com.nagarro.watchstore.exception;

import org.springframework.http.HttpStatus;

public class NotInStockException extends GenericException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NotInStockException(String fieldName, String message) {
		super(fieldName, HttpStatus.NOT_FOUND, message);
	}

}
