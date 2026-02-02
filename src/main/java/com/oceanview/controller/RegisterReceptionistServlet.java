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
        this.userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Security Check: Only Admin can register users
        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            response.sendRedirect("index.jsp");
            return;
        }

        // 2. Get Form Data
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String username = request.getParameter("username");

        try {
            // 3. Call Service to create user and send email
            boolean success = userService.registerReceptionist(fullName, email, username);

            if (success) {
                request.setAttribute("successMessage", "Receptionist registered successfully! Credentials sent via email.");
            } else {
                request.setAttribute("errorMessage", "Registration failed. Username or Email might already exist.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }

        // 4. Send back to the registration page to show the result
        request.getRequestDispatcher("register_receptionist.jsp").forward(request, response);
    }
}