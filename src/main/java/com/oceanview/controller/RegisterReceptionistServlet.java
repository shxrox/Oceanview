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
import java.sql.SQLException;

@WebServlet("/registerReceptionist")
public class RegisterReceptionistServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        // Initialize the UserService
        this.userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Security Check: Only Admin can register new receptionists
        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            response.sendRedirect("index.jsp");
            return;
        }

        // 2. Retrieve form parameters from the registration page
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String username = request.getParameter("username");

        try {
            // 3. Call UserService to register the receptionist
            String generatedPassword = userService.registerReceptionist(fullName, email, username);

            if (generatedPassword != null) {
                // Registration successful
                request.setAttribute("successMessage", "Receptionist registered successfully!");
                request.setAttribute("generatedPassword", generatedPassword); // Optional: show on page
                request.setAttribute("newUsername", username);
            } else {
                // Registration failed (username/email might already exist)
                request.setAttribute("errorMessage", "Registration failed. Username or Email might already exist.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }

        // 4. Forward back to the registration page to show success/error messages
        request.getRequestDispatcher("receptionist_dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Optionally, redirect GET requests to the registration page
        response.sendRedirect("receptionist_dashboard.jsp");
    }
}
