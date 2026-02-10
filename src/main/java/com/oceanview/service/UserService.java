package com.oceanview.service;

// FIXED IMPORTS: Use Repository instead of DAO
import com.oceanview.repository.UserRepository;
import com.oceanview.repository.UserRepositoryImpl;
import com.oceanview.model.User;

import java.sql.SQLException;

public class UserService {
    // Use the Interface, not the implementation class
    private UserRepository userRepository;

    public UserService() {
        // Initialize the Implementation
        this.userRepository = new UserRepositoryImpl();
    }

    public User login(String email, String password) throws SQLException {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean registerUser(User user) throws SQLException {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return false; // Email already exists
        }
        return userRepository.save(user);
    }
}