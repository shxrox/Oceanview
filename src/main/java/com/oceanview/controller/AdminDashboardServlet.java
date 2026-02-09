package com.oceanview.controller;

import com.oceanview.service.ReservationService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/adminDashboard")
public class AdminDashboardServlet extends HttpServlet {

    private ReservationService reservationService;

    @Override
    public void init() {
        this.reservationService = new ReservationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Get Statistics from Service
        // Returns a map like: { "Single"=5, "Double"=2, "Suite"=1 }
        Map<String, Integer> stats = reservationService.getRoomTypeStats();

        // 2. Attach to request
        request.setAttribute("roomStats", stats);

        // 3. Forward to the Dashboard Page
        request.getRequestDispatcher("admin_dashboard.jsp").forward(request, response);
    }
}