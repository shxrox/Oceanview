package com.oceanview.service;

import com.oceanview.repository.UserRepository;
import com.oceanview.repository.UserRepositoryImpl;
import com.oceanview.model.User;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.SQLException;

public class UserService {
    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepositoryImpl();
    }

    // ... (Keep login and registerUser methods EXACTLY as they were) ...
    public User login(String email, String password) throws SQLException {
        User user = userRepository.findByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public boolean registerUser(User user) throws SQLException {
        if (userRepository.findByEmail(user.getEmail()) != null) return false;
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return userRepository.save(user);
    }

    // --- NEW UPDATE LOGIC ---
    public boolean updateUserProfile(User currentUser, String newName, String newPassword) throws SQLException {
        // 1. Update Name
        currentUser.setName(newName);

        // 2. Update Password (ONLY if they typed something)
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            currentUser.setPassword(hashed);
        }

        // 3. Save changes to DB
        return userRepository.update(currentUser);
    }
}