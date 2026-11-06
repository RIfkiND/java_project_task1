/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.rifki.java_project_task1.server;

import com.rifki.java_project_task1.view.LoginFrame;
import com.rifki.java_project_task1.controller.AuthController;
import com.rifki.java_project_task1.services.AuthService;
import com.rifki.java_project_task1.repository.UserRepository;
/**
 *
 * @author rifki
 */
public class main {

    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();

        AuthService authService = new AuthService(userRepository);

        AuthController authController = new AuthController(authService);

        LoginFrame loginFrame = new LoginFrame(authController);
        loginFrame.setVisible(true);
    }
}
