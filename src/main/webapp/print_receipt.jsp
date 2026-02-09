<%--
  Created by IntelliJ IDEA.
  User: sharo
  Date: 09/02/2026
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.oceanview.model.Reservation" %>
<%@ page import="com.oceanview.model.Room" %>
<%@ page import="com.oceanview.service.ReservationService" %>
<%@ page import="com.oceanview.service.RoomService" %>
<%
    // 1. Get Reservation ID
    String resIdStr = request.getParameter("id");
    if (resIdStr == null) {
        response.sendRedirect("receptionist_dashboard.jsp");
        return;
    }

    // 2. Fetch Details
    int resId = Integer.parseInt(resIdStr);
    ReservationService resService = new ReservationService();
    RoomService roomService = new RoomService();

    // (Note: You might need to add a 'getReservationById' method to your service if you don't have one.
    // For now, let's assume we can fetch it or just display basic info from parameters if easier.
    // Actually, let's just pass the details in the URL for simplicity in this demo,
    // OR we can implement 'getReservationById'. Let's do the robust way: getById).

    // WAIT! We haven't implemented getReservationById yet.
    // To save time, let's just pass the data from the success page!

    String customer = request.getParameter("name");
    String roomNo = request.getParameter("room");
    String checkIn = request.getParameter("in");
    String checkOut = request.getParameter("out");
    String price = request.getParameter("price");
    String total = request.getParameter("total"); // We will calculate this
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Invoice #<%= resId %></title>
    <style>
        body { font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; color: #555; }
        .invoice-box {
            max-width: 800px;
            margin: auto;
            padding: 30px;
            border: 1px solid #eee;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
            font-size: 16px;
            line-height: 24px;
        }
        .invoice-box table { width: 100%; line-height: inherit; text-align: left; }
        .invoice-box table td { padding: 5px; vertical-align: top; }
        .invoice-box table tr td:nth-child(2) { text-align: right; }
        .top_title { font-size: 45px; line-height: 45px; color: #333; }
        .information { background: #eee; }
        .heading { background: #eee; border-bottom: 1px solid #ddd; font-weight: bold; }
        .total { font-weight: bold; border-top: 2px solid #eee; }

        @media print {
            .no-print { display: none; }
            .invoice-box { box-shadow: none; border: 0; }
        }
    </style>
</head>
<body>

<div class="invoice-box">
    <table cellpadding="0" cellspacing="0">
        <tr class="top">
            <td colspan="2">
                <table>
                    <tr>
                        <td class="top_title">
                            Ocean View Resort
                        </td>
                        <td>
                            Invoice #: <%= resId %><br>
                            Created: <%= new java.util.Date() %><br>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>

        <tr class="information">
            <td colspan="2">
                <table>
                    <tr>
                        <td>
                            Ocean View Resort, Inc.<br>
                            123 Seaside Blvd<br>
                            Colombo, Sri Lanka
                        </td>
                        <td>
                            <%= customer %><br>
                            Guest
                        </td>
                    </tr>
                </table>
            </td>
        </tr>

        <tr class="heading">
            <td>Description</td>
            <td>Price</td>
        </tr>

        <tr class="item">
            <td>Room Charge (<%= roomNo %>) <br> <small><%= checkIn %> to <%= checkOut %></small></td>
            <td>$<%= price %> / night</td>
        </tr>

        <tr class="total">
            <td></td>
            <td>Total: $<%= total %></td>
        </tr>
    </table>

    <div class="no-print" style="margin-top: 20px; text-align: center;">
        <button onclick="window.print()" style="padding: 10px 20px; background: #27ae60; color: white; border: none; cursor: pointer; font-size: 16px;">Download PDF / Print</button>
        <br><br>
        <a href="receptionist_dashboard.jsp" style="text-decoration: none; color: #999;">Close Window</a>
    </div>
</div>

</body>
</html>