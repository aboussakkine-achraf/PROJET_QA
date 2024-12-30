package com.project.controller;

import com.project.security.UserDetailsImpl;
import com.project.security.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthController authController;

    private String username;
    private String password;

    @BeforeEach
    public void setUp() {
        username = "testUser";
        password = "password123";
    }

    @Test
    public void testLoginSuccess() {
        // Arrange: Mocking dependencies
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(userDetails.getUsername()).thenReturn(username); // Mock getUsername()

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails); // Mock getPrincipal() to return the userDetails

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication); // Return the mocked Authentication object

        when(jwtUtils.generateToken(username, true)).thenReturn("accessToken");
        when(jwtUtils.generateToken(username, false)).thenReturn("refreshToken");

        // Act: Call the login method
        Map<String, String> response = authController.login(username, password);

        // Assert: Verify the response and interactions
        assertEquals(2, response.size());
        assertEquals("accessToken", response.get("accessToken"));
        assertEquals("refreshToken", response.get("refreshToken"));

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils, times(1)).generateToken(username, true);
        verify(jwtUtils, times(1)).generateToken(username, false);
    }


    @Test
    public void testLoginAuthenticationFailure() {
        // Arrange: Mocking an authentication failure
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Authentication failed"));

        // Act & Assert: Call the login method and expect an exception
        try {
            authController.login(username, password);
        } catch (Exception e) {
            assertEquals("Authentication failed", e.getMessage());
        }

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void testRefreshTokenSuccess() {
        // Arrange: Mocking dependencies
        String refreshToken = "validRefreshToken";
        when(jwtUtils.validateToken(refreshToken)).thenReturn(true);
        when(jwtUtils.extractUsername(refreshToken)).thenReturn(username);
        when(jwtUtils.generateToken(username, true)).thenReturn("newAccessToken");

        // Act: Call the refresh-token method
        String newAccessToken = authController.refreshToken(refreshToken);

        // Assert: Verify the response
        assertEquals("newAccessToken", newAccessToken);

        verify(jwtUtils, times(1)).validateToken(refreshToken);
        verify(jwtUtils, times(1)).extractUsername(refreshToken);
        verify(jwtUtils, times(1)).generateToken(username, true);
    }

    @Test
    public void testRefreshTokenInvalid() {
        // Arrange: Mocking an invalid token
        String invalidRefreshToken = "invalidRefreshToken";
        when(jwtUtils.validateToken(invalidRefreshToken)).thenReturn(false);

        // Act & Assert: Call the refresh-token method and expect an exception
        try {
            authController.refreshToken(invalidRefreshToken);
        } catch (RuntimeException e) {
            assertEquals("Invalid refresh token", e.getMessage());
        }

        verify(jwtUtils, times(1)).validateToken(invalidRefreshToken);
    }
}
