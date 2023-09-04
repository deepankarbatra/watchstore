package com.nagarro.watchstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nagarro.watchstore.jwtservice.JwtRequest;
import com.nagarro.watchstore.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * This interface represents the Authentication API endpoints for the watch store.
 * It provides methods for user authentication and obtaining JWT token.
 * 
 * @author vishaldeswal
 *
 */
@CrossOrigin
@Tag(name = "Authentication API")
public interface AuthenticationController {
	
	/**
	 * Authenticates the user and generates a JWT token.
	 * 
	 * @param jwtRequest the request object containing user credentials
	 * @return the response entity containing the JWT token
	 */
	@PostMapping("/login")
	public ResponseEntity<ApiResponse> authenticateAndGetToken(@RequestBody final JwtRequest jwtRequest);
	
}
