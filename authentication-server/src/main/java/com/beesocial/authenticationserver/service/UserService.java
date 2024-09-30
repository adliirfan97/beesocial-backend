package com.beesocial.authenticationserver.service;

import com.beesocial.authenticationserver.DTOs.User;
import com.beesocial.authenticationserver.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean userExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
