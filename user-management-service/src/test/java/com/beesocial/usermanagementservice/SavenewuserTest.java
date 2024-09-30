package com.beesocial.usermanagementservice;

import com.beesocial.usermanagementservice.controller.UserManagementServiceController;
import com.beesocial.usermanagementservice.model.Role;
import com.beesocial.usermanagementservice.model.User;
import com.beesocial.usermanagementservice.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

class SavenewuserTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserManagementServiceController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this); // Initializes mocks
        ReflectionTestUtils.setField(controller, "userService", userService); // Inject userService mock into controller
    }

    // Test: Saving a valid user should return the saved user object
    @Test
    public void test_save_valid_user() {
        User validUser = new User("John", "Doe", "john.doe@example.com", "1234567890", "password123", "profilePhoto.jpg", "@JohnDoe", Role.EMPLOYEE);
        when(userService.saveUser(validUser)).thenReturn(validUser);

        User result = controller.saveNewUser(validUser);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(validUser.getEmail(), result.getEmail());
        verify(userService, times(1)).saveUser(validUser);
    }

    // Test: Saving a user with missing required fields should throw an exception
    @Test
    public void test_save_user_missing_fields() {
        User invalidUser = new User("", "", "john.doe@example.com", "1234567890", "password123", "profilePhoto.jpg", "@JohnDoe", Role.EMPLOYEE);
        when(userService.saveUser(invalidUser)).thenThrow(new IllegalArgumentException("Missing required fields"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.saveNewUser(invalidUser);
        });

        Assertions.assertEquals("Missing required fields", exception.getMessage());
        verify(userService, times(1)).saveUser(invalidUser);
    }

    // Test: Saving a user with all required fields populated should return the user object
    @Test
    public void test_save_user_with_all_required_fields() {
        User validUser = new User("John", "Doe", "johndoe@example.com", "123456789", "password123", "profile.jpg", "@JohnDoe", Role.EMPLOYEE);
        when(userService.saveUser(validUser)).thenReturn(validUser);

        User result = controller.saveNewUser(validUser);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("John", result.getFirstName());
        Assertions.assertEquals("Doe", result.getLastName());
        Assertions.assertEquals("johndoe@example.com", result.getEmail());
        Assertions.assertEquals("123456789", result.getPhoneNumber());
        Assertions.assertEquals("password123", result.getPassword());
        Assertions.assertEquals("profile.jpg", result.getProfilePhoto());
        Assertions.assertEquals(Role.EMPLOYEE, result.getRole());

        verify(userService, times(1)).saveUser(validUser);
    }

    // Test: Saving a user with a valid email format should return the saved user object
    @Test
    public void test_save_user_with_valid_email_format() {
        User validUser = new User("Jane", "Smith", "janesmith@example.com", "987654321", "pass123", "image.jpg", "@JohnDoe", Role.HR);
        when(userService.saveUser(validUser)).thenReturn(validUser);

        User result = controller.saveNewUser(validUser);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Jane", result.getFirstName());
        Assertions.assertEquals("Smith", result.getLastName());
        Assertions.assertEquals("janesmith@example.com", result.getEmail());
        Assertions.assertEquals("987654321", result.getPhoneNumber());
        Assertions.assertEquals("pass123", result.getPassword());
        Assertions.assertEquals("image.jpg", result.getProfilePhoto());
        Assertions.assertEquals(Role.HR, result.getRole());

        verify(userService, times(1)).saveUser(validUser);
    }
}