package com.nagarro.watchstore.service;


import java.util.Optional;

import com.nagarro.watchstore.entity.User;
/**
 * Service interface for managing user-related operations.
 * @author vishal deswal
 * @version 1.0
 */
public interface UserService {
	/**
     * Retrieve a user by their email ID.
     *
     * @param emailId The email ID of the user.
     * @return An Optional containing the user if found, or an empty Optional if not found.
     */
	public Optional<User> getUserByID(String emailId);

    /**
     * Add a new user.
     *
     * @param user The user to be added.
     * @return The emailId of the added user.
     */
	public String addUser(User user);
	
	/**
     * Retrieve a user by their email ID.
     *
     * @param emailId The email ID of the user.
     * @return An Optional containing the user if found, or an empty Optional if not found.
     */
	public User findUserById(String emailId);
	

}
