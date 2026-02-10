<%--
  Created by IntelliJ IDEA.
  User: sharo
  Date: 09/02/2026
  Time: 16:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Help & Support - Ocean View Resort</title>
  <link rel="stylesheet" href="css/style.css">
  <style>
    .help-container { max-width: 800px; margin: 50px auto; background: white; padding: 40px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }
    .help-section { margin-bottom: 30px; border-bottom: 1px solid #eee; padding-bottom: 20px; }
    .help-section h3 { color: #2c3e50; margin-top: 0; }
    .step { margin-bottom: 10px; }
    .step strong { color: #2980b9; }
    .faq-question { font-weight: bold; cursor: pointer; color: #34495e; margin: 10px 0; }
    .faq-answer { display: none; margin-left: 20px; color: #7f8c8d; }
  </style>
  <script>
    function toggleAnswer(id) {
      var answer = document.getElementById(id);
      if (answer.style.display === "block") {
        answer.style.display = "none";
      } else {
        answer.style.display = "block";
      }
    }
  </script>
</head>
<body>

<div class="help-container">
  <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
    <h2>System Help Guide</h2>
    <a href="javascript:history.back()" class="button-link" style="background: #95a5a6;">&larr; Back</a>
  </div>

  <div class="help-section">
    <h3>How to Make a Reservation</h3>
    <p>Follow these 3 simple steps to book a room:</p>
    <div class="step"><strong>Step 1:</strong> Click "Make New Reservation" on the dashboard.</div>
    <div class="step"><strong>Step 2:</strong> Select the <em>Check-In</em> and <em>Check-Out</em> dates. (Note: You cannot select past dates).</div>
    <div class="step"><strong>Step 3:</strong> Choose a room from the available list and click "Select This Room".</div>
    <div class="step"><strong>Step 4:</strong> Enter the Guest's Name, Email, and Phone Number, then click "Confirm".</div>
  </div>

  <div class="help-section">
    <h3>Printing Invoices</h3>
    <p>After a booking is confirmed, you will see a purple <strong>"Download Invoice"</strong> button.</p>
    <p>Click it to open a printable PDF view. You can save this file or print it directly for the guest.</p>
  </div>

  <div class="help-section">
    <h3>Frequently Asked Questions (Click to Expand)</h3>

    <div class="faq-question" onclick="toggleAnswer('a1')">▶ Why does it say "No Rooms Available"?</div>
    <div id="a1" class="faq-answer">
      This means all rooms are fully booked for the dates you selected. Try changing the dates to find an opening.
    </div>

    <div class="faq-question" onclick="toggleAnswer('a2')">▶ Can I book a room for the past?</div>
    <div id="a2" class="faq-answer">
      No. The system automatically blocks dates before "Today" to prevent errors.
    </div>

    <div class="faq-question" onclick="toggleAnswer('a3')">▶ How do I reset a password?</div>
    <div id="a3" class="faq-answer">
      Please contact the System Administrator to reset staff passwords.
    </div>
  </div>

  <div style="text-align: center; margin-top: 40px; font-size: 12px; color: #bdc3c7;">
    Ocean View Resort Management System v1.0 <br>
    Support Contact: admin@oceanview.com
  </div>
</div>

</body>
</html>