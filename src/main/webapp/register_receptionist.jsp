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
    // Security Check: Only Admin can access this page
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
    <title>Register Receptionist - Ocean View Resort</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="login-container" style="width: 400px;">
    <h2>Register New Staff</h2>
    <h3>Receptionist Details</h3>

    <% String success = (String) request.getAttribute("successMessage"); %>
    <% String error = (String) request.getAttribute("errorMessage"); %>

    <% if (success != null) { %>
    <div class="error-message" style="background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb;">
        <%= success %>
    </div>
    <% } %>

    <% if (error != null) { %>
    <div class="error-message">
        <%= error %>
    </div>
    <% } %>

    <form action="registerReceptionist" method="post">
        <div class="form-group">
            <label for="fullName">Full Name</label>
            <input type="text" id="fullName" name="fullName" required>
        </div>

        <div class="form-group">
            <label for="email">Email Address</label>
            <input type="email" id="email" name="email" required>
        </div>

        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required>
        </div>

        <button type="submit">Create Account & Send Email</button>
    </form>

    <br>
    <div style="text-align: center;">
        <a href="admin_dashboard.jsp" style="text-decoration: none; color: #7f8c8d;">&larr; Back to Dashboard</a>
    </div>
</div>

</body>
</html>
