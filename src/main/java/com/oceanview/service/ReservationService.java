package com.oceanview.service;

import com.oceanview.dao.ReservationDAO;
import com.oceanview.dao.ReservationDAOImpl;
import com.oceanview.dao.RoomDAO;
import com.oceanview.dao.RoomDAOImpl;
import com.oceanview.model.Reservation;
import com.oceanview.model.Room;
import com.oceanview.util.EmailUtility;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ReservationService {

    private ReservationDAO reservationDAO;
    private RoomDAO roomDAO;

    public ReservationService() {
        this.reservationDAO = new ReservationDAOImpl();
        this.roomDAO = new RoomDAOImpl();
    }

    public boolean processBooking(int roomId, String customerName, String customerEmail, String customerPhone, Date checkIn, Date checkOut) {
        try {
            Room room = roomDAO.findById(roomId);
            if (room == null || !room.isAvailable()) {
                return false;
            }

            Reservation res = new Reservation(roomId, customerName, customerEmail, customerPhone, checkIn, checkOut);

            boolean isSaved = reservationDAO.save(res);

            if (isSaved) {
                roomDAO.updateAvailability(roomId, false);

                String subject = "Booking Confirmation - Ocean View Resort";
                String message = "Dear " + customerName + ",\n\n" +
                        "Thank you for choosing Ocean View Resort.\n" +
                        "Your reservation for Room " + room.getRoomNumber() + " (" + room.getRoomType() + ") has been confirmed.\n\n" +
                        "Check-in: " + checkIn + "\n" +
                        "Check-out: " + checkOut + "\n" +
                        "Price per night: $" + room.getPricePerNight() + "\n\n" +
                        "We look forward to hosting you!";

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

    public java.util.Map<String, Integer> getRoomTypeStats() {
        try {
            return reservationDAO.getRoomTypeCounts();
        } catch (SQLException e) {
            e.printStackTrace();
            return new java.util.HashMap<>();
        }
    }
}
