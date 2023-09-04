package com.nagarro.watchstore.validator;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.nagarro.watchstore.dto.UserDto;
import com.nagarro.watchstore.enums.UserRole;
import com.nagarro.watchstore.exception.BadRequestException;
import com.nagarro.watchstore.util.CommonValidator;


/**
 * Validator class to validate the fields of a RegisterUserDto object.
 * @author vishal deswal
 * @version 1.0
 */
@Component
public class UserDtoValidator implements Predicate<UserDto> {

    /**
     * Validates the fields of a RegisterUserDto object.
     *
     * @param userDto The RegisterUserDto object to be validated.
     * @return The ValidationResult object containing the validation errors, if any.
     */
	@Override
	public boolean test(UserDto userDto) {
    	
    	CommonValidator.isEmailNotValid(userDto.getEmailId());
    	CommonValidator.isNameNotValid(userDto.getName());
    	CommonValidator.isPasswordNotValid(userDto.getPassword());
    	CommonValidator.isPhoneNotValid(userDto.getPhoneNumber());
    	
        return true;
	}

}
