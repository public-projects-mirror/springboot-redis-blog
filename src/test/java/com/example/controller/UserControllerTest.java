package com.example.controller;

import com.example.dto.UserDTO;
import com.example.manager.BaseAuthManager;
import com.example.model.EditPasswordRequest;
import com.example.response.ApiResponse;
import com.example.service.AuthService;
import com.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private BaseAuthManager authManager;

    @Mock
    private AuthService authService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void editPassword_ShouldReturnSuccess() {
        EditPasswordRequest editPasswordRequest = new EditPasswordRequest("oldPassword", "newPassword");
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId("1");
        userDTO.setUsername("testUser");

        when(authManager.getUserId("session123")).thenReturn("1");
        when(userService.findUserByUserId("1")).thenReturn(userDTO);
        doNothing().when(authService).authenticate("testUser", "oldPassword");
        doNothing().when(userService).editPassword("1", "newPassword");

        ApiResponse<String> response = userController.editPassword("session123", editPasswordRequest);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("User password is edited successfully", response.getMessage());
    }

    @Test
    void editPassword_ShouldThrowAuthenticationFailedException() {
        EditPasswordRequest editPasswordRequest = new EditPasswordRequest("wrongOldPassword", "newPassword");
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId("1");
        userDTO.setUsername("testUser");

        when(authManager.getUserId("session123")).thenReturn("1");
        when(userService.findUserByUserId("1")).thenReturn(userDTO);
        doThrow(new RuntimeException("Authentication failed")).when(authService).authenticate("testUser", "wrongOldPassword");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userController.editPassword("session123", editPasswordRequest));
        assertEquals("Authentication failed", exception.getMessage());
    }
}
