package com.example.controller;

import com.example.manager.BaseAuthManager;
import com.example.model.EditPasswordRequest;
import com.example.response.ApiResponse;
import com.example.service.AuthService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private BaseAuthManager authManager;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/editPassword", method = RequestMethod.GET)
    public ApiResponse<String> editPassword(@RequestParam String sessionId,
                                            @RequestBody EditPasswordRequest editPasswordRequest) {
        String userId = authManager.getUserId(sessionId);
        String username = userService.findUserByUserId(userId).getUsername();
        String oldPassword = editPasswordRequest.getOldPassword();
        String newPassword = editPasswordRequest.getNewPassword();
        authService.authenticate(username, oldPassword);
        userService.editPassword(userId, newPassword);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("User password is edited successfully");
        return apiResponse;
    }
}
