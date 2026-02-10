package com.oceanview.controller;

import com.oceanview.model.Room;
import com.oceanview.service.RoomService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

@WebServlet("/searchRooms")
public class SearchRoomsServlet extends HttpServlet {
    private RoomService roomService;

    public void init() { this.roomService = new RoomService(); }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            Date checkIn = Date.valueOf(request.getParameter("checkIn"));
            Date checkOut = Date.valueOf(request.getParameter("checkOut"));
            List<Room> rooms = roomService.getAvailableRooms(checkIn, checkOut);

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < rooms.size(); i++) {
                Room r = rooms.get(i);
                json.append(String.format("{\"id\":%d,\"number\":\"%s\",\"type\":\"%s\",\"price\":%.2f}",
                        r.getId(), r.getRoomNumber(), r.getRoomType(), r.getPricePerNight()));
                if (i < rooms.size() - 1) json.append(",");
            }
            json.append("]");
            out.print(json.toString());
        } catch (Exception e) {
            out.print("[]");
        }
    }
}