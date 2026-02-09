package com.oceanview.dao;

import com.oceanview.model.Room;
import java.sql.SQLException;
import java.util.List;

public interface RoomDAO {
    List<Room> getAllRooms() throws SQLException;
    Room findById(int id) throws SQLException;
    void updateAvailability(int id, boolean isAvailable) throws SQLException;

    // NEW METHOD
    List<Room> findAvailableRoomsByDate(java.sql.Date checkIn, java.sql.Date checkOut) throws SQLException;
}