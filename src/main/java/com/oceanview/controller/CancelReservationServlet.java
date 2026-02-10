package com.oceanview.controller;

import com.oceanview.service.ReservationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cancelReservation")
public class CancelReservationServlet extends HttpServlet {

    private ReservationService reservationService;

    @Override
    public void init() {
        this.reservationService = new ReservationService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean deleted = reservationService.cancelReservation(id);

            if (deleted) {
                response.sendRedirect("viewReservations?msg=Cancelled");
            } else {
                response.sendRedirect("viewReservations?error=Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("viewReservations?error=Error");
        }
    }
}
