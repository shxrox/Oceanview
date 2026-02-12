package com.oceanview.util;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminSeeder {
    public static void main(String[] args) {
        // SQL to insert the new admin
        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";

        // SQL to delete the old admin (optional, keeps DB clean)
        String deleteSql = "DELETE FROM users WHERE email = 'admin@oceanview.com'";

        try (Connection conn = DBConnection.getConnection()) {

            // 1. Remove old admin if exists (to avoid confusion)
            try (PreparedStatement delStmt = conn.prepareStatement(deleteSql)) {
                delStmt.executeUpdate();
            }

            // 2. Insert NEW Admin
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                String rawPassword = "admin123";
                String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

                stmt.setString(1, "System Administrator");
                stmt.setString(2, "admin@gmail.com");  // <--- UPDATED EMAIL
                stmt.setString(3, hashedPassword);
                stmt.setString(4, "ADMIN");

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("✅ SUCCESS: New Admin user created.");
                    System.out.println("📧 Email: admin@gmail.com");
                    System.out.println("🔑 Password: " + rawPassword);
                }
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.out.println("⚠️ Admin with this email already exists.");
            } else {
                e.printStackTrace();
            }
        }
    }
}