package com.nagarro.watchstore.validator;


import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nagarro.watchstore.constants.Constant;
import com.nagarro.watchstore.exception.InvalidEmailArgumentException;
import com.nagarro.watchstore.extractor.UserDetailExtractor;
import com.nagarro.watchstore.util.CommonValidator;

/**
 * The EmailValidator class is responsible for validating email addresses.
 * It implements the Predicate interface, allowing it to be used as a function that tests whether a given email is valid.
 * 
 * This class validates an email by checking if it is a valid email format using the CommonValidator class.
 * Additionally, it verifies that the email matches the currently authenticated user's email retrieved through the UserDetailExtractor.
 * 
 * If the email is not valid or does not match the authenticated user's email, an InvalidEmailArgumentException is thrown.
 * 
 * This class is a Spring component and is typically used within the application for email validation purposes.
 * 
 * @author vishaldeswal
 */

@Component
public class EmailValidator implements Predicate<String> {
	
	private final UserDetailExtractor detailExtractor;
	private static final Logger logger = LoggerFactory.getLogger(EmailValidator.class);
	

	/**
	 * Constructs an EmailValidator with the provided UserDetailExtractor dependency.
	 * 
	 * @param detailExtractor the UserDetailExtractor used to retrieve the currently authenticated user's email.
	 */
	@Autowired
	public EmailValidator(UserDetailExtractor detailExtractor) {
		super();
		this.detailExtractor = detailExtractor;
	}
	
	/**
	 * Tests whether the given email is valid and matches the authenticated user's email.
	 * 
	 * @param email the email address to be validated.
	 * @return true if the email is valid and matches the authenticated user's email, false otherwise.
	 * @throws InvalidEmailArgumentException if the email is not valid or does not match the authenticated user's email.
	 */
	@Override
	public boolean test(String email) {
		
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
