package com.oceanview.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Get the current session
        HttpSession session = request.getSession(false); // false = don't create if not exists

        // 2. Invalidate it (Clear all data)
        if (session != null) {
            session.invalidate();
        }

        // 3. Redirect back to Login Page
        response.sendRedirect("index.jsp");
    }
}