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

    <% String error = (String) request.getAttribute("errorMessage"); %>
    <% if (error != null) { %>
    <div class="error-message"><%= error %></div>
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
        <button type="submit">Create Account</button>
    </form>

    <br>
    <div style="text-align: center;">
        <a href="admin_dashboard.jsp" style="text-decoration: none; color: #7f8c8d;">&larr; Back to Dashboard</a>
    </div>
</div>

<%
    String genPassword = (String) request.getAttribute("generatedPassword");
    String newUsername = (String) request.getAttribute("newUsername");
    if (genPassword != null) {
%>
<div class="modal-overlay" id="credentialModal">
    <div class="modal-content">
        <h3 style="color: #27ae60;">Registration Successful!</h3>
        <p>Please copy these credentials immediately.</p>

        <div class="credential-box" id="credentialText">
            Username: <%= newUsername %><br>
            Password: <%= genPassword %>
        </div>

        <button class="copy-btn" onclick="copyToClipboard()">Copy to Clipboard</button>
        <button class="close-btn" onclick="closeModal()">Close</button>
    </div>
</div>

<script>
    function copyToClipboard() {
        // Get the text content
        const text = "Username: <%= newUsername %>\nPassword: <%= genPassword %>";

        // Copy to clipboard API
        navigator.clipboard.writeText(text).then(() => {
            alert("Credentials copied to clipboard!");
        }).catch(err => {
            console.error('Failed to copy: ', err);
        });
    }

    function closeModal() {
        document.getElementById('credentialModal').style.display = 'none';
        // Optional: Redirect back to dashboard after closing
        window.location.href = 'admin_dashboard.jsp';
    }
</script>
<% } %>

</body>
</html>
