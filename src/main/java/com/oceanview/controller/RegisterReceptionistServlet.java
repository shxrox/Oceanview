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

        // ... inside doPost method ...

        try {
            String generatedPassword = userService.registerReceptionist(fullName, email, username);

            if (generatedPassword != null) {
                request.setAttribute("successMessage", "Receptionist registered successfully!");
                request.setAttribute("generatedPassword", generatedPassword);
                request.setAttribute("newUsername", username);
            } else {
                request.setAttribute("errorMessage", "Registration failed. Username might already exist.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // CHECK: If the error message mentions "Duplicate entry", show a friendly error
            if (e.getMessage().contains("Duplicate entry")) {
                request.setAttribute("errorMessage", "Registration failed: This Username or Email is already taken.");
            } else {
                request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            }
        }

        request.getRequestDispatcher("register_receptionist.jsp").forward(request, response);

// ... end of method ...
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Optionally, redirect GET requests to the registration page
        response.sendRedirect("receptionist_dashboard.jsp");
    }
}
