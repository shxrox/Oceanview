package com.oceanview.controller;

import com.oceanview.model.Reservation;
import com.oceanview.model.Room;
import com.oceanview.service.ReservationService;
import com.oceanview.service.RoomService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebServlet("/calendarData")
public class CalendarServlet extends HttpServlet {
    private ReservationService reservationService;
    private RoomService roomService;

    public void init() {
        this.reservationService = new ReservationService();
        this.roomService = new RoomService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            List<Room> allRooms = roomService.getAllRooms();
            List<Reservation> allRes = reservationService.getAllReservations();

            // SORTING: Group by Type (Single, Double...), then by Number (101, 102...)
            Collections.sort(allRooms, new Comparator<Room>() {
                public int compare(Room r1, Room r2) {
                    int typeCompare = r1.getRoomType().compareTo(r2.getRoomType());
                    if (typeCompare != 0) return typeCompare;
                    return r1.getRoomNumber().compareTo(r2.getRoomNumber());
                }
            });

            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < allRooms.size(); i++) {
                Room room = allRooms.get(i);

                json.append(String.format("{\"id\":%d, \"number\":\"%s\", \"type\":\"%s\", \"bookings\":[",
                        room.getId(), room.getRoomNumber(), room.getRoomType()));

                boolean first = true;
                for (Reservation r : allRes) {
                    if (r.getRoomId() == room.getId()) {
                        if (!first) json.append(",");
                        // Send simple dates
                        json.append(String.format("{\"start\":\"%s\", \"end\":\"%s\", \"guest\":\"%s\"}",
                                r.getCheckIn(), r.getCheckOut(), r.getCustomerName()));
                        first = false;
                    }
                }

                json.append("]}");
                if (i < allRooms.size() - 1) json.append(",");
            }
            json.append("]");
            out.print(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            out.print("[]");
        }
    }
}