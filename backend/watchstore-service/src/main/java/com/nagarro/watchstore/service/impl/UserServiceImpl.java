package com.nagarro.watchstore.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nagarro.watchstore.constants.Constant;
import com.nagarro.watchstore.dao.UserRepository;
import com.nagarro.watchstore.entity.User;
import com.nagarro.watchstore.service.UserService;
import com.nagarro.watchstore.exception.UserAlreadyExistException;
import com.nagarro.watchstore.exception.UserNotFoundException;
/**
 * Implementation of the UserService interface that handles user-related operations.
 *@author vishal deswal
 *@version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * Constructs a new instance of UserServiceImpl with the UserRepository dependency.
     *
     * @param userRepository The UserRepository to be used for data access.
     */
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	@Override
	public Optional<User> getUserByID(String emailId) {
		Optional<User> userOptional= userRepository.findById(emailId);
		if(userOptional.isEmpty()) {
			logger.info(Constant.USER_NOT_FOUND+emailId);
			throw new UserNotFoundException("Email_Id", Constant.USER_NOT_FOUND+emailId);
		}
		return userOptional;
	}

	@Override
	public String addUser(User user) {
		Optional<User> userOptional = userRepository.findById(user.getEmailId());
		if (userOptional.isPresent()) {
			logger.info(Constant.USER_ALREADY_EXIST + user.getEmailId());
			throw new UserAlreadyExistException(Constant.USER_ALREADY_EXIST+userOptional.get().getEmailId(),"Email_Id");
		}
		Optional<User> savedUserOptional = Optional.of(userRepository.save(user));
		return savedUserOptional.get().getEmailId();
	}

	@Override
	public User findUserById(String emailId) {
		Optional<User> userOptional= userRepository.findById(emailId);
		if(userOptional.isEmpty()) {
			logger.info(Constant.USER_NOT_FOUND + emailId);
			throw new UserNotFoundException("Email_Id",Constant.USER_NOT_FOUND+emailId);
		}
		return userOptional.get();
	}	

}

