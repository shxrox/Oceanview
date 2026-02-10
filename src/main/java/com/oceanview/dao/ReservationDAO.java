package com.oceanview.dao;

import com.oceanview.model.Reservation;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ReservationDAO {
    boolean save(Reservation reservation) throws SQLException;
    List<Reservation> findAll() throws SQLException;
    Map<String, Integer> getRoomTypeCounts() throws SQLException;

    // NEW: Add this line so the Service can see it
    boolean deleteReservation(int id) throws SQLException;
}