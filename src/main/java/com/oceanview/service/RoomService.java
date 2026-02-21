package com.oceanview.service;

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
        try { return roomRepository.findById(id); }
        catch (SQLException e) { return null; }
    }

    public List<Room> getAllRooms() {
        try { return roomRepository.findAll(); }
        catch (Exception e) { return Collections.emptyList(); }
    }

    public boolean addNewRoom(Room room) {
        try { return roomRepository.save(room); }
        catch (SQLException e) { return false; }
    }

    public boolean updateRoomPrice(int roomId, double newPrice) {
        try { return roomRepository.updatePrice(roomId, newPrice); }
        catch (SQLException e) { return false; }
    }

    public boolean updateRoomFull(Room room) {
        try { return roomRepository.updateRoom(room); }
        catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // NEW METHOD: Delete Room
    public boolean deleteRoom(int id) {
        try { return roomRepository.delete(id); }
        catch (SQLException e) {
            e.printStackTrace();
            return false; // Returns false if it fails (e.g., if a room has past reservations attached)
        }
    }
}