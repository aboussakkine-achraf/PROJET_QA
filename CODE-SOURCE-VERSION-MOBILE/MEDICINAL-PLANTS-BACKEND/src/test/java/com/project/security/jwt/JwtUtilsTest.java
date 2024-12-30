package com.project.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

    private JwtUtils jwtUtilsTest;

    private String jwtSecret = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
    private long accessTokenExpiration = 3600000L; // 1 hour
    private long refreshTokenExpiration = 86400000L; // 1 day

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        jwtUtilsTest = new JwtUtils();

        // Use reflection to set private fields in JwtUtils
        Field jwtSecretField = JwtUtils.class.getDeclaredField("jwtSecret");
        jwtSecretField.setAccessible(true);
        jwtSecretField.set(jwtUtilsTest, jwtSecret);

        Field accessTokenExpirationField = JwtUtils.class.getDeclaredField("accessTokenExpiration");
        accessTokenExpirationField.setAccessible(true);
        accessTokenExpirationField.set(jwtUtilsTest, accessTokenExpiration);

        Field refreshTokenExpirationField = JwtUtils.class.getDeclaredField("refreshTokenExpiration");
        refreshTokenExpirationField.setAccessible(true);
        refreshTokenExpirationField.set(jwtUtilsTest, refreshTokenExpiration);
    }
    @Test
    void testGenerateToken() {
        String username = "testUser";
        String token = jwtUtilsTest.generateToken(username, true);

        // Ensure the generated token is not null
        assertNotNull(token);

        // Decode the token and extract the claims to verify the username
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        // Assert that the username is present in the claims
        assertEquals(username, claims.getSubject());
    }

    @Test
    void testValidateToken_ValidToken() {
        String validToken = jwtUtilsTest.generateToken("testUser", true);

        boolean isValid = jwtUtilsTest.validateToken(validToken);

        // Assert that the token is valid
        assertTrue(isValid);
    }

    @Test
    void testValidateToken_InvalidToken() {
        String invalidToken = "invalidToken";

        boolean isValid = jwtUtilsTest.validateToken(invalidToken);

        // Assert that the token is invalid
        assertFalse(isValid);
    }

    @Test
    void testExtractUsername() {
        String username = "testUser";
        String token = jwtUtilsTest.generateToken(username, true);

        String extractedUsername = jwtUtilsTest.extractUsername(token);

        // Assert that the username extracted from the token matches the original username
        assertEquals(username, extractedUsername);
    }

    @Test
    void testIsRefreshTokenExpired_ExpiredToken() {
        String expiredToken = Jwts.builder()
                .setSubject("testUser")
                .setIssuedAt(new Date(System.currentTimeMillis() - 86400000L)) // 1 day ago
                .setExpiration(new Date(System.currentTimeMillis() - 1000)) // expired token
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        boolean isExpired = jwtUtilsTest.isRefreshTokenExpired(expiredToken);

        // Assert that the expired token is correctly detected
        assertTrue(isExpired);
    }

    @Test
    void testIsRefreshTokenExpired_ValidToken() {
        String validToken = jwtUtilsTest.generateToken("testUser", false);

        boolean isExpired = jwtUtilsTest.isRefreshTokenExpired(validToken);

        // Assert that the valid token is not expired
        assertFalse(isExpired);
    }
}
