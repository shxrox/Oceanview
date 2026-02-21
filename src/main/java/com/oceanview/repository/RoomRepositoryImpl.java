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
                        rs.getInt("id"), rs.getString("room_number"), rs.getString("room_type"),
                        rs.getDouble("price_per_night"), true, rs.getString("image_url"), rs.getString("description")
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
                        rs.getInt("id"), rs.getString("room_number"), rs.getString("room_type"),
                        rs.getDouble("price_per_night"), true, rs.getString("image_url"), rs.getString("description")
                );
            }
        }
        return null;
    }

    @Override
    public List<Room> findAll() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms ORDER BY room_number";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                rooms.add(new Room(
                        rs.getInt("id"), rs.getString("room_number"), rs.getString("room_type"),
                        rs.getDouble("price_per_night"), true, rs.getString("image_url"), rs.getString("description")
                ));
            }
        }
        return rooms;
    }

    @Override
    public boolean save(Room room) throws SQLException {
        String sql = "INSERT INTO rooms (room_number, room_type, price_per_night, is_available, image_url, description) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, room.getRoomNumber());
            stmt.setString(2, room.getRoomType());
            stmt.setDouble(3, room.getPricePerNight());
            stmt.setBoolean(4, true);
            stmt.setString(5, room.getImageUrl());
            stmt.setString(6, room.getDescription());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updatePrice(int roomId, double newPrice) throws SQLException {
        String sql = "UPDATE rooms SET price_per_night = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newPrice);
            stmt.setInt(2, roomId);
            return stmt.executeUpdate() > 0;
        }
    }

    // NEW METHOD: Update all details
    @Override
    public boolean updateRoom(Room room) throws SQLException {
        String sql = "UPDATE rooms SET room_number=?, room_type=?, price_per_night=?, image_url=?, description=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, room.getRoomNumber());
            stmt.setString(2, room.getRoomType());
            stmt.setDouble(3, room.getPricePerNight());
            stmt.setString(4, room.getImageUrl());
            stmt.setString(5, room.getDescription());
            stmt.setInt(6, room.getId());
            return stmt.executeUpdate() > 0;
        }
    }
}