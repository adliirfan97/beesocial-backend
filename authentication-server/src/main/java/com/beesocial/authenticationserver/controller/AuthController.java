package com.beesocial.authenticationserver.controller;

import com.beesocial.authenticationserver.DTOs.*;
import com.beesocial.authenticationserver.service.JwtTokenProvider;
import com.beesocial.authenticationserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtTokenProvider.generateToken(authentication);

            LoginResponse successResponse = new LoginResponse(HttpStatus.OK.value(), "Login successful", token);
            return ResponseEntity.ok(successResponse);
        } catch (AuthenticationException e) {
            LoginResponse errorResponse = new LoginResponse(HttpStatus.UNAUTHORIZED.value(), "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        System.out.println("Registering user: " + request.getEmail());
        try {
            if (userService.userExist(request.getEmail())) {
                RegisterResponse conflictResponse = new RegisterResponse(HttpStatus.CONFLICT.value(), "Email already exists");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(conflictResponse);
            }

            // Create new User
            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole() == null ? Role.EMPLOYEE : request.getRole());
            user.setProfilePhoto(request.getProfilePhoto() == null ? null : request.getProfilePhoto());

            // Save User
            userService.saveUser(user);

            RegisterResponse successResponse  = new RegisterResponse(HttpStatus.CREATED.value(), "User registered successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse );

        } catch (Exception e) {
            RegisterResponse errorResponse   = new RegisterResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Registration failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
