package com.oceanview.controller;

import com.oceanview.model.Room;
import com.oceanview.service.RoomService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

        // 1. Check if dates are present
        String checkIn = request.getParameter("checkIn");
        String checkOut = request.getParameter("checkOut");

        // ROBUSTNESS CHECK: If dates are missing, force user to Step 1
        if (checkIn == null || checkOut == null || checkIn.isEmpty() || checkOut.isEmpty()) {
            response.sendRedirect("wizard_dates.jsp");
            return;
        }

        // 2. Get available rooms
        // (In a real app, we would filter by date here, but for now we show all available rooms)
        List<Room> rooms = roomService.getAvailableRooms();

        // 3. Attach data to request
        request.setAttribute("rooms", rooms);
        // The dates are automatically available in the JSP via request.getParameter,
        // but we can set them explicitly to be safe.
        request.setAttribute("checkIn", checkIn);
        request.setAttribute("checkOut", checkOut);

        // 4. Forward to the Room List (Step 2)
        request.getRequestDispatcher("search_rooms.jsp").forward(request, response);
    }
}