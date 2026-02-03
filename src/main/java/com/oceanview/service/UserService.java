package com.oceanview.service;

import com.oceanview.dao.UserDAO;
import com.oceanview.dao.UserDAOImpl;
import com.oceanview.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class UserService {

    private UserDAO userDAO;

    // Constructor: initialize DAO
    public UserService() {
        // In frameworks like Spring, we could use dependency injection
        // Here we manually instantiate it
        this.userDAO = new UserDAOImpl();
    }

    /**
     * Authenticate user login
     * @param username the username entered
     * @param password the plaintext password entered
     * @return User object if successful, null otherwise
     * @throws SQLException if database error occurs
     */
    public User login(String username, String password) throws SQLException {
        // 1. Find user by username
        User user = userDAO.findByUsername(username);

        // 2. Check password if user exists
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user; // Login successful
        }

        return null; // Login failed
    }

    /**
     * Register a new receptionist (FILE LOGGER VERSION)
     * @param fullName Full name of receptionist
     * @param email Email of receptionist (used for sending credentials)
     * @param username Username for login
     * @return The raw password if registration succeeds, null if username exists or save fails
     * @throws SQLException if database error occurs
     */
    public String registerReceptionist(String fullName, String email, String username) throws SQLException {
        // 1. Check if username exists
        if (userDAO.findByUsername(username) != null) {
            return null; // Username taken
        }

        // 2. Generate credentials
        String rawPassword = generateRandomPassword();
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

        // 3. Create User
        User receptionist = new User();
        receptionist.setFullName(fullName);
        receptionist.setEmail(email);
        receptionist.setUsername(username);
        receptionist.setPassword(hashedPassword);
        receptionist.setRole("RECEPTIONIST");

        // 4. Save User
        if (userDAO.save(receptionist)) {

            // 5. "Send" Email (Triggers File Logger via EmailUtility)
            // Run in a separate thread so UI does not freeze
            new Thread(() -> {
                try {
                    String subject = "Ocean View Resort - Your Login Credentials";
                    String message = "Dear " + fullName + ",\n\n" +
                            "Welcome to the team!\n" +
                            "Your account has been created successfully.\n\n" +
                            "Username: " + username + "\n" +
                            "Password: " + rawPassword + "\n\n" +
                            "Please keep these credentials safe.\n\n" +
                            "Ocean View Resort Management";

                    com.oceanview.util.EmailUtility.sendEmail(email, subject, message);

                } catch (Exception e) {
                    System.err.println("Failed to write email file: " + e.getMessage());
                }
            }).start();

            return rawPassword; // Return password for admin / clipboard popup
        }

        return null; // Failed to save user
    }

    /**
     * Generate a random 8-character password with letters, digits, and symbols
     * @return Random password string
     */
    private String generateRandomPassword() {
        int length = 8;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$!";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
}
