package com.nagarro.watchstore.controller.impl;

import java.util.function.Function;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.watchstore.constants.Constant;
import com.nagarro.watchstore.controller.UserController;
import com.nagarro.watchstore.dto.UserDto;
import com.nagarro.watchstore.dto.UserProfileDto;
import com.nagarro.watchstore.entity.User;
import com.nagarro.watchstore.exception.InvalidEmailArgumentException;
import com.nagarro.watchstore.exception.InvalidEmailFormatException;
import com.nagarro.watchstore.extractor.UserDetailExtractor;
import com.nagarro.watchstore.extractor.UserInfo;
import com.nagarro.watchstore.response.ApiResponse;
import com.nagarro.watchstore.service.UserService;
import com.nagarro.watchstore.util.CommonValidator;
import com.nagarro.watchstore.util.EmailValidator;


/**
 * Implementation of the UserController interface that handles user-related
 * endpoints.
 * 
 * @author vishal deswal
 * @version 1.0
 */
@RestController
public class UserControllerImpl implements UserController {

	private final UserService userService;
	private final Function<User, UserProfileDto> userProfileEntityTransformer;
	private final Predicate<UserDto> registerUserDtoValidator;
	private final Predicate<String> emailValidator;
	private final Function<UserDto, User> registerUserDtoTransformer;
	private static final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

	/**
	 * Constructs a new instance of UserControllerImpl with the UserService
	 * dependency.
	 *
	 * @param userServiceImpl The UserService implementation to be used.
	 */
	@Autowired
	public UserControllerImpl(final UserService userService,
			final Function<User, UserProfileDto> userProfileEntityTransformer,
			final Predicate<UserDto> registerUserDtoValidator,
			final Predicate<String> emailValidator, 
			final Function<UserDto, User> registerUserDtoTransformer) {
		super();
		this.userService = userService;
		this.userProfileEntityTransformer = userProfileEntityTransformer;
		this.registerUserDtoValidator = registerUserDtoValidator;
		this.emailValidator= emailValidator;
		this.registerUserDtoTransformer = registerUserDtoTransformer;
		
	}

	@Override
	public ResponseEntity<ApiResponse> register(final UserDto userDto) {
		this.registerUserDtoValidator.test(userDto);
		User user = this.registerUserDtoTransformer.apply(userDto);
		logger.info(Constant.REQUEST_TO_REGISTER, user.getEmailId());
		String savedEmailId = userService.addUser(user);
		logger.info(Constant.RESPONSE_TO_REGISTER, user.getEmailId());
		ApiResponse response = new ApiResponse(Constant.USER_REGISTER_SUCCESSFULLY + savedEmailId);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}



	@Override
	public ResponseEntity<UserProfileDto> getUserById(String userEmailID) {
		this.emailValidator.test(userEmailID);
		User user = userService.findUserById(userEmailID);
		UserProfileDto userProfileDto = this.userProfileEntityTransformer.apply(user);
		logger.info(Constant.RESPONSE_FETCH_USER, userEmailID);
		return new ResponseEntity<>(userProfileDto, HttpStatus.OK);
	}
}
