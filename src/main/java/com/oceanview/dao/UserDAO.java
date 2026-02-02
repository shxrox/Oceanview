package com.oceanview.dao;

import com.oceanview.model.User;
import java.sql.SQLException;


public interface UserDAO {

    User findByUsername(String username) throws SQLException;


    boolean save(User user) throws SQLException;
}