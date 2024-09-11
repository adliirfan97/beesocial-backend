package com.beesocial.usermanagementservice;

import com.beesocial.usermanagementservice.controller.UserManagementServiceController;
import com.beesocial.usermanagementservice.model.ROLE;
import com.beesocial.usermanagementservice.model.User;
import com.beesocial.usermanagementservice.service.UserService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserManagementServiceControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserManagementServiceController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this); // Initializes mocks
        ReflectionTestUtils.setField(controller, "userService", userService); // Inject userService mock into controller
    }

    @Test
    public void test_save_valid_user() {
        // Arrange
        User user = new User("John", "Doe", "john.doe@example.com", "1234567890", "password123", "profilePhoto.jpg", ROLE.EMPLOYEE);
        when(userService.saveUser(user)).thenReturn(user);

        // Act
        User savedUser = controller.saveNewUser(user);

        // Assert
        assertNotNull(savedUser);
        assertEquals("John", savedUser.getFirstName());
        assertEquals("Doe", savedUser.getLastName());
        assertEquals("john.doe@example.com", savedUser.getEmail());
    }
}
