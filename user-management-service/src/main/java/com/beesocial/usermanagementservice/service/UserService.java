package com.beesocial.usermanagementservice.service;

import com.beesocial.usermanagementservice.model.User;
import com.beesocial.usermanagementservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
