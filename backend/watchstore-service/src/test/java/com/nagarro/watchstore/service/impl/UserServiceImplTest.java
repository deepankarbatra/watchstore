package com.nagarro.watchstore.service.impl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nagarro.watchstore.dao.UserRepository;
import com.nagarro.watchstore.entity.User;
import com.nagarro.watchstore.exception.UserNotFoundException;
import com.nagarro.watchstore.exception.UserAlreadyExistException;

/**
 * Test cases for UserServiceImpl class.
 * Author: Vishal Deswal
 */
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserByID_ExistingUser() {
        // Arrange
        String emailId = "vishal@example.com";
        User user = new User();
        user.setEmailId(emailId);
        Optional<User> userOptional = Optional.of(user);
        when(userRepository.findById(emailId)).thenReturn(userOptional);

        // Act
        Optional<User> result = userService.getUserByID(emailId);

        // Assert
        assertEquals(userOptional, result);
        verify(userRepository, times(1)).findById(emailId);
    }

    @Test
    public void testGetUserByID_NonExistingUser() {
        // Arrange
        String emailId = "vishal@example.com";
        when(userRepository.findById(emailId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserByID(emailId));
        verify(userRepository, times(1)).findById(emailId);
    }

    @Test
    public void testAddUser_NewUser() {
        // Arrange
        User user = new User();
        user.setEmailId("vishal@example.com");
        when(userRepository.findById(user.getEmailId())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        // Act
        String result = userService.addUser(user);

        // Assert
        assertEquals(user.getEmailId(), result);
        verify(userRepository, times(1)).findById(user.getEmailId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testAddUser_ExistingUser() {
        // Arrange
        User user = new User();
        user.setEmailId("vishal@example.com");
        when(userRepository.findById(user.getEmailId())).thenReturn(Optional.of(user));

        // Act and Assert
        assertThrows(UserAlreadyExistException.class, () -> userService.addUser(user));
        verify(userRepository, times(1)).findById(user.getEmailId());
        verify(userRepository, never()).save(user);
    }

    @Test
    public void testFindUserById_ExistingUser() {
        // Arrange
        String emailId = "vishal@example.com";
        User user = new User();
        user.setEmailId(emailId);
        Optional<User> userOptional = Optional.of(user);
        when(userRepository.findById(emailId)).thenReturn(userOptional);

        // Act
        User result = userService.findUserById(emailId);

        // Assert
        assertEquals(user, result);
        verify(userRepository, times(1)).findById(emailId);
    }

    @Test
    public void testFindUserById_NonExistingUser() {
        // Arrange
        String emailId = "vishal@example.com";
        when(userRepository.findById(emailId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userService.findUserById(emailId));
        verify(userRepository, times(1)).findById(emailId);
    }
}
