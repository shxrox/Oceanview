package com.oceanview.dao;

import com.oceanview.model.Reservation;
import java.sql.SQLException;
import java.util.List;

public interface ReservationDAO {
    // Save a new reservation to the database
    boolean save(Reservation reservation) throws SQLException;

    // Retrieve all reservations (for the "View All Bookings" page later)
    List<Reservation> findAll() throws SQLException;
}