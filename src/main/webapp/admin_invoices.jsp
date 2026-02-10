<%--
  Created by IntelliJ IDEA.
  User: sharo
  Date: 10/02/2026
  Time: 12:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.oceanview.model.Reservation" %>
<%@ page import="com.oceanview.model.User" %>
<%
  User user = (User) session.getAttribute("user");
  if (user == null || !"ADMIN".equals(user.getRole())) {
    response.sendRedirect("index.jsp");
    return;
  }
  List<Reservation> list = (List<Reservation>) request.getAttribute("invoiceList");
  Map<String, Double> revenueStats = (Map<String, Double>) request.getAttribute("revenueStats");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Invoice Management - Ocean View Resort</title>
  <link rel="stylesheet" href="css/style.css">

  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
  <script type="text/javascript">
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
      var data = google.visualization.arrayToDataTable([
        ['Room Type', 'Total Revenue ($)', { role: 'style' }],
        <%
           if (revenueStats != null) {
               // Define colors for bars
               String[] colors = {"#e67e22", "#3498db", "#9b59b6", "#2ecc71"};
               int i = 0;
               for (Map.Entry<String, Double> entry : revenueStats.entrySet()) {
        %>
        ['<%= entry.getKey() %>', <%= entry.getValue() %>, '<%= colors[i++ % colors.length] %>'],
        <%
               }
           } else {
        %>
        ['No Data', 0, 'silver']
        <% } %>
      ]);

      var options = {
        title: 'Total Revenue by Room Type',
        hAxis: {title: 'Room Type'},
        vAxis: {title: 'Revenue ($)'},
        legend: { position: "none" }
      };

      var chart = new google.visualization.ColumnChart(document.getElementById('revenue_chart'));
      chart.draw(data, options);
    }

    // ... existing printInvoice function ...
    function printInvoice(id, name, room, checkIn, checkOut, price) {
      // ... (same as before) ...
      var start = new Date(checkIn);
      var end = new Date(checkOut);
      var days = (end - start) / (1000 * 60 * 60 * 24);
      if (days < 1) days = 1;
      var total = days * price;

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

  <div id="revenue_chart" style="width: 100%; height: 300px; margin-bottom: 30px; border: 1px solid #ddd; background: white;"></div>

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