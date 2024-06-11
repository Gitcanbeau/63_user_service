package com.codedecode.userinfo.controller;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import com.codedecode.userinfo.dto.UserDTO;
import com.codedecode.userinfo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class UserControllerTest {
    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        // Create a mock user to be saved
        UserDTO mockUser = new UserDTO(1, "John", "pwd", "add street", "add city");

        // Mock the service behavior
        when(userService.addUser(mockUser)).thenReturn(mockUser);

        // Call the controller method
        ResponseEntity<UserDTO> response = userController.addUser(mockUser);

        // Verify the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockUser, response.getBody());

        // Verify that the service method was called
        verify(userService, times(1)).addUser(mockUser);
    }

    @Test
    void testFetchUserDetailsById() {
        // Create a mock user ID
        Integer mockUserId = 1;

        // Create a mock user to be returned by the service
        UserDTO mockUser = new UserDTO(1, "John", "pwd", "add street", "add city");

        // Mock the service behavior
        when(userService.fetchUserDetailsById(mockUserId)).thenReturn(new ResponseEntity<>(mockUser, HttpStatus.OK));

        // Call the controller method
        ResponseEntity<UserDTO> response = userController.fetchUserDetailsById(mockUserId);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());

        // Verify that the service method was called
        verify(userService, times(1)).fetchUserDetailsById(mockUserId);
    }

    @Test
    void testFetchUserDetailsById_NotFound() {
        Integer mockUserId = 1;
        when(userService.fetchUserDetailsById(mockUserId)).thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

        ResponseEntity<UserDTO> response = userController.fetchUserDetailsById(mockUserId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(userService, times(1)).fetchUserDetailsById(mockUserId);
    }
}
