package com.nagarro.watchstore.jwtservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is the data we are taking for authenticating user
 * @author mdsharif,vishaldeswal
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {
	
	public String email;
	public String password;

}
