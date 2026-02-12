package com.oceanview.controller;

import com.oceanview.model.User;
import com.oceanview.service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/userManagement")
public class UserManagementServlet extends HttpServlet {
    private UserService userService;

    public void init() {
        this.userService = new UserService();
    }

    // --- GET: Load the Table ---
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("🔍 API: Request received for /userManagement");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            List<User> users = userService.getAllUsers();
            System.out.println("✅ API: Found " + users.size() + " users.");

            // Manual JSON Construction
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < users.size(); i++) {
                User u = users.get(i);
                // Be careful with quotes in strings
                json.append(String.format(
                        "{\"id\":%d, \"name\":\"%s\", \"email\":\"%s\", \"role\":\"%s\"}",
                        u.getId(),
                        u.getName().replace("\"", "\\\""), // Escape quotes in names
                        u.getEmail(),
                        u.getRole()
                ));

                if (i < users.size() - 1) {
                    json.append(",");
                }
            }
            json.append("]");

            out.print(json.toString());

        } catch (Exception e) {
            e.printStackTrace(); // Print error to IntelliJ Console
            response.setStatus(500); // Tell frontend there was an error
            out.print("{\"error\": \"Server Error\"}");
        }
    }

    // --- POST: Delete User ---
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String idStr = request.getParameter("id");

        System.out.println("🗑 API: Delete request for ID: " + idStr);

        if ("delete".equals(action) && idStr != null) {
            int id = Integer.parseInt(idStr);
            boolean deleted = userService.deleteUser(id);

            response.setContentType("application/json");
            if (deleted) {
                response.getWriter().print("{\"status\":\"success\"}");
            } else {
                response.getWriter().print("{\"status\":\"error\", \"message\":\"Could not delete\"}");
            }
        }
    }
}