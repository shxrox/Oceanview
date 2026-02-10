package com.oceanview.controller;

import com.oceanview.service.ReservationService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/cancelReservation")
public class CancelReservationServlet extends HttpServlet {
    private ReservationService reservationService;

    public void init() { this.reservationService = new ReservationService(); }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean deleted = reservationService.cancelReservation(id);
            if(deleted) response.getWriter().print("{\"status\":\"success\"}");
            else response.getWriter().print("{\"status\":\"error\"}");
        } catch (Exception e) {
            response.getWriter().print("{\"status\":\"error\"}");
        }
    }
}