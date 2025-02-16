package com.beesocial.authenticationserver.controller;

import com.beesocial.authenticationserver.DTOs.*;
import com.beesocial.authenticationserver.service.JwtTokenProvider;
import com.beesocial.authenticationserver.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            LoginResponse validationErrorResponse = new LoginResponse();
            validationErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            validationErrorResponse.setMessage("Validation error");
            validationErrorResponse.setFieldErrors(fieldErrors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorResponse);
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtTokenProvider.generateToken(authentication);

            LoginResponse successResponse = new LoginResponse();
            successResponse.setStatus(HttpStatus.OK.value());
            successResponse.setMessage("Login successful");
            successResponse.setToken(token);
            return ResponseEntity.ok(successResponse);
        } catch (AuthenticationException e) {
            LoginResponse errorResponse = new LoginResponse();
            errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            errorResponse.setMessage("Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

            RegisterResponse validationErrorResponse = new RegisterResponse();
            validationErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            validationErrorResponse.setMessage("Validation error");
            validationErrorResponse.setFieldErrors(fieldErrors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorResponse);
        }
        System.out.println("Registering user: " + request.getEmail());
        try {
            if (userService.userExist(request.getEmail())) {
                RegisterResponse conflictResponse = new RegisterResponse();
                conflictResponse.setStatus(HttpStatus.CONFLICT.value());
                conflictResponse.setMessage("Email already exists");
                System.out.println("Email, " + request.getEmail() + ", already exists");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(conflictResponse);
            }

            // Create new User
            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            // Save User
            userService.saveUser(user);

            RegisterResponse successResponse  = new RegisterResponse();
            successResponse.setStatus(HttpStatus.CREATED.value());
            successResponse.setMessage("User registered successfully");
            System.out.println("User, " + request.getEmail() + ", registered successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse );

        } catch (Exception e) {
            RegisterResponse errorResponse = new RegisterResponse();
            errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.setMessage("Registration failed");
            System.out.println("Registration failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
