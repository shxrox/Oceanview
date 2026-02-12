package com.oceanview.controller;

import com.oceanview.model.User;
import com.oceanview.service.UserService;
import com.oceanview.service.EmailService; // Import EmailService
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService;
    private EmailService emailService;

    public void init() {
        this.userService = new UserService();
        this.emailService = new EmailService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String email = request.getParameter("email");

        // 1. Auto-Generate Password (if none provided)
        // UUID gives a random string like "a1b2-c3d4..."
        // We take the first 8 characters for simplicity
        String plainPassword = UUID.randomUUID().toString().substring(0, 8);

        try {
            // 2. Register User (This will Hash the password internally)
            User newUser = new User(name, email, plainPassword, "RECEPTIONIST");
            boolean saved = userService.registerUser(newUser);

            if (saved) {
                // 3. Send Email with the PLAIN password so the user knows it
                emailService.sendAccountCreationEmail(name, email, plainPassword);

                out.print("{\"status\":\"success\", \"message\":\"Account created. Password sent to email.\"}");
            } else {
                out.print("{\"status\":\"error\", \"message\":\"Email already exists\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"status\":\"error\", \"message\":\"Server error\"}");
        }
    }
}