package com.oceanview.dao;

import com.oceanview.model.Room;
import java.sql.SQLException;
import java.util.List;

public interface RoomDAO {
    // Method to get a list of all rooms that are currently empty
    List<Room> findAllAvailable() throws SQLException;

    // Method to find a specific room by its ID
    Room findById(int id) throws SQLException;

    // Method to change a room's status (e.g., from Available to Booked)
    boolean updateAvailability(int id, boolean isAvailable) throws SQLException;
}