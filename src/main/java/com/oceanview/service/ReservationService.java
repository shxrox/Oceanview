package com.oceanview.service;

import com.oceanview.repository.*;
import com.oceanview.model.Reservation;
import com.oceanview.model.Room;
import com.oceanview.model.User;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ReservationService {
    private ReservationRepository reservationRepository;
    private RoomRepository roomRepository;
    private UserRepository userRepository;
    private EmailService emailService;

    public ReservationService() {
        this.reservationRepository = new ReservationRepositoryImpl();
        this.roomRepository = new RoomRepositoryImpl();
        this.userRepository = new UserRepositoryImpl();
        this.emailService = new EmailService();
    }

    // --- 1. PROCESS BOOKING (With Price Calculation + Email + SMS) ---
    public boolean processBooking(int roomId, String name, String email, String phone, Date checkIn, Date checkOut) {
        try {
            // A. Create Reservation Object
            Reservation res = new Reservation(roomId, name, email, phone, checkIn, checkOut);

            // B. Save to Database
            boolean isSaved = reservationRepository.save(res);

            if (isSaved) {
                // C. Calculate Total Price
                Room room = roomRepository.findById(roomId);

                long diffInMillies = Math.abs(checkOut.getTime() - checkIn.getTime());
                long diffDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                if (diffDays < 1) diffDays = 1; // Minimum 1 night charge

                double totalAmount = diffDays * room.getPricePerNight();

                // D. Send Notifications

                // 1. Send Email
                emailService.sendBookingConfirmation(res, totalAmount, room.getRoomNumber());

                // 2. Send SMS
                String smsMessage = "OceanView: Confirmed! Room " + room.getRoomNumber() + " is booked. Total: $" + String.format("%.2f", totalAmount);
                emailService.sendSMS(phone, smsMessage);

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

    // --- 3. OTHER METHODS ---
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