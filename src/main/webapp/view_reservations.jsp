<%--
  Created by IntelliJ IDEA.
  User: sharo
  Date: 03/02/2026
  Time: 15:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.oceanview.model.Reservation" %>
<%@ page import="com.oceanview.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || !"RECEPTIONIST".equals(user.getRole())) {
        response.sendRedirect("index.jsp");
        return;
    }

    List<Reservation> list = (List<Reservation>) request.getAttribute("reservationList");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Reservation History - Ocean View Resort</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="dashboard-container" style="max-width: 1000px; margin: 50px auto;">
    <h2 style="text-align: center;">All Reservations</h2>

    <div style="text-align: right; margin-bottom: 20px;">
        <a href="receptionist_dashboard.jsp" style="text-decoration: none; color: #7f8c8d;">&larr; Back to Dashboard</a>
    </div>

    <table border="1" style="width: 100%; border-collapse: collapse; text-align: left;">
        <thead>
        <tr style="background-color: #f39c12; color: white;">
            <th style="padding: 10px;">ID</th>
            <th style="padding: 10px;">Room</th>
            <th style="padding: 10px;">Customer</th>
            <th style="padding: 10px;">Dates (In / Out)</th> <th style="padding: 10px;">Phone</th>
            <th style="padding: 10px;">Date Booked</th>
            <th style="padding: 10px;">Action</th>
        </tr>
        </thead>
        <tbody>
        <%
            if (list != null && !list.isEmpty()) {
                for (Reservation res : list) {
        %>
        <tr style="border-bottom: 1px solid #ddd;">
            <td style="padding: 10px;"><%= res.getId() %></td>
            <td style="padding: 10px;"><%= res.getRoomId() %></td>
            <td style="padding: 10px;">
                <strong><%= res.getCustomerName() %></strong><br>
                <span style="font-size: 12px; color: #7f8c8d;"><%= res.getCustomerEmail() %></span>
            </td>

            <td style="padding: 10px;">
                <span style="color: #27ae60;">In: <%= res.getCheckIn() %></span><br>
                <span style="color: #c0392b;">Out: <%= res.getCheckOut() %></span>
            </td>

            <td style="padding: 10px;"><%= res.getCustomerPhone() %></td>
            <td style="padding: 10px;"><%= res.getBookingDate() %></td>
            <td style="padding: 10px;">
                <form action="cancelReservation" method="post" onsubmit="return confirm('Are you sure you want to cancel this booking? This cannot be undone.');">
                    <input type="hidden" name="id" value="<%= res.getId() %>">
                    <button type="submit" style="background-color: #e74c3c; color: white; border: none; padding: 5px 10px; cursor: pointer; border-radius: 3px;">
                        ✖ Cancel
                    </button>
                </form>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="6" style="padding: 20px; text-align: center;">No reservations found.</td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

</body>
</html>