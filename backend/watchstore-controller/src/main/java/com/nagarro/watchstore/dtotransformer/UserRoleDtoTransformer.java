package com.nagarro.watchstore.dtotransformer;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.nagarro.watchstore.constants.Constant;
import com.nagarro.watchstore.dto.UserRoleDto;
import com.nagarro.watchstore.enums.UserRole;
import com.nagarro.watchstore.exception.BadRequestException;
/**
 * The UserRoleDtoTransformer class is responsible for transforming a UserRoleDto object into a UserRole object.
 * 
 * This class implements the Function interface, allowing it to be used as a function to transform UserRoleDto instances.
 * @author vishaldeswal
 */

@Component
public class UserRoleDtoTransformer implements Function<UserRoleDto, UserRole> {

	@Override
	public UserRole apply(UserRoleDto userRoleDTO) {
		  String userType = userRoleDTO.getUserType().toLowerCase();
	      return UserRole.fromString(userType).orElseThrow(() -> new BadRequestException("role:",Constant.INVALID_USER_ROLE+ userType));
	        
	}
}