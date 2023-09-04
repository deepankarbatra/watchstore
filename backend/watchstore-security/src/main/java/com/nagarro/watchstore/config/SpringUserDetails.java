package com.nagarro.watchstore.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nagarro.watchstore.entity.User;

/**
 * In this class We are assigning Spring userdetails according to our user entity
 * @author mdsharif,vishaldeswal
 *
 */
public class SpringUserDetails implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	private String email;
	private String password;
	private List<GrantedAuthority> authorities;
	
	public SpringUserDetails(User userInfo) {
		email = userInfo.getEmailId();
		password = userInfo.getPassword();
		authorities =new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(userInfo.getRole().getUserType().toUpperCase()));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
