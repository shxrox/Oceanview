package com.oceanview.repository;

import com.oceanview.model.Room;
import com.oceanview.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepositoryImpl implements RoomRepository {
    @Override
    public List<Room> findAvailableRooms(Date checkIn, Date checkOut) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        // Logic: Select rooms NOT inside the reservations table for these dates
        String sql = "SELECT * FROM rooms WHERE id NOT IN (" +
                "SELECT room_id FROM reservations WHERE " +
                "(check_in < ? AND check_out > ?))";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, checkOut);
            stmt.setDate(2, checkIn);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rooms.add(new Room(
                        rs.getInt("id"),
                        rs.getString("room_number"),
                        rs.getString("room_type"),
                        rs.getDouble("price_per_night"),
                        true
                ));
            }
        }
        return rooms;
    }

    @Override
    public Room findById(int id) throws SQLException {
        String sql = "SELECT * FROM rooms WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Room(
                        rs.getInt("id"),
                        rs.getString("room_number"),
                        rs.getString("room_type"),
                        rs.getDouble("price_per_night"),
                        true
                );
            }
        }
        return null;
    }
}