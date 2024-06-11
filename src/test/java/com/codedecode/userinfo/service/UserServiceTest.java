package com.codedecode.userinfo.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import com.codedecode.userinfo.dto.UserDTO;
import com.codedecode.userinfo.entity.User;
import com.codedecode.userinfo.mapper.UserMapper;
import com.codedecode.userinfo.repo.UserRepo;
import com.codedecode.userinfo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepo userRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        // Create a mock user entity
        User mockUser = new User(1, "John", "pwd", "add street", "add city");

        // Create a mock user DTO
        UserDTO mockUserDTO = new UserDTO(1, "John", "pwd", "add street", "add city");

        // Mock the repository behavior
        when(userRepo.save(any(User.class))).thenReturn(mockUser);

        // Call the service method
        UserDTO result = userService.addUser(mockUserDTO);

        // Verify the result
        assertEquals(mockUserDTO, result);

        // Verify that the repository save method was called
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void testFetchUserDetailsById() {
        // Create a mock user ID
        Integer mockUserId = 1;

        // Create a mock user entity
        User mockUser = new User(1, "John", "pwd", "add street", "add city");

        // Mock the repository behavior
        when(userRepo.findById(mockUserId)).thenReturn(Optional.of(mockUser));

        // Call the service method
        ResponseEntity<UserDTO> response = userService.fetchUserDetailsById(mockUserId);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(UserMapper.INSTANCE.mapUserToUserDTO(mockUser), response.getBody());

        // Verify that the repository findById method was called
        verify(userRepo, times(1)).findById(mockUserId);
    }

    @Test
    void testFetchUserDetailsById_NotFound() {
        // Create a mock user ID
        Integer mockUserId = 1;

        // Mock the repository behavior
        when(userRepo.findById(mockUserId)).thenReturn(Optional.empty());

        // Call the service method
        ResponseEntity<UserDTO> response = userService.fetchUserDetailsById(mockUserId);

        // Verify the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        // Verify that the repository findById method was called
        verify(userRepo, times(1)).findById(mockUserId);
    }
}

