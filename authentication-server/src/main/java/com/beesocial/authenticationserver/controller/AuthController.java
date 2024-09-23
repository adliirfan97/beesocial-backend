package com.beesocial.authenticationserver.controller;

import com.beesocial.authenticationserver.DTOs.AuthRequest;
import com.beesocial.authenticationserver.DTOs.AuthResponse;
import com.beesocial.authenticationserver.DTOs.User;
import com.beesocial.authenticationserver.DTOs.UserRegistrationRequest;
import com.beesocial.authenticationserver.feign.FirebaseStorageClient;
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

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final FirebaseStorageClient firebaseStorageClient;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder, FirebaseStorageClient firebaseStorageClient) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.firebaseStorageClient = firebaseStorageClient;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtTokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest request) {
        System.out.println("Registering user: " + request.getEmail());
        try {
            // TODO: Fix this shit
//            if (userService.userExist(request.getEmail())) {
//                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
//            }
                System.out.println(request);
                // Encrypt Password
                request.setPassword(passwordEncoder.encode(request.getPassword()));
                request.setRole("USER");

                System.out.println(request);
                System.out.println(userService.saveUser(request));

                return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed.");
        }
    }
}
