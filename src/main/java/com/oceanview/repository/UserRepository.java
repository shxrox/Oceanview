package com.oceanview.repository;
import com.oceanview.model.User;
import java.sql.SQLException;

public interface UserRepository {
    User findByEmail(String email) throws SQLException;
    boolean save(User user) throws SQLException;


    boolean update(User user) throws SQLException;
}