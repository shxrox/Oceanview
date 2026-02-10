package com.oceanview.repository;
import com.oceanview.model.Reservation;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ReservationRepository {
    boolean save(Reservation reservation) throws SQLException;
    List<Reservation> findAll() throws SQLException;
    boolean delete(int id) throws SQLException;

    // Admin Dashboard Stats
    Map<String, Integer> getRoomTypeCounts() throws SQLException;
    Map<String, Double> getRevenueByRoomType() throws SQLException;
}