package com.oceanview.controller;

import com.oceanview.model.Reservation;
import com.oceanview.service.ReservationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewReservations")
public class ViewReservationsServlet extends HttpServlet {

    private ReservationService reservationService;

    @Override
    public void init() {
        this.reservationService = new ReservationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Get all reservations from Service
        List<Reservation> list = reservationService.getAllReservations();

        // 2. Attach list to request
        request.setAttribute("reservationList", list);

        // 3. Forward to the JSP page
        request.getRequestDispatcher("view_reservations.jsp").forward(request, response);
    }
}