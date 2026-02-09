package com.oceanview.controller;

import com.oceanview.model.Room;
import com.oceanview.service.RoomService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/searchRooms")
public class SearchRoomsServlet extends HttpServlet {

    private RoomService roomService;

    @Override
    public void init() {
        this.roomService = new RoomService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String checkInStr = request.getParameter("checkIn");
        String checkOutStr = request.getParameter("checkOut");

        if (checkInStr == null || checkOutStr == null || checkInStr.isEmpty() || checkOutStr.isEmpty()) {
            response.sendRedirect("wizard_dates.jsp");
            return;
        }

        try {
            Date checkIn = Date.valueOf(checkInStr);
            Date checkOut = Date.valueOf(checkOutStr);

            if (checkOut.before(checkIn) || checkOut.equals(checkIn)) {
                response.sendRedirect("wizard_dates.jsp?error=Check-out date must be after Check-in date.");
                return;
            }

            List<Room> rooms = roomService.getAvailableRooms(checkIn, checkOut);

            request.setAttribute("rooms", rooms);
            request.setAttribute("checkIn", checkInStr);
            request.setAttribute("checkOut", checkOutStr);

            request.getRequestDispatcher("search_rooms.jsp").forward(request, response);

        } catch (IllegalArgumentException e) {
            response.sendRedirect("wizard_dates.jsp?error=Invalid date format.");
        }
    }
}
