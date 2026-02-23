# 🌊 Ocean View Resort Management System

A full-stack, MVC-based Java Web Application designed to manage hotel operations smoothly. This system provides distinct portals for **Administrators** and **Receptionists** to handle room bookings, user management, billing, and analytics.

---

## 🌟 Features

### 👔 Admin Panel

* **Analytics Dashboard:** Visualizes revenue and room popularity using Google Charts.
* **Room Management:** Add, edit, or delete rooms, including uploading room images.
* **User Management:** Create and delete receptionist accounts.
* **Financial Tracking:** Log resort expenses (utilities, salaries, maintenance) and calculate net profit.
* **Invoices:** View recent bookings and generate printable invoice receipts.

### 🛎️ Receptionist Panel

* **Room Booking:** Search for available rooms by date and process new guest reservations.
* **Availability Calendar:** A visual calendar showing room occupancy and free dates.
* **Reservation Management:** View, search, and cancel existing guest bookings.
* **Profile Management:** Receptionists can update their own name and password.
* **Receipt Generation:** Automatically generates a printable receipt upon confirming a booking.

### ⚙️ Core System Features

* **Authentication:** Secure login system with hashed passwords using BCrypt.
* **Simulated Notifications:** Automatically generates local text files to simulate Email and SMS confirmations sent to guests.
* **AJAX Integration:** Dynamic, page-reload-free UI updates using the JavaScript Fetch API.

---

## 🛠️ Tech Stack

* **Frontend:** HTML5, CSS3, JavaScript (Vanilla), Google Charts API
* **Backend:** Java EE (Servlets, JSP)
* **Architecture:** MVC (Model-View-Controller)
* **Database:** MySQL
* **Security:** jBCrypt (Password Hashing)
* **Server:** Apache Tomcat (Recommended v9.0+)

---

## 🚀 Getting Started

### 📌 Prerequisites

* Java Development Kit (JDK) 8 or higher
* Apache Tomcat Server (v9.0+)
* MySQL Server (running on port `3307` by default)
* IDE (IntelliJ IDEA, Eclipse, or VS Code)

---

## 🗄️ 1. Database Setup

1. Create a new MySQL database named `ocean_view_resort`.
2. Ensure your tables (`users`, `rooms`, `reservations`, `expenses`) are created.
   *(Note: Import your SQL dump file if you have one.)*
3. Update the database credentials in `src/main/resources/db.properties`:

```properties
db.url=jdbc:mysql://localhost:3307/ocean_view_resort?useSSL=false&serverTimezone=UTC
db.user=root
db.password=YourMySQLPassword
db.driver=com.mysql.cj.jdbc.Driver
```

---

## 📂 2. Configure Local Paths

The system simulates Email and SMS functionality by saving text files locally. Update the output paths in:

`src/main/java/com/oceanview/service/EmailService.java`

```java
private static final String EMAIL_PATH = "C:\\Path\\To\\Your\\Desired\\Folder\\FakeEmails\\";
private static final String SMS_PATH   = "C:\\Path\\To\\Your\\Desired\\Folder\\FakeSMS\\";
```

---

## 👤 3. Seed the Admin User

To log in for the first time, run `AdminSeeder.java` as a standalone Java application to create the default Admin account.

**Default Credentials:**

* **Email:** [admin@gmail.com](mailto:admin@gmail.com)
* **Password:** admin123

---

## 🏗️ 4. Build and Deploy

1. Build the project using your IDE or Maven/Gradle (if configured).
2. Deploy the generated `.war` file to your Tomcat Server.
3. Access the application in your browser:

```
http://localhost:8080/OceanView/login_app.html
```

---

## 📁 Project Structure Overview

```
src/
├── main/
│   ├── java/com/oceanview/
│   │   ├── controller/      # Java Servlets handling HTTP requests (Login, Booking, etc.)
│   │   ├── model/           # POJO classes (User, Room, Reservation)
│   │   ├── repository/      # JDBC Database interaction logic (DAO pattern)
│   │   ├── service/         # Business logic and external services (EmailService)
│   │   └── util/            # DB Connection and Seeders
│   ├── resources/
│   │   └── db.properties    # Database configuration credentials
│   └── webapp/              # Frontend files
│       ├── css/             # Stylesheets
│       ├── images/          # Static assets and icons
│       ├── uploads/         # Uploaded room images
│       ├── WEB-INF/         # web.xml and protected JSPs
│       └── *.html           # Dashboard, Login, and Application Views
└── test/
    └── java/com/oceanview/  # JUnit Tests (e.g., ReservationServiceTest)
```

---

## 🧪 Testing

The project includes automated Unit Tests. Run the tests (e.g., `ReservationServiceTest.java`) using your IDE’s built-in JUnit test runner to verify core business logic such as billing calculations.

---

## 📄 License

This project is submitted as part of the ICBT Advanced Programming coursework.
