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

        // 1. Get the Room ID from the URL (e.g., ?roomId=101)
        String idParam = request.getParameter("roomId");

        if (idParam != null) {
            int roomId = Integer.parseInt(idParam);

            // 2. Find the Room details
            Room room = roomService.getRoomById(roomId);

            // 3. Send Room to the JSP Form
            request.setAttribute("room", room);
            request.getRequestDispatcher("book_room.jsp").forward(request, response);
        } else {
            response.sendRedirect("searchRooms");
        }
    }
}