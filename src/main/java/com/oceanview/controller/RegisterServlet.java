package com.oceanview.controller;

import com.oceanview.model.User;
import com.oceanview.service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService;

    public void init() { this.userService = new UserService(); }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User newUser = new User(name, email, password, "RECEPTIONIST");
            boolean saved = userService.registerUser(newUser);
            if (saved) {
                out.print("{\"status\":\"success\"}");
            } else {
                out.print("{\"status\":\"error\", \"message\":\"Email already exists\"}");
            }
        } catch (Exception e) {
            out.print("{\"status\":\"error\", \"message\":\"Server error\"}");
        }
    }
}