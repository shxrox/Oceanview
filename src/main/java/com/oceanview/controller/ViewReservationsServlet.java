package com.oceanview.controller;

import com.oceanview.model.Reservation;
import com.oceanview.service.ReservationService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/viewReservations")
public class ViewReservationsServlet extends HttpServlet {
    private ReservationService reservationService;

    public void init() { this.reservationService = new ReservationService(); }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            List<Reservation> list = reservationService.getAllReservations();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < list.size(); i++) {
                Reservation r = list.get(i);
                json.append(String.format("{\"id\":%d,\"roomNumber\":\"%s\",\"customerName\":\"%s\",\"checkIn\":\"%s\",\"checkOut\":\"%s\"}",
                        r.getId(), r.getRoomNumber(), r.getCustomerName(), r.getCheckIn(), r.getCheckOut()));
                if (i < list.size() - 1) json.append(",");
            }
            json.append("]");
            out.print(json.toString());
        } catch (Exception e) {
            out.print("[]");
        }
    }
}