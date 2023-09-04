package com.nagarro.watchstore.entitytransformer;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nagarro.watchstore.dto.UserProfileDto;
import com.nagarro.watchstore.entity.User;

/**
 * Transformer class to convert a User entity to a UserProfileDto.
 * @author vishal deswal
 * @version 1.0
 */
@Component
public class UserProfileTransformer implements Function<User,UserProfileDto>{
	
	private UserRoleTransformer roleTransformer;
	
	
	@Autowired
    public UserProfileTransformer(UserRoleTransformer roleTransformer) {
		super();
		this.roleTransformer = roleTransformer;
	}



	/**
     * Transforms a User entity to a UserProfileDto object.
     *
     * @param user The User entity to be transformed.
     * @return The transformed UserProfileDto object.
     */
	@Override
	public UserProfileDto apply(User user) {
		UserProfileDto userProfileDto = new UserProfileDto();
		userProfileDto.setEmailId(user.getEmailId());
		userProfileDto.setName(user.getName());
		userProfileDto.setPhoneNumber(user.getPhoneNumber());
		userProfileDto.setRole(roleTransformer.apply(user.getRole()));
		return userProfileDto;
	}

}
