package com.rifki.java_project_task1.view;

import com.rifki.java_project_task1.controller.AuthController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private AuthController authController;

    private JTextField tfNim;
    private JButton btnLogin;

    public LoginFrame(AuthController authController) {
        this.authController = authController;

        setTitle("Login Form");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JLabel labelNim = new JLabel("NIM:");
        tfNim = new JTextField(15);
        btnLogin = new JButton("Login");

        add(labelNim);
        add(tfNim);
        add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String inputNim = tfNim.getText().trim();

        if (inputNim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "NIM tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean success = authController.login(inputNim);

        if (success) {
            JOptionPane.showMessageDialog(this, "Login Berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            
            System.out.println("User aktif: " + authController.getUser());
   
            this.dispose(); 
        } else {
            JOptionPane.showMessageDialog(this, "NIM tidak ditemukan / Login Gagal!", "Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }
}