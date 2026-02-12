package com.oceanview.repository;
import com.oceanview.model.User;
import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    User findByEmail(String email) throws SQLException;
    boolean save(User user) throws SQLException;
    boolean update(User user) throws SQLException;

    // NEW METHODS
    List<User> findAll() throws SQLException;
    boolean delete(int id) throws SQLException;
}