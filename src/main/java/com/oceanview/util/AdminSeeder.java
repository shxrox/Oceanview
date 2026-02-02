package com.oceanview.util;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminSeeder {
    public static void main(String[] args) {
        String sql = "INSERT INTO users (username, password, role, full_name, email) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 1. Define Admin Credentials
            String rawPassword = "admin123"; // This is the password you will use to login
            String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

            // 2. Set values in the query
            stmt.setString(1, "admin");
            stmt.setString(2, hashedPassword);
            stmt.setString(3, "ADMIN"); // Must match the ENUM in database
            stmt.setString(4, "System Administrator");
            stmt.setString(5, "admin@oceanview.com");

            // 3. Execute
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("SUCCESS: Admin user created!");
                System.out.println("Username: admin");
                System.out.println("Password: " + rawPassword);
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Duplicate entry error code in MySQL
                System.out.println("Admin user already exists.");
            } else {
                e.printStackTrace();
            }
        }
    }
}