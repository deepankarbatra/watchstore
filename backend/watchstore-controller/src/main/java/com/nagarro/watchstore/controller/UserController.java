package com.nagarro.watchstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nagarro.watchstore.dto.UserDto;
import com.nagarro.watchstore.dto.UserProfileDto;
import com.nagarro.watchstore.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller interface for managing user-related endpoints.
 * 
 * @author vishal deswal
 * @version 1.0
 */
@CrossOrigin
@Tag(name = "User API")
public interface UserController {

	/**
	 * Retrieve a user profile by their email ID.
	 *
	 * @param userEmailID The email ID of the user.
	 * @return ResponseEntity containing the user profile if found, or an
	 *         appropriate error response if not found.
	 */
	@GetMapping("/users/{id}")
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@Operation(summary = "Fetch user details by user email id.")
	public ResponseEntity<UserProfileDto> getUserById(@PathVariable(value = "id") final String userEmailID);
	
	/**
	 * Register a new user.
	 *
	 * @param userDto The RegisterUserDto object containing the user details to be
	 *                registered.
	 * @return A ResponseEntity with the response message indicating the success of
	 *         the registration.
	 */
	@PostMapping("/users")
	@Operation(summary = "Register new user.")
	public ResponseEntity<ApiResponse> register(@RequestBody final UserDto userDto);

}
