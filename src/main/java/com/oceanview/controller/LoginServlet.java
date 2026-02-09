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

@WebServlet("/login") // This maps the URL http://localhost:8080/login to this Java code
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        this.userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userService.login(username, password);

            if (user != null) {
                // Login Success: Create a session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // Role-Based Redirection
                if ("ADMIN".equals(user.getRole())) {
                    response.sendRedirect("adminDashboard");
                } else {
                    response.sendRedirect("receptionist_dashboard.jsp");
                }
            } else {
                // Login Failed: Send back to login page with error
                request.setAttribute("errorMessage", "Invalid username or password");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error during login", e);
        }
    }
}