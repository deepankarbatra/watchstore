package com.nagarro.watchstore.extractor;

import com.nagarro.watchstore.enums.UserRole;
/**
 * The UserInfo class represents the extracted user information.
 * It holds details such as the user's email ID and role.
 * 
 * This class is typically used in conjunction with implementations of the {@link UserDetailExtractor} interface,
 * where the user details are extracted from the authentication process and encapsulated in an instance of this class.
 * 
 * The email ID and user role are accessible through getter and setter methods provided by this class.
 * 
 * @see UserDetailExtractor
 * @see UserRole
 * 
 * @author vishaldeswal
 */
public class UserInfo {
	String emailId;
	UserRole userRole;
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
}
