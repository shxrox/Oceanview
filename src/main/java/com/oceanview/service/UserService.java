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

    // ... existing constructor and login method ...

    public boolean registerReceptionist(String fullName, String email, String username) throws SQLException {
        // 1. Generate a random temporary password (8 characters)
        String rawPassword = generateRandomPassword();

        // 2. Hash the password for security
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

        // 3. Create the User Object
        User receptionist = new User();
        receptionist.setFullName(fullName);
        receptionist.setEmail(email);
        receptionist.setUsername(username);
        receptionist.setPassword(hashedPassword);
        receptionist.setRole("RECEPTIONIST");

        // 4. Save to Database
        boolean isSaved = userDAO.save(receptionist);

        // 5. Send Email (Running in a new thread so the website doesn't freeze while sending)
        if (isSaved) {
            new Thread(() -> {
                String subject = "Ocean View Resort - Staff Login Credentials";
                String message = "Dear " + fullName + ",\n\n" +
                        "Your Receptionist account has been created.\n\n" +
                        "Username: " + username + "\n" +
                        "Password: " + rawPassword + "\n\n" +
                        "Please login immediately at the portal.";

                com.oceanview.util.EmailUtility.sendEmail(email, subject, message);
            }).start();
        }

        return isSaved;
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    // We will add the 'registerReceptionist' method here later!
}