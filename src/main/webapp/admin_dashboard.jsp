<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.oceanview.model.User" %>
<%@ page import="java.util.Map" %>
<%
  // 1. Security Check
  User user = (User) session.getAttribute("user");
  if (user == null || !"ADMIN".equals(user.getRole())) {
    response.sendRedirect("index.jsp");
    return;
  }

  // 2. Get Statistics from Servlet
  Map<String, Integer> stats = (Map<String, Integer>) request.getAttribute("roomStats");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Admin Dashboard - Ocean View Resort</title>
  <link rel="stylesheet" href="css/style.css">

  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
  <script type="text/javascript">
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
      // Create the data table
      var data = new google.visualization.DataTable();
      data.addColumn('string', 'Room Type');
      data.addColumn('number', 'Bookings');
      data.addRows([
        <%
           if (stats != null) {
               for (Map.Entry<String, Integer> entry : stats.entrySet()) {
        %>
        ['<%= entry.getKey() %>', <%= entry.getValue() %>],
        <%
               }
           } else {
        %>
        ['No Data', 1]
        <% } %>
      ]);

      // Set chart options
      var options = {
        'title': 'Room Popularity (Based on Bookings)',
        'width': 500,
        'height': 400,
        'is3D': true,
        'colors': ['#e67e22', '#3498db', '#9b59b6', '#2ecc71']
      };

      // Instantiate and draw our chart, passing in some options.
      var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
      chart.draw(data, options);
    }
  </script>
</head>
<body>

<div class="dashboard-container">
  <div class="header">
    <h1>Admin Analytics Dashboard</h1>
    <p>Welcome, <%= user.getUsername() %></p>
  </div>

  <div class="stats-panel" style="display: flex; flex-wrap: wrap; justify-content: space-around; margin-top: 30px;">

    <div style="background: white; padding: 20px; border-radius: 10px; box-shadow: 0 4px 6px rgba(0,0,0,0.1);">
      <div id="chart_div"></div>
    </div>

    <div style="background: white; padding: 20px; border-radius: 10px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); width: 300px;">
      <h3>Quick Actions</h3>
      <ul style="list-style: none; padding: 0;">
        <li style="margin-bottom: 15px;">
          <a href="register.jsp" class="button-link" style="display: block; text-align: center;">Register New Staff</a>
        </li>
        <li style="margin-bottom: 15px;">
          <a href="adminInvoices" class="button-link" style="display: block; text-align: center; background-color: #34495e;">
            📑 Invoice Manager
          </a>
        </li>
        <li>
          <a href="logout" class="button-link" style="background-color: #e74c3c; display: block; text-align: center;">Logout</a>
        </li>
      </ul>
    </div>

  </div>
</div>

</body>
</html>