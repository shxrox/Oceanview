<%--
  Created by IntelliJ IDEA.
  User: sharo
  Date: 03/02/2026
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.oceanview.model.Room" %>
<%@ page import="com.oceanview.service.RoomService" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.temporal.ChronoUnit" %>
<%
    String name = request.getParameter("name");
    String checkIn = request.getParameter("in");
    String checkOut = request.getParameter("out");
    String roomIdStr = request.getParameter("id");

    // Default values
    double price = 0;
    double total = 0;
    long nights = 1;
    String roomNumber = "Unknown";

    if (roomIdStr != null) {
        RoomService rs = new RoomService();
        Room room = rs.getRoomById(Integer.parseInt(roomIdStr));
        if (room != null) {
            price = room.getPricePerNight();
            roomNumber = room.getRoomNumber();

            // CALCULATE NIGHTS
            if (checkIn != null && checkOut != null) {
                try {
                    LocalDate d1 = LocalDate.parse(checkIn);
                    LocalDate d2 = LocalDate.parse(checkOut);
                    nights = ChronoUnit.DAYS.between(d1, d2);
                    if (nights < 1) nights = 1; // Minimum 1 night
                } catch (Exception e) {
                    nights = 1; // Fallback if dates are weird
                }
            }

            // Calculate Total
            total = price * nights;
        }
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Booking Confirmed</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .success-icon { font-size: 50px; color: #27ae60; margin-bottom: 20px; }
    </style>
</head>
<body>

<div class="login-container" style="text-align: center; margin-top: 100px;">
    <div class="success-icon">✔</div>
    <h2>Booking Successful!</h2>
    <p>The room has been reserved for <strong><%= nights %></strong> night(s).</p>
    <p>A confirmation email has been sent to the customer.</p>

    <% if (name != null) { %>
    <div style="margin: 20px 0;">
        <a href="print_receipt.jsp?id=<%= roomIdStr %>&name=<%= name %>&room=<%= roomNumber %>&in=<%= checkIn %>&out=<%= checkOut %>&price=<%= price %>&total=<%= total %>"
           target="_blank"
           class="button-link"
           style="background-color: #8e44ad;">
            📄 Download Invoice ($<%= total %>)
        </a>
    </div>
    <% } %>

    <br>
    <a href="receptionist_dashboard.jsp" class="button-link">Back to Dashboard</a>
</div>

</body>
</html>