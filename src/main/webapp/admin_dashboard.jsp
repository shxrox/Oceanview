<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.oceanview.model.User" %>
<%
  // 1. Security Check: Ensure the user is logged in AND is an ADMIN
  User user = (User) session.getAttribute("user");
  if (user == null || !"ADMIN".equals(user.getRole())) {
    response.sendRedirect("index.jsp");
    return;
  }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Admin Dashboard - Ocean View Resort</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="dashboard-container" style="text-align: center; margin-top: 50px;">
  <h2>Welcome, <%= user.getFullName() %> (Admin)</h2>

  <div class="action-buttons" style="margin-top: 30px;">
    <a href="register_receptionist.jsp" class="button-link">Register New Receptionist</a>
  </div>

  <br><br>

  <a href="logout" style="color: red;">Logout</a>
</div>

</body>
</html>