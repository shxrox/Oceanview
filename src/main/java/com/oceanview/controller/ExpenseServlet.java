package com.oceanview.controller;

import com.oceanview.util.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/addExpense")
public class ExpenseServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String category = request.getParameter("category");
        double amount = Double.parseDouble(request.getParameter("amount"));

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO expenses (category, amount, expense_date) VALUES (?, ?, CURDATE())";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, category);
            stmt.setDouble(2, amount);

            stmt.executeUpdate();

            response.setContentType("application/json");
            response.getWriter().print("{\"status\":\"success\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("{\"status\":\"error\"}");
        }
    }
}