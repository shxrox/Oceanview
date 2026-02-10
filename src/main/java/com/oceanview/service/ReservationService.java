package com.oceanview.service;

// FIXED IMPORTS
import com.oceanview.repository.ReservationRepository;
import com.oceanview.repository.ReservationRepositoryImpl;
import com.oceanview.model.Reservation;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationService {
    private ReservationRepository reservationRepository;
    private EmailService emailService;

    public ReservationService() {
        this.reservationRepository = new ReservationRepositoryImpl();
        this.emailService = new EmailService();
    }

    public boolean processBooking(int roomId, String name, String email, String phone, Date checkIn, Date checkOut) {
        try {
            Reservation res = new Reservation(roomId, name, email, phone, checkIn, checkOut);
            boolean isSaved = reservationRepository.save(res);

            if (isSaved) {
                emailService.sendBookingConfirmation(res);
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Reservation> getAllReservations() {
        try {
            return reservationRepository.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public boolean cancelReservation(int id) {
        try {
            return reservationRepository.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, Integer> getRoomTypeCounts() {
        try {
            return reservationRepository.getRoomTypeCounts();
        } catch (SQLException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public Map<String, Double> getRevenueStats() {
        try {
            return reservationRepository.getRevenueByRoomType();
        } catch (SQLException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}