package com.oceanview.dao;

import com.oceanview.model.Reservation;
import com.oceanview.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {

    @Override
    public boolean save(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservations (room_id, customer_name, customer_email, customer_phone, check_in, check_out) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservation.getRoomId());
            stmt.setString(2, reservation.getCustomerName());
            stmt.setString(3, reservation.getCustomerEmail());
            stmt.setString(4, reservation.getCustomerPhone());
            stmt.setDate(5, reservation.getCheckIn());
            stmt.setDate(6, reservation.getCheckOut());

            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public List<Reservation> findAll() throws SQLException {
        List<Reservation> list = new ArrayList<>();

        // JOIN reservations with rooms to get room_number and price
        String sql = "SELECT res.*, r.room_number, r.price_per_night " +
                "FROM reservations res " +
                "JOIN rooms r ON res.room_id = r.id " +
                "ORDER BY res.booking_date DESC";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Reservation res = new Reservation();
                res.setId(rs.getInt("id"));
                res.setRoomId(rs.getInt("room_id"));
                res.setCustomerName(rs.getString("customer_name"));
                res.setCustomerEmail(rs.getString("customer_email"));
                res.setCustomerPhone(rs.getString("customer_phone"));
                res.setCheckIn(rs.getDate("check_in"));
                res.setCheckOut(rs.getDate("check_out"));
                res.setBookingDate(rs.getTimestamp("booking_date"));

                // NEW: Set the joined data
                res.setRoomNumber(rs.getString("room_number"));
                res.setPricePerNight(rs.getDouble("price_per_night"));

                list.add(res);
            }
        }
        return list;
    }

    public java.util.Map<String, Integer> getRoomTypeCounts() throws SQLException {
        java.util.Map<String, Integer> stats = new java.util.HashMap<>();

        String sql = "SELECT r.room_type, COUNT(res.id) as total " +
                "FROM reservations res " +
                "JOIN rooms r ON res.room_id = r.id " +
                "GROUP BY r.room_type";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String type = rs.getString("room_type");
                int count = rs.getInt("total");
                stats.put(type, count);
            }
        }
        return stats;
    }


}
