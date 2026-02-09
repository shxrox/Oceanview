package com.oceanview.controller;

import com.oceanview.service.ReservationService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Date;

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

        try {
            // 1. Capture Form Data
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            String name = request.getParameter("customerName");
            String email = request.getParameter("customerEmail");
            String phone = request.getParameter("customerPhone");
            String checkInStr = request.getParameter("checkIn");
            String checkOutStr = request.getParameter("checkOut");

            // 2. Convert Strings to SQL Dates
            Date checkIn = Date.valueOf(checkInStr);
            Date checkOut = Date.valueOf(checkOutStr);

            // 3. Process the Booking
            boolean isSuccess = reservationService.processBooking(
                    roomId, name, email, phone, checkIn, checkOut
            );

            if (isSuccess) {
                // 4. Success -> Redirect with parameters
                String encodedName = URLEncoder.encode(name, "UTF-8");
                String encodedEmail = URLEncoder.encode(email, "UTF-8");
                String encodedPhone = URLEncoder.encode(phone, "UTF-8");

                response.sendRedirect("booking_success.jsp" +
                        "?status=success" +
                        "&id=" + roomId +
                        "&name=" + encodedName +
                        "&email=" + encodedEmail +
                        "&phone=" + encodedPhone +
                        "&in=" + checkInStr +
                        "&out=" + checkOutStr
                );
            } else {
                // 5. Failure -> Forward back to search page with error message
                request.setAttribute("errorMessage", "Booking failed. Room might be taken.");
                request.getRequestDispatcher("searchRooms").forward(request, response);
            }

        } catch (IllegalArgumentException e) {
            // Handles bad date formats or invalid numbers
            response.sendRedirect("searchRooms?error=Invalid+input");
        } catch (Exception e) {
            // Catch-all for any unexpected errors
            e.printStackTrace();
            response.sendRedirect("searchRooms?error=Server+error");
        }
    }
}
