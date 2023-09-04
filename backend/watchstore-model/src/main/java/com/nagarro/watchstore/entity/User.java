package com.nagarro.watchstore.entity;

import com.nagarro.watchstore.enums.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a user in the watch store application.
 * 
 * @author vishal deswal
 * @version 1.0
 */

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	/**
	 * The email ID of the user.
	 */
	@Id
	private String emailId;
	/**
	 * The name of the user.
	 */
	private String name;
	/**
	 * The password of the user.
	 */
	private String password;

	/**
	 * The role of the user.
	 */
	@Enumerated(EnumType.STRING)
	private UserRole role;

	/**
	 * The phone number of the user.
	 */
	private String phoneNumber;

}
