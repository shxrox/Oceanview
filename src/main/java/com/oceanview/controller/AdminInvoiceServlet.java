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
import java.util.Map;

@WebServlet("/adminInvoices")
public class AdminInvoiceServlet extends HttpServlet {

    private ReservationService reservationService;

    @Override
    public void init() {
        this.reservationService = new ReservationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Fetch Lists (Table Data)
        List<Reservation> list = reservationService.getAllReservations();

        // 2. Fetch Stats (Chart Data)
        Map<String, Double> revenueStats = reservationService.getRevenueStats();

        // 3. Attach both to request
        request.setAttribute("invoiceList", list);
        request.setAttribute("revenueStats", revenueStats);

        // 4. Forward to JSP
        request.getRequestDispatcher("admin_invoices.jsp").forward(request, response);
    }
}