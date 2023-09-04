package com.nagarro.watchstore.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.watchstore.constants.Constant;
import com.nagarro.watchstore.controller.AuthenticationController;
import com.nagarro.watchstore.entity.User;
import com.nagarro.watchstore.exception.AuthenticationFailedException;
import com.nagarro.watchstore.exception.InvalidEmailFormatException;
import com.nagarro.watchstore.exception.InvalidPasswordFormatException;
import com.nagarro.watchstore.jwtservice.JwtRequest;
import com.nagarro.watchstore.jwtservice.JwtService;
import com.nagarro.watchstore.response.ApiResponse;
import com.nagarro.watchstore.service.UserService;
import com.nagarro.watchstore.util.CommonValidator;


/**
 * The implementation class of the AuthenticationController interface. This class provides
 * the actual implementation for authenticate user.
 * @author vishaldeswal
 *
 */
@RestController
public class AuthenticationControllerImpl implements AuthenticationController {

	private final UserService userService;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	

	@Autowired
	public AuthenticationControllerImpl(final UserService userService,
										final JwtService jwtService,
										final AuthenticationManager authenticationManager) {
		super();
		this.userService = userService;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		
	}

	@Override
	public ResponseEntity<ApiResponse> authenticateAndGetToken(final JwtRequest jwtRequest) {
		Authentication authenticate;
		if (CommonValidator.isEmailNotValid(jwtRequest.getEmail())) {
			throw new InvalidEmailFormatException("Email Id", Constant.INVALID_EMAIL_FORMAT + jwtRequest.getEmail());
		}

		else if (CommonValidator.isPasswordNotValid(jwtRequest.getPassword())) {
			throw new InvalidPasswordFormatException("Password",
					Constant.INVALID_PASSWORD_FORMAT + jwtRequest.getPassword());
		}

		try {
			authenticate = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));
		} catch (AuthenticationException e) {
			
			User user = userService.findUserById(jwtRequest.getEmail()); 
			throw new AuthenticationFailedException("Password", Constant.AUTHENTICATION_FAILED);
		}

		if (authenticate.isAuthenticated()) {

			String jwtToken = jwtService.generateToken(jwtRequest);
			ApiResponse apiResponse = new ApiResponse(jwtToken);

			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);

		}
		return null;
	}

	

}
