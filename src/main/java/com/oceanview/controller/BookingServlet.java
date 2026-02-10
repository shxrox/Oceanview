package com.oceanview.controller;

import com.oceanview.service.ReservationService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

@WebServlet("/bookRoom")
public class BookingServlet extends HttpServlet {
    private ReservationService reservationService;

    public void init() { this.reservationService = new ReservationService(); }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            Date checkIn = Date.valueOf(request.getParameter("checkIn"));
            Date checkOut = Date.valueOf(request.getParameter("checkOut"));

            boolean success = reservationService.processBooking(roomId, name, email, phone, checkIn, checkOut);

            if (success) out.print("{\"status\":\"success\"}");
            else out.print("{\"status\":\"error\", \"message\":\"Room unavailable\"}");
        } catch (Exception e) {
            out.print("{\"status\":\"error\", \"message\":\"Invalid Data\"}");
        }
    }
}