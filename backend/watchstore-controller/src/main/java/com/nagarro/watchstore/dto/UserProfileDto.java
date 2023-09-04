package com.nagarro.watchstore.dto;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) class for representing a user profile.
 * @author vishal deswal
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class UserProfileDto {
	private String emailId;
	private String name;
	private UserRoleDto role;
	private String phoneNumber;
}
