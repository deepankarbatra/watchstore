package com.nagarro.watchstore.extractor;

import org.springframework.security.core.userdetails.UserDetails;

import com.nagarro.watchstore.enums.UserRole;

/**
 * The UserDetailExtractor interface provides a contract for extracting user details from the authentication object.
 * Implementations of this interface are responsible for retrieving the principal details of the authenticated user.
 * 
 * @author vishaldeswal
 */
public interface UserDetailExtractor {
	/**
	 * Retrieves the user information from the authentication object.
	 * 
	 * @return an instance of {@link UserInfo} containing the extracted user details.
	 */
	UserInfo getUserInfo();
}
