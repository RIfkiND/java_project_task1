package com.rifki.java_project_task1.controller;

import com.rifki.java_project_task1.services.AuthService;
import com.rifki.java_project_task1.model.User;

public class AuthController {

    private AuthService authService;
    private User user;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public boolean login(String inputNim) {
        boolean isSuccess = authService.login(inputNim);
        if (isSuccess) {
            this.user = authService.getUserByNim(inputNim);
        }
        return isSuccess;
    }

    public User getUser() {
        return user;
    }
    
}