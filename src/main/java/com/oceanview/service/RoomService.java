package com.oceanview.service;

import com.oceanview.dao.RoomDAO;
import com.oceanview.dao.RoomDAOImpl;
import com.oceanview.model.Room;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomService {

    private RoomDAO roomDAO;

    public RoomService() {
        // Initialize the DAO
        this.roomDAO = new RoomDAOImpl();
    }

    // 1. Get all rooms that are currently free
    public List<Room> getAvailableRooms() {
        try {
            return roomDAO.findAllAvailable();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return empty list on error
        }
    }

    // 2. Get details of a specific room by ID
    public Room getRoomById(int id) {
        try {
            return roomDAO.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}