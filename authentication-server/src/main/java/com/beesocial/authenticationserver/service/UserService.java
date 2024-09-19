package com.beesocial.authenticationserver.service;

import com.beesocial.authenticationserver.DTOs.User;
import com.beesocial.authenticationserver.feign.FirebaseStorageClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService implements UserDetailsService {
    private final FirebaseStorageClient firebaseStorageClient;

    public UserService(FirebaseStorageClient firebaseStorageClient) {
        this.firebaseStorageClient = firebaseStorageClient;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = firebaseStorageClient.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    public <T> String saveUser(T user) {
        return firebaseStorageClient.saveUser(user);
    }

    public boolean userExist(String email) {
        User user = firebaseStorageClient.getUserByEmail(email);
        System.out.println(user);
        return user != null;
    }
}
