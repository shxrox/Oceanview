package com.oceanview.repository;

import com.oceanview.model.Reservation;
import com.oceanview.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationRepositoryImpl implements ReservationRepository {

    // 1. PROCESS BOOKING
    @Override
    public boolean save(Reservation res) throws SQLException {
        String sql = "INSERT INTO reservations (room_id, customer_name, customer_email, customer_phone, check_in, check_out) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, res.getRoomId());
            stmt.setString(2, res.getCustomerName());
            stmt.setString(3, res.getCustomerEmail());
            stmt.setString(4, res.getCustomerPhone());
            stmt.setDate(5, res.getCheckIn());
            stmt.setDate(6, res.getCheckOut());
            return stmt.executeUpdate() > 0;
        }
    }

    // 2. VIEW RESERVATIONS (Receptionist & Admin Invoice List)
    @Override
    public List<Reservation> findAll() throws SQLException {
        List<Reservation> list = new ArrayList<>();
        // Join with Rooms to get Room Number & Price for the Invoice
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

                // Extra fields for Invoice/View
                res.setRoomNumber(rs.getString("room_number"));
                res.setPricePerNight(rs.getDouble("price_per_night"));

                list.add(res);
            }
        }
        return list;
    }

    // 3. CANCEL RESERVATION
    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM reservations WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // 4. ADMIN DASHBOARD: Pie Chart Data
    @Override
    public Map<String, Integer> getRoomTypeCounts() throws SQLException {
        Map<String, Integer> map = new HashMap<>();
        String sql = "SELECT r.room_type, COUNT(res.id) as cnt " +
                "FROM reservations res JOIN rooms r ON res.room_id = r.id " +
                "GROUP BY r.room_type";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while(rs.next()) map.put(rs.getString("room_type"), rs.getInt("cnt"));
        }
        return map;
    }

    @Override
    public Map<String, Double> getRevenueByRoomType() throws SQLException {
        Map<String, Double> map = new HashMap<>();

        // We define the room types we want to calculate
        String[] types = {"Single", "Double", "Suite"};

        // JDBC Syntax to call a Stored Procedure: {CALL ProcedureName(Param1, Param2)}
        String sql = "{CALL GetRoomRevenue(?, ?)}";

        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            for (String type : types) {
                // 1. Pass the Input Parameter (IN p_roomType)
                stmt.setString(1, type);

                // 2. Register the Output Parameter (OUT p_totalRevenue)
                // This tells Java: "Expect a Decimal number back from the database here"
                stmt.registerOutParameter(2, java.sql.Types.DECIMAL);

                // 3. Run the Procedure
                stmt.execute();

                // 4. Retrieve the calculated value
                double revenue = stmt.getDouble(2);
                map.put(type, revenue);
            }
        }
        return map;
    }
}