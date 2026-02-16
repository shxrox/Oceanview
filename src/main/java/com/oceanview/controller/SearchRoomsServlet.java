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
        response.setCharacterEncoding("UTF-8"); // Important for descriptions
        PrintWriter out = response.getWriter();

        try {
            Date checkIn = Date.valueOf(request.getParameter("checkIn"));
            Date checkOut = Date.valueOf(request.getParameter("checkOut"));
            List<Room> rooms = roomService.getAvailableRooms(checkIn, checkOut);

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < rooms.size(); i++) {
                Room r = rooms.get(i);

                // Safety: Handle nulls for old database rows
                String img = (r.getImageUrl() != null) ? r.getImageUrl() : "https://via.placeholder.com/300";
                String desc = (r.getDescription() != null) ? r.getDescription().replace("\"", "\\\"") : "No description available.";

                json.append(String.format(
                        "{\"id\":%d, \"number\":\"%s\", \"type\":\"%s\", \"price\":%.2f, \"image\":\"%s\", \"description\":\"%s\"}",
                        r.getId(), r.getRoomNumber(), r.getRoomType(), r.getPricePerNight(), img, desc
                ));

                if (i < rooms.size() - 1) json.append(",");
            }
            json.append("]");
            out.print(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
            out.print("[]");
        }
    }
}