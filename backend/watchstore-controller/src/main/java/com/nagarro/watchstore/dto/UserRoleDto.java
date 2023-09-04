package com.nagarro.watchstore.dto;
/***
 * This Dto is used to converting business UserRole entity for exchanging information over API.  
 *  
 * @author vishaldeswal
 */
public enum UserRoleDto {
	 /**
     * Represents the admin role.
     */
	ADMIN("admin"),
	/**
     * Represents the customer role.
     */
    CUSTOMER("customer");

    private final String userType;
    
    
    UserRoleDto(String userType) {
        this.userType = userType;
    }
    
    /**
     * Get the user type associated with the role.
     *
     * @return The user type.
     */
    public String getUserType() {
        return userType;
    }

}
