package com.oceanview.service;

import com.oceanview.dao.ReservationDAO;
import com.oceanview.dao.ReservationDAOImpl;
import com.oceanview.dao.RoomDAO;
import com.oceanview.dao.RoomDAOImpl;
import com.oceanview.model.Reservation;
import com.oceanview.util.EmailUtility;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ReservationService {

    private ReservationDAO reservationDAO;
    private RoomDAO roomDAO;

    public ReservationService() {
        this.reservationDAO = new ReservationDAOImpl();
        this.roomDAO = new RoomDAOImpl();
    }

    public boolean processBooking(int roomId, String customerName, String customerEmail, String customerPhone, Date checkIn, Date checkOut) {
        try {
            Reservation res = new Reservation(roomId, customerName, customerEmail, customerPhone, checkIn, checkOut);
            boolean isSaved = reservationDAO.save(res);

            if (isSaved) {
                String subject = "Booking Confirmation - Ocean View Resort";
                String message = "Dear " + customerName + ",\n\n" +
                        "Your reservation for Room " + roomId + " has been confirmed.\n\n" +
                        "Check-in: " + checkIn + "\n" +
                        "Check-out: " + checkOut + "\n\n";

                new Thread(() -> EmailUtility.sendEmail(customerEmail, subject, message)).start();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Reservation> getAllReservations() {
        try {
            return reservationDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Integer> getRoomTypeStats() {
        try {
            return reservationDAO.getRoomTypeCounts();
        } catch (SQLException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public boolean cancelReservation(int id) {
        try {
            return reservationDAO.deleteReservation(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ... inside ReservationService.java ...
    public java.util.Map<String, Double> getRevenueStats() {
        try {
            return reservationDAO.getRevenueByRoomType();
        } catch (SQLException e) {
            e.printStackTrace();
            return new java.util.HashMap<>();
        }
    }
}
