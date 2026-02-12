package com.oceanview.service;

import com.oceanview.model.Reservation;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmailService {

    // YOUR SPECIFIC FOLDER PATH
    private static final String FOLDER_PATH = "C:\\Users\\sharo\\OneDrive\\Pictures\\Documents\\ICBT-TOP\\semester-1\\Assessment\\ICBT_CIS6003_Advanced Programing\\OceanView\\FakeEmails\\";

    // Method 1: Booking Confirmation (Existing)
    public void sendBookingConfirmation(Reservation res) {
        String fileName = "Booking_" + res.getRoomId() + "_" + System.currentTimeMillis() + ".txt";
        String content = "Subject: Booking Confirmed\nTo: " + res.getCustomerEmail() + "\n\nDear " + res.getCustomerName() +
                ",\nYour room is booked from " + res.getCheckIn() + " to " + res.getCheckOut() + ".";
        saveToFile(fileName, content);
    }

    // Method 2: New Account Credentials (NEW)
    public void sendAccountCreationEmail(String name, String email, String plainPassword) {
        String fileName = "Welcome_" + name.replace(" ", "_") + "_" + System.currentTimeMillis() + ".txt";

        StringBuilder content = new StringBuilder();
        content.append("To: ").append(email).append("\n");
        content.append("Subject: Welcome to Ocean View Resort - Staff Login\n");
        content.append("--------------------------------------------------\n");
        content.append("Dear ").append(name).append(",\n\n");
        content.append("An account has been created for you.\n");
        content.append("Please use the following credentials to login:\n\n");
        content.append("   URL: http://localhost:8080/oceanview/login_app.html\n");
        content.append("   Email: ").append(email).append("\n");
        content.append("   Temporary Password: ").append(plainPassword).append("\n\n"); // Sending the PLAIN password so they can read it
        content.append("Please change your password after logging in.\n");
        content.append("--------------------------------------------------\n");
        content.append("Ocean View IT Admin\n");

        saveToFile(fileName, content.toString());
    }

    // Helper Method to write files
    private void saveToFile(String fileName, String content) {
        File folder = new File(FOLDER_PATH);
        if (!folder.exists()) folder.mkdirs();

        try (FileWriter writer = new FileWriter(new File(folder, fileName))) {
            writer.write(content);
            System.out.println("📧 Fake Email Saved: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}