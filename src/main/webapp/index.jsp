<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - Ocean View Resort</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="login-container">
    <h2>Ocean View Resort</h2>
    <h3>Staff Login</h3>

    <%
        String error = (String) request.getAttribute("errorMessage");
        if (error != null) {
    %>
    <div class="error-message"><%= error %></div>
    <% } %>

    <form action="login" method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>

        <button type="submit">Login</button>
    </form>
</div>

</body>
</html>