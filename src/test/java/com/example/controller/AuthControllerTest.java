package com.example.controller;

import com.example.dto.UserDTO;
import com.example.manager.BaseAuthManager;
import com.example.model.UserRequest;
import com.example.response.ApiResponse;
import com.example.service.AuthService;
import com.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthService authService;

    @Mock
    private BaseAuthManager authManager;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_ShouldReturnSuccess() {
        // 假设 UserDTO 中有 user_id 和 username 字段
        UserRequest userRequest = new UserRequest("testUser", "testPassword");
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId("1");
        userDTO.setUsername("testUser");

        doNothing().when(authService).authenticate(userRequest.getUsername(), userRequest.getPassword());
        when(userService.findUserByUsername(userRequest.getUsername())).thenReturn(userDTO);
        when(authManager.createSession("1")).thenReturn("session123");

        ApiResponse<String> response = authController.login(userRequest);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Successfully logged in", response.getMessage());
        assertEquals("session123", response.getData());
    }

    @Test
    void login_ShouldThrowAuthenticationFailedException() {
        UserRequest userRequest = new UserRequest("testUser", "wrongPassword");
        doThrow(new RuntimeException("Authentication failed")).when(authService).authenticate(userRequest.getUsername(), userRequest.getPassword());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authController.login(userRequest));
        assertEquals("Authentication failed", exception.getMessage());
    }

    @Test
    void registerUser_ShouldReturnSuccess() {
        UserRequest userRequest = new UserRequest("testUser", "testPassword");
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId("1");
        userDTO.setUsername("testUser");

        doNothing().when(userService).checkUserExists(userRequest.getUsername());
        when(userService.saveUser(userRequest.getUsername(), userRequest.getPassword())).thenReturn(userDTO);
        when(authManager.createSession("1")).thenReturn("session123");

        ApiResponse<String> response = authController.registerUser(userRequest);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("User registered successfully", response.getMessage());
        assertEquals("session123", response.getData());
    }

    @Test
    void registerUser_ShouldThrowUserAlreadyExistsException() {
        UserRequest userRequest = new UserRequest("testUser", "testPassword");
        doThrow(new RuntimeException("User already exists")).when(userService).checkUserExists(userRequest.getUsername());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authController.registerUser(userRequest));
        assertEquals("User already exists", exception.getMessage());
    }

    @Test
    void home_ShouldReturnUsername() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId("1");
        userDTO.setUsername("testUser");

        when(authManager.getUserId("session123")).thenReturn("1");
        when(userService.findUserByUserId("1")).thenReturn(userDTO);

        ApiResponse<String> response = authController.home("session123");

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Welcome, testUser", response.getMessage());
        assertEquals("testUser", response.getData());
    }

    @Test
    void home_ShouldThrowInvalidSessionException() {
        when(authManager.getUserId("invalidSession")).thenThrow(new RuntimeException("Invalid session"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authController.home("invalidSession"));
        assertEquals("Invalid session", exception.getMessage());
    }

    @Test
    void logout_ShouldReturnSuccess() {
        doNothing().when(authManager).deleteSessionBySessionId("session123");

        ApiResponse<String> response = authController.logout("session123");

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Successfully logged out", response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void logout_ShouldThrowSessionNotFoundException() {
        doThrow(new RuntimeException("Session not found")).when(authManager).deleteSessionBySessionId("invalidSession");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authController.logout("invalidSession"));
        assertEquals("Session not found", exception.getMessage());
    }
}
