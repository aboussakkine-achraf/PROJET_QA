package com.project.controller;

import com.project.security.jwt.JwtUtils;
import com.project.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        // Authenticate the user with provided username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Get the user details
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Generate both access token and refresh token
        String accessToken = jwtUtils.generateToken(userDetails.getUsername(), true);
        String refreshToken = jwtUtils.generateToken(userDetails.getUsername(), false);

        // Create a map to return both tokens in the response
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

    @PostMapping("/refresh-token")
    public String refreshToken(@RequestParam String refreshToken) {
        // Validate the refresh token
        if (jwtUtils.validateToken(refreshToken)) {
            // Extract the username from the refresh token
            String username = jwtUtils.extractUsername(refreshToken);
            // Generate a new access token
            return jwtUtils.generateToken(username, true);
        } else {
            // If the refresh token is invalid, return an error message
            throw new RuntimeException("Invalid refresh token");
        }
    }
}
