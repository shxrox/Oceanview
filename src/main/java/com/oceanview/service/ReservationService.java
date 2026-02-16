package com.oceanview.service;

import com.oceanview.repository.*;
import com.oceanview.model.Reservation;
import com.oceanview.model.Room;
import com.oceanview.model.User; // Import User for profile update

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit; // Needed for date calculation

public class ReservationService {
    private ReservationRepository reservationRepository;
    private RoomRepository roomRepository; // Used to get Room Price
    private UserRepository userRepository; // Used for Profile Update
    private EmailService emailService;

    public ReservationService() {
        this.reservationRepository = new ReservationRepositoryImpl();
        this.roomRepository = new RoomRepositoryImpl();
        this.userRepository = new UserRepositoryImpl();
        this.emailService = new EmailService();
    }

    // --- 1. PROCESS BOOKING (With Price Calculation) ---
    public boolean processBooking(int roomId, String name, String email, String phone, Date checkIn, Date checkOut) {
        try {
            // A. Save the Reservation
            Reservation res = new Reservation(roomId, name, email, phone, checkIn, checkOut);
            boolean isSaved = reservationRepository.save(res);

            if (isSaved) {
                // B. Calculate Total Price (Days * PricePerNight)
                Room room = roomRepository.findById(roomId);

                long diffInMillies = Math.abs(checkOut.getTime() - checkIn.getTime());
                long diffDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                // Ensure at least 1 day counts (even if same day booking)
                if (diffDays < 1) diffDays = 1;

                double totalAmount = diffDays * room.getPricePerNight();

                // C. Send Email with the Total Amount
                emailService.sendBookingConfirmation(res, totalAmount, room.getRoomNumber());
                emailService.sendSMS(phone, "Booking Confirmed! Room " + room.getRoomNumber() + ". Total: $" + totalAmount);

                return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- 2. UPDATE USER PROFILE ---
    public boolean updateUserProfile(User currentUser, String newName, String newPassword) throws SQLException {
        currentUser.setName(newName);
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            String hashed = org.mindrot.jbcrypt.BCrypt.hashpw(newPassword, org.mindrot.jbcrypt.BCrypt.gensalt());
            currentUser.setPassword(hashed);
        }
        return userRepository.update(currentUser);
    }

    // --- 3. OTHER METHODS (Keep these as they are) ---
    public List<Reservation> getAllReservations() {
        try { return reservationRepository.findAll(); }
        catch (SQLException e) { return Collections.emptyList(); }
    }

    public boolean cancelReservation(int id) {
        try { return reservationRepository.delete(id); }
        catch (SQLException e) { return false; }
    }

    public Map<String, Integer> getRoomTypeCounts() {
        try { return reservationRepository.getRoomTypeCounts(); }
        catch (SQLException e) { return new HashMap<>(); }
    }

    public Map<String, Double> getRevenueStats() {
        try { return reservationRepository.getRevenueByRoomType(); }
        catch (SQLException e) { return new HashMap<>(); }
    }
}