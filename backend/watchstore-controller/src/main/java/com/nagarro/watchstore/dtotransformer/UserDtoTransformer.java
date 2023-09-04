package com.nagarro.watchstore.dtotransformer;

import java.util.function.Function;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nagarro.watchstore.dto.UserDto;
import com.nagarro.watchstore.entity.User;
import com.nagarro.watchstore.enums.UserRole;

/**
 * Transformer class to convert a RegisterUserDto to a User entity.
 * @author vishal deswal
 * @version 1.0
 */
@Component
public class UserDtoTransformer implements Function<UserDto, User> {
	
	private UserRoleDtoTransformer roleDtoTransformer;
	
	@Autowired
    public UserDtoTransformer(UserRoleDtoTransformer roleDtoTransformer) {
		super();
		this.roleDtoTransformer = roleDtoTransformer;
	}
	/**
     * Transforms a RegisterUserDto object to a User entity.
     *
     * @param userDto The RegisterUserDto object to be transformed.
     * @return The transformed User entity.
     */
	@Override
	public User apply(UserDto userDto) {
		User user = new User();
		user.setEmailId(userDto.getEmailId());
		user.setName(userDto.getName());
		user.setPassword(encryptPassword(userDto.getPassword()));
		user.setRole(roleDtoTransformer.apply(userDto.getRole()));
		user.setPhoneNumber(userDto.getPhoneNumber());
		return user;
	}
	/**
     * Encrypts the password using the BCrypt hashing algorithm.
     *
     * @param password The password to be encrypted.
     * @return The encrypted password.
     */
	private String encryptPassword(String password) {
		String salt = BCrypt.gensalt();
		String hashedPassword = BCrypt.hashpw(password, salt);
		return hashedPassword;
	}
}
