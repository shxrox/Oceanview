<%--
  Created by IntelliJ IDEA.
  User: sharo
  Date: 09/02/2026
  Time: 10:43
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

    // Get error message if redirected from backend validation
    String error = request.getParameter("error");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>New Reservation - Step 1</title>
    <link rel="stylesheet" href="css/style.css">

    <script>
        // Function to set today's date as the minimum for check-in
        function setupDates() {
            var today = new Date().toISOString().split('T')[0];
            document.getElementById("checkIn").setAttribute('min', today);
            document.getElementById("checkOut").setAttribute('min', today);
        }

        // Function to update Check-Out min date based on Check-In selection
        function updateCheckOutMin() {
            var checkInDate = document.getElementById("checkIn").value;
            document.getElementById("checkOut").setAttribute('min', checkInDate);
        }
    </script>
</head>
<body onload="setupDates()">

<div class="login-container" style="width: 400px; margin-top: 50px;">
    <h2>New Reservation</h2>
    <p style="text-align: center; color: #7f8c8d;">Step 1: Select Dates</p>

    <% if (error != null) { %>
    <p style="color: red; text-align: center; background: #fadbd8; padding: 10px; border-radius: 5px;">
        <%= error %>
    </p>
    <% } %>

    <form action="searchRooms" method="get">
        <div class="form-group">
            <label for="checkIn">Check-in Date</label>
            <input type="date" id="checkIn" name="checkIn" required onchange="updateCheckOutMin()">
        </div>

        <div class="form-group">
            <label for="checkOut">Check-out Date</label>
            <input type="date" id="checkOut" name="checkOut" required>
        </div>

        <button type="submit" style="background-color: #2980b9;">Next: Find Rooms &rarr;</button>
    </form>

    <div style="text-align: center; margin-top: 15px;">
        <a href="receptionist_dashboard.jsp" style="text-decoration: none; color: #7f8c8d;">Cancel</a>
    </div>
</div>

</body>
</html>