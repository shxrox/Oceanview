<%--
  Created by IntelliJ IDEA.
  User: sharo
  Date: 03/02/2026
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.oceanview.model.User" %>
<%
    // Security Check
    User user = (User) session.getAttribute("user");
    if (user == null || !"RECEPTIONIST".equals(user.getRole())) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Booking Confirmed - Ocean View Resort</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="dashboard-container" style="text-align: center; margin-top: 100px;">

    <div style="background-color: #27ae60; color: white; width: 60px; height: 60px; line-height: 60px; border-radius: 50%; margin: 0 auto; font-size: 30px;">
        ✓
    </div>

    <h2 style="color: #2c3e50; margin-top: 20px;">Reservation Successful!</h2>
    <p>The room has been booked and a confirmation email has been generated.</p>

    <br><br>

    <div class="action-buttons">
        <a href="searchRooms" class="button-link">Make Another Reservation</a>
        <a href="receptionist_dashboard.jsp" class="button-link" style="background-color: #7f8c8d;">Back to Dashboard</a>
    </div>

</div>

</body>
</html>