package com.beesocial.usermanagementservice;

import com.beesocial.usermanagementservice.controller.UserManagementServiceController;
import com.beesocial.usermanagementservice.model.ROLE;
import com.beesocial.usermanagementservice.model.User;
import com.beesocial.usermanagementservice.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SavenewuserTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserManagementServiceController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this); // Initializes mocks
        ReflectionTestUtils.setField(controller, "userService", userService); // Inject userService mock into controller
    }

    // Test: Saving a valid user should return the saved user object
    @Test
    public void test_save_valid_user() {
        User validUser = new User("John", "Doe", "john.doe@example.com", "1234567890", "password123", "profilePhoto.jpg", ROLE.EMPLOYEE);
        when(userService.saveUser(validUser)).thenReturn(validUser);

        User result = controller.saveNewUser(validUser);

        assertNotNull(result);
        assertEquals(validUser.getEmail(), result.getEmail());
        verify(userService, times(1)).saveUser(validUser);
    }

    // Test: Saving a user with missing required fields should throw an exception
    @Test
    public void test_save_user_missing_fields() {
        User invalidUser = new User("", "", "john.doe@example.com", "1234567890", "password123", "profilePhoto.jpg", ROLE.EMPLOYEE);
        when(userService.saveUser(invalidUser)).thenThrow(new IllegalArgumentException("Missing required fields"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.saveNewUser(invalidUser);
        });

        assertEquals("Missing required fields", exception.getMessage());
        verify(userService, times(1)).saveUser(invalidUser);
    }

    // Test: Saving a user with all required fields populated should return the user object
    @Test
    public void test_save_user_with_all_required_fields() {
        User validUser = new User("John", "Doe", "johndoe@example.com", "123456789", "password123", "profile.jpg", ROLE.EMPLOYEE);
        when(userService.saveUser(validUser)).thenReturn(validUser);

        User result = controller.saveNewUser(validUser);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("johndoe@example.com", result.getEmail());
        assertEquals("123456789", result.getPhoneNumber());
        assertEquals("password123", result.getPassword());
        assertEquals("profile.jpg", result.getProfilePhoto());
        assertEquals(ROLE.EMPLOYEE, result.getRole());

        verify(userService, times(1)).saveUser(validUser);
    }

    // Test: Saving a user with a valid email format should return the saved user object
    @Test
    public void test_save_user_with_valid_email_format() {
        User validUser = new User("Jane", "Smith", "janesmith@example.com", "987654321", "pass123", "image.jpg", ROLE.HR);
        when(userService.saveUser(validUser)).thenReturn(validUser);

        User result = controller.saveNewUser(validUser);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals("janesmith@example.com", result.getEmail());
        assertEquals("987654321", result.getPhoneNumber());
        assertEquals("pass123", result.getPassword());
        assertEquals("image.jpg", result.getProfilePhoto());
        assertEquals(ROLE.HR, result.getRole());

        verify(userService, times(1)).saveUser(validUser);
    }
}