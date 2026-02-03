package com.oceanview.controller;

import com.oceanview.service.ReservationService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        // 1. Capture Form Data
        try {
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            String name = request.getParameter("customerName");
            String email = request.getParameter("customerEmail");
            String phone = request.getParameter("customerPhone");

            // 2. Call Service to Process Booking
            boolean isSuccess = reservationService.processBooking(roomId, name, email, phone);

            if (isSuccess) {
                // 3. Success -> Send to Success Page
                response.sendRedirect("booking_success.jsp");
            } else {
                // 4. Failure -> Send back to search with error
                request.setAttribute("errorMessage", "Booking failed. Room might be taken.");
                request.getRequestDispatcher("searchRooms").forward(request, response);
            }

        } catch (NumberFormatException e) {
            response.sendRedirect("searchRooms");
        }
    }
}