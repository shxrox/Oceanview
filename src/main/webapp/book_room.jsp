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
  User user = (User) session.getAttribute("user");
  if (user == null || !"RECEPTIONIST".equals(user.getRole())) {
    response.sendRedirect("index.jsp");
    return;
  }

  Room room = (Room) request.getAttribute("room");
  // Retrieve the dates passed from Step 2
  String checkIn = (String) request.getAttribute("checkIn");
  String checkOut = (String) request.getAttribute("checkOut");

  // If dates are missing here, default to empty string to avoid "null" text
  if (checkIn == null) checkIn = "";
  if (checkOut == null) checkOut = "";
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Confirm Booking - Ocean View Resort</title>
  <link rel="stylesheet" href="css/style.css">

  <script>
    function validateForm() {
      // 1. Get the phone number value
      var phone = document.getElementById("customerPhone").value;

      // 2. Define a regex for numbers only (10 digits)
      var phonePattern = /^[0-9]{10}$/;

      // 3. Check if phone matches
      if (!phonePattern.test(phone)) {
        alert("Invalid Phone Number! Please enter exactly 10 digits.");
        return false; // Stop the form submission
      }

      // 4. Check if Name is empty
      var name = document.getElementById("customerName").value;
      if (name.trim() === "") {
        alert("Customer Name cannot be empty.");
        return false;
      }

      // If everything is good, allow submission
      return true;
    }
  </script>
</head>
<body>

<div class="login-container" style="width: 450px; margin-top: 50px;">
  <h2>Step 3: Guest Details</h2>

  <div style="background: #ecf0f1; padding: 15px; border-radius: 5px; margin-bottom: 20px; text-align: left;">
    <p><strong>Room:</strong> <%= (room != null) ? room.getRoomNumber() : "Unknown" %> (<%= (room != null) ? room.getRoomType() : "" %>)</p>
    <p><strong>Check-In:</strong> <%= checkIn %></p>
    <p><strong>Check-Out:</strong> <%= checkOut %></p>
    <p><strong>Price:</strong> $<%= (room != null) ? room.getPricePerNight() : "0" %> / night</p>
  </div>

  <form action="processBooking" method="post" onsubmit="return validateForm()">
    <input type="hidden" name="roomId" value="<%= (room != null) ? room.getId() : "" %>">
    <input type="hidden" name="checkIn" value="<%= checkIn %>">
    <input type="hidden" name="checkOut" value="<%= checkOut %>">

    <div class="form-group">
      <label for="customerName">Customer Name</label>
      <input type="text" id="customerName" name="customerName" required>
    </div>

    <div class="form-group">
      <label for="customerEmail">Customer Email</label>
      <input type="email" id="customerEmail" name="customerEmail" required>
    </div>

    <div class="form-group">
      <label for="customerPhone">Phone Number (10 digits)</label>
      <input type="tel" id="customerPhone" name="customerPhone" placeholder="077xxxxxxx" required>
    </div>

    <button type="submit" style="background-color: #27ae60;">Confirm Booking</button>
  </form>

  <br>
  <div style="text-align: center;">
    <a href="searchRooms?checkIn=<%= checkIn %>&checkOut=<%= checkOut %>" style="text-decoration: none; color: #7f8c8d;">Cancel</a>
  </div>
</div>

</body>
</html>