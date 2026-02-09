package com.oceanview.dao;

import com.oceanview.model.Room;
import com.oceanview.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {

    @Override
    public List<Room> getAllRooms() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getInt("id"));
                room.setRoomNumber(rs.getString("room_number"));
                room.setRoomType(rs.getString("room_type"));

                // FIXED: Use "price_per_night"
                room.setPricePerNight(rs.getDouble("price_per_night"));

                room.setAvailable(rs.getBoolean("is_available"));
                rooms.add(room);
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
                Room room = new Room();
                room.setId(rs.getInt("id"));
                room.setRoomNumber(rs.getString("room_number"));
                room.setRoomType(rs.getString("room_type"));

                // FIXED: Use "price_per_night"
                room.setPricePerNight(rs.getDouble("price_per_night"));

                room.setAvailable(rs.getBoolean("is_available"));
                return room;
            }
        }
        return null;
    }

    @Override
    public void updateAvailability(int id, boolean isAvailable) throws SQLException {
        String sql = "UPDATE rooms SET is_available = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, isAvailable);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Room> findAvailableRoomsByDate(java.sql.Date checkIn, java.sql.Date checkOut) throws SQLException {
        List<Room> rooms = new ArrayList<>();

        // This query finds rooms that DO NOT have a reservation overlapping the requested dates.
        String sql = "SELECT * FROM rooms r WHERE r.id NOT IN (" +
                "    SELECT res.room_id FROM reservations res " +
                "    WHERE res.check_in < ? AND res.check_out > ? " +
                ")";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, checkOut);
            stmt.setDate(2, checkIn);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getInt("id"));
                room.setRoomNumber(rs.getString("room_number"));
                room.setRoomType(rs.getString("room_type"));

                // FIXED: Use "price_per_night"
                room.setPricePerNight(rs.getDouble("price_per_night"));

                // Logic: If it's returned by this query, it is available for these dates
                room.setAvailable(true);
                rooms.add(room);
            }
        }
        return rooms;
    }
}