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

@WebServlet("/updateProfile")
public class UpdateProfileServlet extends HttpServlet {
    private UserService userService;

    public void init() { this.userService = new UserService(); }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        HttpSession session = request.getSession(false);

        // Security Check: Is user logged in?
        if (session == null || session.getAttribute("user") == null) {
            response.getWriter().print("{\"status\":\"error\", \"message\":\"Not logged in\"}");
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        String newName = request.getParameter("name");
        String newPass = request.getParameter("password");

        try {
            boolean updated = userService.updateUserProfile(currentUser, newName, newPass);

            if (updated) {
                // Update the session so the UI shows the new name immediately
                session.setAttribute("user", currentUser);
                response.getWriter().print("{\"status\":\"success\"}");
            } else {
                response.getWriter().print("{\"status\":\"error\", \"message\":\"Database failed\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("{\"status\":\"error\", \"message\":\"Server Error\"}");
        }
    }
}