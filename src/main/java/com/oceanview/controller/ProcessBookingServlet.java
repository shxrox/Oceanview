package com.oceanview.controller;

import com.oceanview.service.ReservationService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/processBooking")
public class ProcessBookingServlet extends HttpServlet {

    private ReservationService reservationService;

    @Override
    public void init() {
        this.reservationService = new ReservationService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. Capture Form Data
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            String name = request.getParameter("customerName");
            String email = request.getParameter("customerEmail");
            String phone = request.getParameter("customerPhone");

            // NEW: Capture Dates (Strings)
            String checkInStr = request.getParameter("checkIn");
            String checkOutStr = request.getParameter("checkOut");

            // Convert Strings to SQL Dates
            Date checkIn = Date.valueOf(checkInStr);
            Date checkOut = Date.valueOf(checkOutStr);

            // 2. Call Service to Process Booking (Updated method)
            boolean isSuccess = reservationService.processBooking(roomId, name, email, phone, checkIn, checkOut);

            if (isSuccess) {
                // 3. Success -> Send to Success Page
                response.sendRedirect("booking_success.jsp");
            } else {
                // 4. Failure -> Send back to search with error
                request.setAttribute("errorMessage", "Booking failed. Room might be taken.");
                request.getRequestDispatcher("searchRooms").forward(request, response);
            }

        } catch (IllegalArgumentException e) {
            // Handles bad date formats or numbers
            response.sendRedirect("searchRooms");
        }
    }
}