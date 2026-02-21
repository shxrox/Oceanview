package com.oceanview.controller;

import com.oceanview.model.Room;
import com.oceanview.service.RoomService;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/roomManagement")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class RoomManagementServlet extends HttpServlet {
    private RoomService roomService;

    public void init() {
        this.roomService = new RoomService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            List<Room> rooms = roomService.getAllRooms();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < rooms.size(); i++) {
                Room r = rooms.get(i);

                String desc = r.getDescription() != null ? r.getDescription().replace("\"", "\\\"").replace("\n", " ") : "";
                String img = r.getImageUrl() != null ? r.getImageUrl() : "images/icon1.png";

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        response.setContentType("application/json");

        try {
            if ("add".equals(action)) {
                Room newRoom = new Room();
                newRoom.setRoomNumber(request.getParameter("number"));
                newRoom.setRoomType(request.getParameter("type"));
                newRoom.setPricePerNight(Double.parseDouble(request.getParameter("price")));
                newRoom.setDescription(request.getParameter("description"));

                Part filePart = request.getPart("image");
                String imagePath = uploadImage(filePart, request);
                newRoom.setImageUrl(imagePath != null ? imagePath : "images/icon1.png");

                boolean success = roomService.addNewRoom(newRoom);
                response.getWriter().print(success ? "{\"status\":\"success\"}" : "{\"status\":\"error\"}");

            } else if ("edit".equals(action)) {
                int roomId = Integer.parseInt(request.getParameter("id"));
                Room existingRoom = roomService.getRoomById(roomId);

                if (existingRoom != null) {
                    existingRoom.setRoomNumber(request.getParameter("number"));
                    existingRoom.setRoomType(request.getParameter("type"));
                    existingRoom.setPricePerNight(Double.parseDouble(request.getParameter("price")));
                    existingRoom.setDescription(request.getParameter("description"));

                    Part filePart = request.getPart("image");
                    String imagePath = uploadImage(filePart, request);
                    if (imagePath != null) {
                        existingRoom.setImageUrl(imagePath);
                    }

                    boolean success = roomService.updateRoomFull(existingRoom);
                    response.getWriter().print(success ? "{\"status\":\"success\"}" : "{\"status\":\"error\"}");
                } else {
                    response.getWriter().print("{\"status\":\"error\"}");
                }

            } else if ("delete".equals(action)) {
                // NEW ACTION: Delete Room
                int roomId = Integer.parseInt(request.getParameter("id"));
                boolean success = roomService.deleteRoom(roomId);
                if (success) {
                    response.getWriter().print("{\"status\":\"success\"}");
                } else {
                    // Usually fails if the room is attached to reservations in the database (Foreign Key Constraint)
                    response.getWriter().print("{\"status\":\"error\", \"message\":\"Cannot delete room! It has past or active bookings.\"}");
                }

            } else if ("update_price".equals(action)) {
                int roomId = Integer.parseInt(request.getParameter("id"));
                double newPrice = Double.parseDouble(request.getParameter("price"));
                boolean success = roomService.updateRoomPrice(roomId, newPrice);
                response.getWriter().print(success ? "{\"status\":\"success\"}" : "{\"status\":\"error\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("{\"status\":\"error\"}");
        }
    }

    private String uploadImage(Part filePart, HttpServletRequest request) throws IOException {
        if (filePart == null || filePart.getSize() == 0) return null;
        String fileName = java.nio.file.Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        if (fileName == null || fileName.isEmpty()) return null;

        String uploadPath = request.getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        String uniqueFileName = System.currentTimeMillis() + "_" + fileName.replaceAll("\\s+", "_");
        filePart.write(uploadPath + File.separator + uniqueFileName);

        return "uploads/" + uniqueFileName;
    }
}