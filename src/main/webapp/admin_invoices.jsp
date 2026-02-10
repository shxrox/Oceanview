<%--
  Created by IntelliJ IDEA.
  User: sharo
  Date: 10/02/2026
  Time: 12:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.oceanview.model.Reservation" %>
<%@ page import="com.oceanview.model.User" %>
<%
  User user = (User) session.getAttribute("user");
  if (user == null || !"ADMIN".equals(user.getRole())) {
    response.sendRedirect("index.jsp");
    return;
  }
  List<Reservation> list = (List<Reservation>) request.getAttribute("invoiceList");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Invoice Management - Ocean View Resort</title>
  <link rel="stylesheet" href="css/style.css">

  <script>
    function printInvoice(id, name, room, checkIn, checkOut, price) {
      // Simple Client-Side Calculation for the link
      var start = new Date(checkIn);
      var end = new Date(checkOut);
      var days = (end - start) / (1000 * 60 * 60 * 24);
      if (days < 1) days = 1;
      var total = days * price;

      // Construct URL
      var url = "print_receipt.jsp?id=" + id +
              "&name=" + encodeURIComponent(name) +
              "&room=" + encodeURIComponent(room) +
              "&in=" + checkIn +
              "&out=" + checkOut +
              "&price=" + price +
              "&total=" + total;

      window.open(url, '_blank');
    }
  </script>
</head>
<body>

<div class="dashboard-container" style="max-width: 1000px; margin: 50px auto;">
  <h2 style="text-align: center;">Invoice Management</h2>

  <div style="text-align: right; margin-bottom: 20px;">
    <a href="adminDashboard" style="text-decoration: none; color: #7f8c8d;">&larr; Back to Dashboard</a>
  </div>

  <table border="1" style="width: 100%; border-collapse: collapse; text-align: left;">
    <thead>
    <tr style="background-color: #34495e; color: white;">
      <th style="padding: 10px;">ID</th>
      <th style="padding: 10px;">Guest</th>
      <th style="padding: 10px;">Room</th>
      <th style="padding: 10px;">Dates</th>
      <th style="padding: 10px;">Action</th>
    </tr>
    </thead>
    <tbody>
    <%
      if (list != null && !list.isEmpty()) {
        for (Reservation res : list) {
    %>
    <tr style="border-bottom: 1px solid #ddd;">
      <td style="padding: 10px;">#<%= res.getId() %></td>
      <td style="padding: 10px;"><%= res.getCustomerName() %></td>
      <td style="padding: 10px;"><%= res.getRoomNumber() %></td>
      <td style="padding: 10px;">
        <small><%= res.getCheckIn() %> to <%= res.getCheckOut() %></small>
      </td>
      <td style="padding: 10px;">
        <button onclick="printInvoice('<%= res.getId() %>', '<%= res.getCustomerName() %>', '<%= res.getRoomNumber() %>', '<%= res.getCheckIn() %>', '<%= res.getCheckOut() %>', <%= res.getPricePerNight() %>)"
                style="background-color: #8e44ad; color: white; border: none; padding: 5px 10px; cursor: pointer; border-radius: 3px;">
          📄 Print Invoice
        </button>
      </td>
    </tr>
    <%
      }
    } else {
    %>
    <tr><td colspan="5" style="text-align: center; padding: 20px;">No invoices found.</td></tr>
    <% } %>
    </tbody>
  </table>
</div>

</body>
</html>