package com.nagarro.watchstore.util;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nagarro.watchstore.constants.Constant;
import com.nagarro.watchstore.exception.BadRequestException;

/**
 * CommonValidator provides utility methods for validating common data fields.
 * @author vishaldeswal
 * @version 1.0
 */
public class CommonValidator {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonValidator.class);
	/**
	 * Validates whether a phone number is in a valid format.
	 * 
	 * @param phoneNumber the phone number to validate
	 * @return true if the phone number is not valid, false otherwise
	 * @throws BadRequestException if the phone number is empty or null
	 */
	public static boolean isPhoneNotValid(String phoneNumber) {
		
		if(isFieldEmptyOrNull(phoneNumber)) {
			logger.info("Phone number is either null or empty");
			throw new BadRequestException("Phone Number:", "Phone number is required.");
		}
		
		final String regexPattern = "^[0-9]{10}$";
		boolean result = Pattern.compile(regexPattern).matcher(phoneNumber).matches() == false;
		
		if(result) {
			logger.info("Phone number format is invalid");
			throw new BadRequestException("Phone Number:", "Invalid phone number.");
		}
		
		return result;
	}

	/**
	 * Validates whether a password is in a valid format.
	 * 
	 * @param password the password to validate
	 * @return true if the password is not valid, false otherwise
	 * @throws BadRequestException if the password is empty or null, or if the format is invalid
	 */
	public static boolean isPasswordNotValid(String password) {
		
		if(isFieldEmptyOrNull(password)) {
			logger.info("Password is either null or empty");
			throw new BadRequestException("password:", "Password is required.");
		}
		
		final String regexPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)"
									+ "(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,24}$";
		boolean result = Pattern.compile(regexPattern).matcher(password).matches() == false;
		
		if(result) {
			logger.info("Password format is invalid: ",password);
			throw new BadRequestException("password:", "Invalid password format.");
		}
		return result;
	}

	/**
	 * Validates whether a name is in a valid format.
	 * 
	 * @param name the name to validate
	 * @return true if the name is not valid, false otherwise
	 * @throws BadRequestException if the name is empty or null, or if the format is invalid
	 */
	public static boolean isNameNotValid(String name) {
		

		if(isFieldEmptyOrNull(name)) {
			logger.info("Name is either null or empty");
			throw new BadRequestException("name", "Name is required.");
		}
		
		final String regexPattern = "^[A-Za-z\\s]+$";
		boolean result = Pattern.compile(regexPattern).matcher(name).matches() == false;
		if(result) {
			logger.info("Name format is invalid: ",name);
			throw new BadRequestException("name", "Only alphabets are allowed.");
		}
		return result;
	}

	/**
	 * Validates whether an email ID is in a valid format.
	 * 
	 * @param emailId the email ID to validate
	 * @return true if the email ID is not valid, false otherwise
	 * @throws BadRequestException if the email ID is empty or null, or if the format is invalid
	 */
	public static boolean isEmailNotValid(String emailId) {
		
		if(isFieldEmptyOrNull(emailId)) {
			logger.info("EmailId is either null or empty");
			throw new BadRequestException("emailId", "Email ID is required");
		}
		
		final String regexPattern = "^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$";
		boolean result = Pattern.compile(regexPattern).matcher(emailId).matches() == false;
		if(result) {
			logger.info("EmailId format is invalid: ",emailId);
			throw new BadRequestException("emailId", "Invalid email Id format.");
		}
		return result;
	}
	

	/**
	 * Checks whether a field value is empty or null.
	 * 
	 * @param fieldValue the field value to check
	 * @return true if the field value is empty or null, false otherwise
	 */
	public static boolean isFieldEmptyOrNull(String fieldValue) {
		return fieldValue == null || fieldValue.isEmpty();
	}
}
