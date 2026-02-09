package com.oceanview.controller;

import com.oceanview.model.Room;
import com.oceanview.service.RoomService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/bookRoom")
public class BookRoomServlet extends HttpServlet {

    private RoomService roomService;

    @Override
    public void init() {
        this.roomService = new RoomService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("roomId");
        // NEW: Capture dates from URL
        String checkIn = request.getParameter("checkIn");
        String checkOut = request.getParameter("checkOut");

        if (idParam != null) {
            int roomId = Integer.parseInt(idParam);
            Room room = roomService.getRoomById(roomId);

            request.setAttribute("room", room);
            // NEW: Send dates to the JSP form
            request.setAttribute("checkIn", checkIn);
            request.setAttribute("checkOut", checkOut);

            request.getRequestDispatcher("book_room.jsp").forward(request, response);
        } else {
            response.sendRedirect("searchRooms");
        }
    }
}