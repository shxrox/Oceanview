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

        // 1. Fetch all reservations (now including Room Number and Price)
        List<Reservation> list = reservationService.getAllReservations(); // Ensure this method exists in Service calling DAO.findAll()

        // 2. Attach to request
        request.setAttribute("invoiceList", list);

        // 3. Forward to JSP
        request.getRequestDispatcher("admin_invoices.jsp").forward(request, response);
    }
}