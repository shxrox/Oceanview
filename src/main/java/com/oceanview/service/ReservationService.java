package com.oceanview.service;

import com.oceanview.dao.ReservationDAO;
import com.oceanview.dao.ReservationDAOImpl;
import com.oceanview.dao.RoomDAO;
import com.oceanview.dao.RoomDAOImpl;
import com.oceanview.model.Reservation;
import com.oceanview.model.Room;
import com.oceanview.util.EmailUtility;

import java.sql.SQLException;
import java.util.List;

public class ReservationService {

    private ReservationDAO reservationDAO;
    private RoomDAO roomDAO;

    public ReservationService() {
        this.reservationDAO = new ReservationDAOImpl();
        this.roomDAO = new RoomDAOImpl();
    }

    public boolean processBooking(int roomId, String customerName, String customerEmail, String customerPhone) {
        try {
            // 1. Double check: Is the room still available?
            Room room = roomDAO.findById(roomId);
            if (room == null || !room.isAvailable()) {
                return false; // Room was just taken or doesn't exist
            }

            // 2. Create the Reservation object
            Reservation res = new Reservation(roomId, customerName, customerEmail, customerPhone);

            // 3. Save to Database
            boolean isSaved = reservationDAO.save(res);

            if (isSaved) {
                // 4. IMPORTANT: Mark the room as occupied (Unavailable)
                roomDAO.updateAvailability(roomId, false);

                // 5. Send Confirmation Email (File Logger)
                String subject = "Booking Confirmation - Ocean View Resort";
                String message = "Dear " + customerName + ",\n\n" +
                        "Thank you for choosing Ocean View Resort.\n" +
                        "Your reservation for Room " + room.getRoomNumber() + " (" + room.getRoomType() + ") has been confirmed.\n\n" +
                        "Price per night: $" + room.getPricePerNight() + "\n\n" +
                        "We look forward to hosting you!";

                // Run email in background so page loads fast
                new Thread(() -> EmailUtility.sendEmail(customerEmail, subject, message)).start();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper to view all bookings
    public List<Reservation> getAllReservations() {
        try {
            return reservationDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}