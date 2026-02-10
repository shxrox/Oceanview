package com.oceanview.service;

// FIXED IMPORTS
import com.oceanview.repository.RoomRepository;
import com.oceanview.repository.RoomRepositoryImpl;
import com.oceanview.model.Room;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class RoomService {
    private RoomRepository roomRepository;

    public RoomService() {
        this.roomRepository = new RoomRepositoryImpl();
    }

    public List<Room> getAvailableRooms(Date checkIn, Date checkOut) {
        try {
            if (checkOut.before(checkIn) || checkOut.equals(checkIn)) {
                return Collections.emptyList();
            }
            return roomRepository.findAvailableRooms(checkIn, checkOut);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Room getRoomById(int id) {
        try {
            return roomRepository.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}