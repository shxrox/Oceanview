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

        // 1. Get available rooms from Service
        List<Room> availableRooms = roomService.getAvailableRooms();

        // 2. Attach the list to the request so JSP can see it
        request.setAttribute("rooms", availableRooms);

        // 3. Forward to the JSP page
        request.getRequestDispatcher("search_rooms.jsp").forward(request, response);
    }
}