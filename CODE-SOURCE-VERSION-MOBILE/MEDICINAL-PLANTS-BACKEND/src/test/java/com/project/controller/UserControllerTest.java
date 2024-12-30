package com.project.controller;

import com.project.entity.User;
import com.project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setPassword("password123");
    }

    @Test
    public void testRegisterUserSuccessfully() {
        // Mock repository behavior to simulate a new user (username and email not found)
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        // Calling the register method
        ResponseEntity<String> response = userController.register(user);

        // Verifying the behavior and the response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered successfully!", response.getBody());

        // Verifying interactions
        verify(userRepository, times(1)).findByUsername(user.getUsername()); // Ensure username check is invoked
        verify(userRepository, times(1)).findByEmail(user.getEmail()); // Ensure email check is invoked
        verify(passwordEncoder, times(1)).encode("password123"); // Ensure password encoding happens with correct password
        verify(userRepository, times(1)).save(user); // Ensure save method is invoked to persist user
    }


    @Test
    public void testRegisterUserWithEmailAlreadyExists() {
        // Mock repository behavior to simulate that the email already exists
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Calling the register method
        ResponseEntity<String> response = userController.register(user);

        // Verifying the behavior and the response
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("User already exists!", response.getBody());

        // Verifying interactions
        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, times(0)).save(user); // Ensure save is not called
    }
}
