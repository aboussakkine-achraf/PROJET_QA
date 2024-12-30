package com.project.security;

import com.project.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;

class UserDetailsImplTest {

    @Mock
    private User user;

    @InjectMocks
    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        // Mocking a user with some sample data
        user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword123");
        userDetails = new UserDetailsImpl(user);
    }

    @Test
    void testGetUsername() {
        // Test the getUsername() method
        assertEquals("testUser", userDetails.getUsername());
    }

    @Test
    void testGetPassword() {
        // Test the getPassword() method
        assertEquals("testPassword123", userDetails.getPassword());
    }

    @Test
    void testIsAccountNonExpired() {
        // Test the isAccountNonExpired() method
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        // Test the isAccountNonLocked() method
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        // Test the isCredentialsNonExpired() method
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        // Test the isEnabled() method
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void testGetAuthorities() {
        // Test the getAuthorities() method
        assertNull(userDetails.getAuthorities(), "Authorities should be null for this test");
    }
}
