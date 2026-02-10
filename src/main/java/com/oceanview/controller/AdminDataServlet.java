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
import java.util.Map;

@WebServlet("/adminData")
public class AdminDataServlet extends HttpServlet {
    private ReservationService reservationService;

    public void init() { this.reservationService = new ReservationService(); }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            Map<String, Integer> pie = reservationService.getRoomTypeCounts();
            Map<String, Double> bar = reservationService.getRevenueStats();
            List<Reservation> invoices = reservationService.getAllReservations();

            StringBuilder json = new StringBuilder("{");

            // Pie Data
            json.append("\"pieData\":[");
            int c = 0;
            for(Map.Entry<String,Integer> e : pie.entrySet()){
                json.append(String.format("{\"type\":\"%s\",\"count\":%d}", e.getKey(), e.getValue()));
                if(++c < pie.size()) json.append(",");
            }
            json.append("],");

            // Bar Data
            json.append("\"barData\":[");
            c = 0;
            for(Map.Entry<String,Double> e : bar.entrySet()){
                json.append(String.format("{\"type\":\"%s\",\"revenue\":%.2f}", e.getKey(), e.getValue()));
                if(++c < bar.size()) json.append(",");
            }
            json.append("],");

            // Invoices
            json.append("\"invoices\":[");
            for(int i=0; i<invoices.size(); i++){
                Reservation r = invoices.get(i);
                json.append(String.format("{\"id\":%d,\"customer\":\"%s\",\"room\":\"%s\",\"checkIn\":\"%s\",\"checkOut\":\"%s\",\"price\":%.2f}",
                        r.getId(), r.getCustomerName(), r.getRoomNumber(), r.getCheckIn(), r.getCheckOut(), r.getPricePerNight()));
                if(i < invoices.size()-1) json.append(",");
            }
            json.append("]");

            json.append("}");
            out.print(json.toString());

        } catch (Exception e) {
            out.print("{\"error\":\"Server Error\"}");
        }
    }
}