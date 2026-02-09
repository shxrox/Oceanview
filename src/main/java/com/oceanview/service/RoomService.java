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
        this.roomDAO = new RoomDAOImpl();
    }

    public List<Room> getAvailableRooms() {
        try {
            return roomDAO.findAllAvailable();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Room getRoomById(int id) {
        try {
            return roomDAO.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Room> getAvailableRooms(java.sql.Date checkIn, java.sql.Date checkOut) {
        try {
            return roomDAO.findAvailableRoomsByDate(checkIn, checkOut);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
