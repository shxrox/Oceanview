<%--
  Created by IntelliJ IDEA.
  User: sharo
  Date: 05/02/2026
  Time: 18:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.oceanview.model.Room" %>
<%@ page import="com.oceanview.model.User" %>
<%
    // Security Check
    User user = (User) session.getAttribute("user");
    if (user == null || !"RECEPTIONIST".equals(user.getRole())) {
        response.sendRedirect("index.jsp");
        return;
    }

    // Get the list of rooms
    List<Room> rooms = (List<Room>) request.getAttribute("rooms");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Live Room Status - Ocean View Resort</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="dashboard-container" style="max-width: 1000px; margin: 40px auto;">
    <h2 style="text-align: center;">Live Room Status</h2>
    <p style="text-align: center; color: #7f8c8d;">Select a room to begin reservation.</p>

    <div style="text-align: right; margin-bottom: 20px;">
        <a href="receptionist_dashboard.jsp" style="text-decoration: none; color: #7f8c8d;">&larr; Back to Dashboard</a>
    </div>

    <div class="room-grid">
        <%
            if (rooms != null && !rooms.isEmpty()) {
                for (Room room : rooms) {
        %>
        <div class="room-card">
            <span class="status-badge status-free">Available</span>

            <div class="room-number">Room <%= room.getRoomNumber() %></div>
            <div class="room-type"><%= room.getRoomType() %></div>
            <div class="room-price">$<%= room.getPricePerNight() %> / night</div>

            <a href="bookRoom?roomId=<%= room.getId() %>" class="button-link"
               style="background-color: #3498db; display: block; margin-top: 10px;">
                Book Now
            </a>
        </div>
        <%
            }
        } else {
        %>
        <div style="width: 100%; text-align: center; padding: 40px; color: #95a5a6;">
            <h3>No rooms available right now.</h3>
        </div>
        <% } %>
    </div>
</div>

</body>
</html>