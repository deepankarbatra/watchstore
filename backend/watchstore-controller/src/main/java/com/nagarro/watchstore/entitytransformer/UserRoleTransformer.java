package com.nagarro.watchstore.entitytransformer;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.nagarro.watchstore.dto.UserRoleDto;
import com.nagarro.watchstore.enums.UserRole;

@Component
public class UserRoleTransformer implements Function<UserRole, UserRoleDto> {

    @Override
    public UserRoleDto apply(UserRole userRole) {
        return UserRoleDto.valueOf(userRole.name());
    }
}