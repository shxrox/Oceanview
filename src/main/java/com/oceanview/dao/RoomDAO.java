package com.oceanview.dao;

import com.oceanview.model.Room;
import java.sql.SQLException;
import java.util.List;

public interface RoomDAO {
    // Basic CRUD
    List<Room> getAllRooms() throws SQLException;
    Room findById(int id) throws SQLException;

    // The method causing the error (Make sure this line exists!)
    void updateAvailability(int id, boolean isAvailable) throws SQLException;

    // The Smart Search method
    List<Room> findAvailableRoomsByDate(java.sql.Date checkIn, java.sql.Date checkOut) throws SQLException;
}