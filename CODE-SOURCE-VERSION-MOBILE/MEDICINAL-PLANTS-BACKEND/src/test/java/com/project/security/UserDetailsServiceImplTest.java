package com.project.security;

import com.project.entity.User;
import com.project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        // set other user properties as needed
    }

    @Test
    void testLoadUserByUsername_Success() {
        // Mock the repository to return the user when a username is provided
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        // Call the method
        UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");

        // Assert that the userDetails is not null and username is correct
        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Mock the repository to return an empty Optional when a username is provided
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        // Assert that a UsernameNotFoundException is thrown
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("nonExistentUser");
        });
    }
}
