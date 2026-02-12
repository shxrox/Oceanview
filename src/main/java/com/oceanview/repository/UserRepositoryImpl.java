package com.oceanview.repository;

import com.oceanview.model.User;
import com.oceanview.util.DBConnection;
import java.sql.*;

public class UserRepositoryImpl implements UserRepository {

    // ... (Keep findByEmail and save methods EXACTLY as they were) ...
    @Override
    public User findByEmail(String email) throws SQLException {
        // ... (Paste your existing findByEmail code here) ...
        // Re-pasting for completeness if you need it:
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getString("role"));
            }
        }
        return null;
    }

    @Override
    public boolean save(User user) throws SQLException {
        // ... (Paste your existing save code here) ...
        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            return stmt.executeUpdate() > 0;
        }
    }

    // --- NEW UPDATE METHOD ---
    @Override
    public boolean update(User user) throws SQLException {
        String sql = "UPDATE users SET name = ?, password = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getId());
            return stmt.executeUpdate() > 0;
        }
    }
}