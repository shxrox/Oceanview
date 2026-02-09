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

    // UPDATED: Added checkIn and checkOut parameters
    public boolean processBooking(int roomId, String customerName, String customerEmail, String customerPhone, Date checkIn, Date checkOut) {
        try {
            // 1. Double check: Is the room still available?
            Room room = roomDAO.findById(roomId);
            if (room == null || !room.isAvailable()) {
                return false;
            }

            // 2. Create the Reservation object (UPDATED)
            Reservation res = new Reservation(roomId, customerName, customerEmail, customerPhone, checkIn, checkOut);

            // 3. Save to Database
            boolean isSaved = reservationDAO.save(res);

            if (isSaved) {
                // 4. Mark room as occupied
                roomDAO.updateAvailability(roomId, false);

                // 5. Send Confirmation Email (UPDATED Message)
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
}