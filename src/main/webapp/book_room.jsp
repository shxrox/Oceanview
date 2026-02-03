<%--
  Created by IntelliJ IDEA.
  User: sharo
  Date: 03/02/2026
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.oceanview.model.Room" %>
<%@ page import="com.oceanview.model.User" %>
<%
  // Security Check
  User user = (User) session.getAttribute("user");
  if (user == null || !"RECEPTIONIST".equals(user.getRole())) {
    response.sendRedirect("index.jsp");
    return;
  }

  // Get the room sent by the Servlet
  Room room = (Room) request.getAttribute("room");
  if (room == null) {
    response.sendRedirect("searchRooms");
    return;
  }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Confirm Booking - Ocean View Resort</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="login-container" style="width: 450px; margin-top: 50px;">
  <h2>Confirm Reservation</h2>

  <div style="background: #ecf0f1; padding: 15px; border-radius: 5px; margin-bottom: 20px; text-align: left;">
    <p><strong>Room:</strong> <%= room.getRoomNumber() %></p>
    <p><strong>Type:</strong> <%= room.getRoomType() %></p>
    <p><strong>Price:</strong> $<%= room.getPricePerNight() %> / night</p>
  </div>

  <form action="processBooking" method="post">
    <input type="hidden" name="roomId" value="<%= room.getId() %>">

    <h3>Guest Details</h3>

    <div class="form-group">
      <label for="customerName">Customer Name</label>
      <input type="text" id="customerName" name="customerName" required>
    </div>

    <div class="form-group">
      <label for="customerEmail">Customer Email</label>
      <input type="email" id="customerEmail" name="customerEmail" required>
    </div>

    <div class="form-group">
      <label for="customerPhone">Phone Number</label>
      <input type="tel" id="customerPhone" name="customerPhone" required>
    </div>

    <button type="submit" style="background-color: #27ae60;">Confirm Booking</button>
  </form>

  <br>
  <div style="text-align: center;">
    <a href="searchRooms" style="text-decoration: none; color: #7f8c8d;">Cancel</a>
  </div>
</div>

</body>
</html>