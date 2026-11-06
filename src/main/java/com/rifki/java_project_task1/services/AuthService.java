package com.rifki.java_project_task1.services;
import com.rifki.java_project_task1.model.User;
import com.rifki.java_project_task1.repository.UserRepository;


public class AuthService {
    private final UserRepository userRepository;
    private String currentNim;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public boolean login(String inputNim) {
            if (inputNim == null || inputNim.trim().isEmpty()) {
                return false;
            }
            String cleanNim = inputNim.trim();
            boolean isValid = userRepository.checkNim(cleanNim);
            if (isValid) {
                this.currentNim = cleanNim;
            }
            return isValid;
    }
    public String getCurrentNim() {
        return currentNim;
    }
}
