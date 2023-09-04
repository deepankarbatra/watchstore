package com.nagarro.watchstore.exception;

import org.springframework.http.HttpStatus;

public class AlreadyInStockException extends GenericException  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AlreadyInStockException(String fieldName, String message) {
		super(fieldName, HttpStatus.CONFLICT, message);
	}

}
