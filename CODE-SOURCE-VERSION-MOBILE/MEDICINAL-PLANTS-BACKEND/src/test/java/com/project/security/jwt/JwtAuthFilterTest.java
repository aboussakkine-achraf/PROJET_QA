package com.project.security.jwt;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;  // Add this import
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;  // Add this import

@ExtendWith(MockitoExtension.class)  // Add this annotation
class JwtAuthFilterTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();  // Clear context before each test to avoid interference
    }

    @Test
    void testDoFilterInternal_withValidAccessToken() throws Exception {
        String token = "validAccessToken";
        String username = "testUser";

        // Mock behavior of JwtUtils
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtils.validateToken(token)).thenReturn(true);
        when(jwtUtils.extractUsername(token)).thenReturn(username);

        // Execute the filter
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        // Verify that the SecurityContextHolder was populated with the authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals(username, authentication.getName());

        // Verify that filterChain.doFilter() is called to continue the filter chain
        verify(filterChain, times(1)).doFilter(request, response);
    }



    @Test
    void testDoFilterInternal_withInvalidToken() throws Exception {
        String invalidToken = "invalidToken";

        // Mock behavior of JwtUtils
        when(request.getHeader("Authorization")).thenReturn("Bearer " + invalidToken);
        when(jwtUtils.validateToken(invalidToken)).thenReturn(false);

        // Execute the filter
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        // Verify that the SecurityContextHolder is empty (authentication is not set)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication);

        // Verify that filterChain.doFilter() is called to continue the filter chain
        verify(filterChain, times(1)).doFilter(request, response);
    }
}
