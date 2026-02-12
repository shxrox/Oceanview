package com.oceanview.service;

import com.oceanview.repository.UserRepository;
import com.oceanview.repository.UserRepositoryImpl;
import com.oceanview.model.User;
import org.mindrot.jbcrypt.BCrypt; // Ensure you have this import!

import java.sql.SQLException;

public class UserService {
    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepositoryImpl();
    }

    public User login(String email, String password) throws SQLException {
        // 1. Find user by Email
        User user = userRepository.findByEmail(email);

        // 2. Check if user exists AND verify password hash
        if (user != null) {
            // BCrypt.checkpw(plain_text_password, hashed_password_from_db)
            if (BCrypt.checkpw(password, user.getPassword())) {
                return user; // Login Success!
            }
        }
        return null; // Login Failed
    }

    public boolean registerUser(User user) throws SQLException {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return false;
        }

        // 3. Hash password before saving new users too!
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);

        return userRepository.save(user);
    }
}