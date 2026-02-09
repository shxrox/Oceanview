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
    User user = (User) session.getAttribute("user");
    if (user == null || !"RECEPTIONIST".equals(user.getRole())) {
        response.sendRedirect("index.jsp");
        return;
    }
    List<Room> rooms = (List<Room>) request.getAttribute("rooms");

    // Retrieve dates if they were selected in Step 1 (We will build Step 1 next)
    String checkIn = request.getParameter("checkIn");
    String checkOut = request.getParameter("checkOut");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Select Room - Ocean View Resort</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="dashboard-container" style="max-width: 800px; margin: 50px auto;">
    <h2 style="text-align: center;">Step 2: Select a Room</h2>

    <% if (checkIn != null && checkOut != null) { %>
    <p style="text-align: center; color: #27ae60;">
        Showing rooms for: <strong><%= checkIn %></strong> to <strong><%= checkOut %></strong>
    </p>
    <% } %>

    <div style="text-align: right; margin-bottom: 20px;">
        <a href="receptionist_dashboard.jsp" style="text-decoration: none; color: #7f8c8d;">Cancel</a>
    </div>

    <table border="1" style="width: 100%; border-collapse: collapse; text-align: center;">
        <thead>
        <tr style="background-color: #ecf0f1;">
            <th style="padding: 10px;">Room</th>
            <th style="padding: 10px;">Type</th>
            <th style="padding: 10px;">Price</th>
            <th style="padding: 10px;">Action</th>
        </tr>
        </thead>
        <tbody>
        <%
            if (rooms != null && !rooms.isEmpty()) {
                for (Room room : rooms) {
        %>
        <tr>
            <td style="padding: 10px;"><strong><%= room.getRoomNumber() %></strong></td>
            <td style="padding: 10px;"><%= room.getRoomType() %></td>
            <td style="padding: 10px;">$<%= room.getPricePerNight() %></td>
            <td style="padding: 10px;">
                <a href="bookRoom?roomId=<%= room.getId() %>&checkIn=<%= checkIn %>&checkOut=<%= checkOut %>"
                   style="background-color: #3498db; color: white; padding: 5px 10px; text-decoration: none; border-radius: 4px;">
                    Select This Room
                </a>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr><td colspan="4" style="padding: 20px;">No rooms available.</td></tr>
        <% } %>
        </tbody>
    </table>
</div>

</body>
</html>