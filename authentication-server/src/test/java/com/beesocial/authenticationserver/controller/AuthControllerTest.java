package com.beesocial.authenticationserver.controller;

import com.beesocial.authenticationserver.DTOs.LoginRequest;
import com.beesocial.authenticationserver.DTOs.LoginResponse;
import com.beesocial.authenticationserver.service.JwtTokenProvider;
import com.beesocial.authenticationserver.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthControllerTest {
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private BindingResult bindingResult;
    private AuthController authController;

    @BeforeEach
    void setup() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
        authenticationManager = mock(AuthenticationManager.class);
        jwtTokenProvider = mock(JwtTokenProvider.class);
        bindingResult = mock(BindingResult.class);
        UserService userService = mock(UserService.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

        // Initialize the AuthController with the mocks
        authController = new AuthController(authenticationManager, jwtTokenProvider, userService, passwordEncoder);
    }

    // Successful login with valid credentials returns HTTP 200 and token
    @Test
    void login_ValidCredentials_ReturnsSuccessResponse() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("validuser@example.com");
        loginRequest.setPassword("validpassword");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        String jwtToken = "mock-jwt-token";
        when(jwtTokenProvider.generateToken(authentication)).thenReturn(jwtToken);

        // Act
        ResponseEntity<LoginResponse> response = authController.login(loginRequest, bindingResult);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login successful", response.getBody().getMessage());
        assertEquals(jwtToken, response.getBody().getToken());
        assertEquals(HttpStatus.OK.value(), response.getBody().getStatus());
    }

    // Invalid credentials return HTTP 401 with appropriate message
    @Test
    void login_InvalidCredentials_ReturnsUnauthorizedResponse() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("invaliduser@example.com");
        loginRequest.setPassword("invalidpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act
        ResponseEntity<LoginResponse> response = authController.login(loginRequest, bindingResult);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody().getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getBody().getStatus());
    }

    // Validation errors in request body return HTTP 400 with error details
    @Test
    void login_ValidationErrors_ReturnsBadRequestResponse() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("");  // Invalid email
        loginRequest.setPassword("validpassword");

        // Simulate validation errors
        when(bindingResult.hasErrors()).thenReturn(true);
        List<FieldError> fieldErrors = Arrays.asList(new FieldError("email", "email", "Email is required"));
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        // Act
        ResponseEntity<LoginResponse> response = authController.login(loginRequest, bindingResult);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation error", response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertTrue(response.getBody().getFieldErrors().containsKey("email"));
        assertEquals("Email is required", response.getBody().getFieldErrors().get("email"));
    }

    // Null Email and Password
    @Test
    void login_NullEmailAndPassword_ReturnsValidationError() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(null);  // Null email
        loginRequest.setPassword(null);  // Null password

        // Simulate validation errors
        when(bindingResult.hasErrors()).thenReturn(true);
        List<FieldError> fieldErrors = Arrays.asList(
                new FieldError("email", "email", "Email is required"),
                new FieldError("password", "password", "Password is required")
        );
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        // Act
        ResponseEntity<LoginResponse> response = authController.login(loginRequest, bindingResult);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation error", response.getBody().getMessage());
        assertTrue(response.getBody().getFieldErrors().containsKey("email"));
        assertTrue(response.getBody().getFieldErrors().containsKey("password"));
        assertEquals("Email is required", response.getBody().getFieldErrors().get("email"));
        assertEquals("Password is required", response.getBody().getFieldErrors().get("password"));
    }

}
