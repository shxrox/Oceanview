package com.oceanview.service;

import com.oceanview.dao.RoomDAO;
import com.oceanview.dao.RoomDAOImpl;
import com.oceanview.model.Room;

import java.sql.Date; // Make sure this is imported
import java.sql.SQLException;
import java.util.ArrayList; // Import ArrayList
import java.util.List;

public class RoomService {

    private RoomDAO roomDAO;

    public RoomService() {
        this.roomDAO = new RoomDAOImpl();
    }

    // OLD METHOD (Removed or Updated)
    // We removed 'findAllAvailable()' because it doesn't know about dates.

    // NEW METHOD: Validates availability based on dates
    public List<Room> getAvailableRooms(Date checkIn, Date checkOut) {
        try {
            // Call the NEW method in DAO
            return roomDAO.findAvailableRoomsByDate(checkIn, checkOut);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Method to get a single room (for booking details)
    public Room getRoomById(int id) {
        try {
            return roomDAO.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}