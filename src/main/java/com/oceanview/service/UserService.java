package com.oceanview.service;

import com.oceanview.dao.UserDAO;
import com.oceanview.dao.UserDAOImpl;
import com.oceanview.model.User;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.SQLException;

public class UserService {

    private UserDAO userDAO;

    public UserService() {
        // In a Framework like Spring, this would be @Autowired (Dependency Injection)
        // Here, we manually instantiate it.
        this.userDAO = new UserDAOImpl();
    }

    // Business Logic: Authenticate User
    public User login(String username, String password) throws SQLException {
        // 1. Find user by username
        User user = userDAO.findByUsername(username);

        // 2. If user exists, check password
        if (user != null) {
            // BCrypt.checkpw(plaintext, hashed) handles the secure comparison
            if (BCrypt.checkpw(password, user.getPassword())) {
                return user; // Login Successful
            }
        }
        return null; // Login Failed (User not found or password mismatch)
    }

    // We will add the 'registerReceptionist' method here later!
}