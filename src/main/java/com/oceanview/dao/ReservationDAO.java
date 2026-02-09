package com.oceanview.dao;

import com.oceanview.model.Reservation;
import java.sql.SQLException;
import java.util.List;

public interface ReservationDAO {

    boolean save(Reservation reservation) throws SQLException;

    List<Reservation> findAll() throws SQLException;

    java.util.Map<String, Integer> getRoomTypeCounts() throws SQLException;
}
