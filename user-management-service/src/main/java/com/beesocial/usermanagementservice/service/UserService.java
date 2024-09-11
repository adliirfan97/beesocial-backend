package com.beesocial.usermanagementservice.service;

import com.beesocial.usermanagementservice.model.User;
import com.beesocial.usermanagementservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // save new user
    public User saveUser(User user){
        return userRepository.save(user);
    }

    // get user by id
    public Optional<User> getUserById(int userId) {
        return userRepository.findById(userId);
    }

    // get all users
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    // delete user by id
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

    // update existing user by id
    public User updateUser(int userId){
        Optional<User> userOptional = userRepository.findById(userId);

        // check if user exists
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return userRepository.save(user);
        }
        return null;
    }
}
