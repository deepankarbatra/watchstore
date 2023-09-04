package com.nagarro.watchstore.extractor.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.nagarro.watchstore.constants.Constant;
import com.nagarro.watchstore.enums.UserRole;
import com.nagarro.watchstore.exception.AuthenticationFailedException;
import com.nagarro.watchstore.extractor.UserDetailExtractor;
import com.nagarro.watchstore.extractor.UserInfo;

/**
 * The UserDetailExtractorImpl class is responsible for extracting the user details from the authentication object.
 * It implements the UserDetailExtractor interface.
 * 
 * This implementation retrieves the principal details of the authenticated user from the SecurityContextHolder.
 * It assumes that the principal object in the authentication context is of type UserDetails.
 * If the principal object is not of the expected type, an exception will be thrown.
 * 
 * This class is annotated with @Component to be automatically detected and registered as a Spring bean.
 * 
 * @author vishaldeswal
 * @see UserDetailExtractor
 */
@Component
public class UserDetailExtractorImpl implements UserDetailExtractor {

	@Override
	public UserInfo getUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			 
			UserDetails user = (UserDetails) authentication.getPrincipal();
			UserRole role=UserRole.fromString(authentication.getAuthorities().stream().findFirst().get().getAuthority()).get();
			 
			UserInfo userInfo = new UserInfo();
			userInfo.setEmailId(user.getUsername());
			userInfo.setUserRole(role);
		    return userInfo;
		 } 
		 
		 else {
		     throw new AuthenticationFailedException("UserJwtToken: ", Constant.AUTHENTICATION_FAILED_TOKEN);
		    
		 }
	}

	

}
