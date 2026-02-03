<%--
  Created by IntelliJ IDEA.
  User: sharo
  Date: 02/02/2026
  Time: 22:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.oceanview.model.User" %>
<%
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
    <title>Receptionist Dashboard - Ocean View Resort</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="dashboard-container" style="text-align: center; margin-top: 50px;">
    <h2>Welcome, <%= user.getFullName() %></h2>
    <h3>Receptionist Panel</h3>

    <div class="action-buttons" style="display: flex; gap: 20px; justify-content: center; margin-top: 30px;">

        <a href="searchRooms" class="button-link" style="background-color: #27ae60;">
            Make Reservation
        </a>

        <a href="view_reservations.jsp" class="button-link" style="background-color: #f39c12;">
            View All Bookings
        </a>
    </div>

    <br><br><br>
    <a href="logout" style="color: red; text-decoration: none; font-weight: bold;">Logout</a>
</div>

</body>
</html>