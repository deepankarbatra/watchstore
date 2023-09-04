package com.nagarro.watchstore.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import com.nagarro.watchstore.constants.Constant;
import com.nagarro.watchstore.exception.InvalidEmailArgumentException;
import com.nagarro.watchstore.extractor.UserDetailExtractor;
/**
 * This class provides utility methods for validating email addresses.
 * It validates email addresses based on the user's email ID extracted from the user details.
 * If the email is not valid or does not match the user's email ID, an exception is thrown.
 *
 *@author vishaldeswal
 */
public class EmailValidator {
	
	private static UserDetailExtractor detailExtractor;
	private static final Logger logger = LoggerFactory.getLogger(EmailValidator.class);
	
	@Autowired
	public EmailValidator(UserDetailExtractor detailExtractor) {
		super();
		detailExtractor = detailExtractor;
	}


	/**
	 * Validates the given email address.
	 * 
	 * @param email the email address to validate
	 * @return true if the email is valid and matches the user's email ID, false otherwise
	 * @throws InvalidEmailArgumentException if the email is not valid or does not match the user's email ID
	 */
	public static boolean validate(String email) {
		String userId = detailExtractor.getUserInfo().getEmailId();
		
		boolean result = !(CommonValidator.isEmailNotValid(email)) && userId.equals(email);
		if(! CommonValidator.isEmailNotValid(email)) {
			if(!userId.equals(email)) {
				logger.info("Email argument passed is invalid.",email);
				throw new InvalidEmailArgumentException("Email Id", Constant.INVALID_EMAIL_ARGUMENT);
			}
		}
		return result;
	}

}
