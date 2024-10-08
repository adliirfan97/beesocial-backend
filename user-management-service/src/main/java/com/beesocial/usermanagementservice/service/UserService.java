package com.beesocial.usermanagementservice.service;

import com.beesocial.usermanagementservice.model.User;
import com.beesocial.usermanagementservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get User by userId
    public User getUserById(long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    // Get User by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Save new User
    public User saveUser(User user){
        return userRepository.save(user);
    }

    // Delete User by userId
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    // Update existing User by userId
    public User updateUser(long userId){
        Optional<User> userOptional = userRepository.findById(userId);

        // check if user exists
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return userRepository.save(user);
        }
        return null;
    }

}
