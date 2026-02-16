package com.oceanview.service;

import com.oceanview.model.Reservation;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EmailService {

    // CORRECT PATHS FOR YOUR COMPUTER
    private static final String EMAIL_PATH = "C:\\Users\\sharo\\OneDrive\\Pictures\\Documents\\ICBT-TOP\\semester-1\\Assessment\\ICBT_CIS6003_Advanced Programing\\OceanView\\FakeEmails\\";
    private static final String SMS_PATH   = "C:\\Users\\sharo\\OneDrive\\Pictures\\Documents\\ICBT-TOP\\semester-1\\Assessment\\ICBT_CIS6003_Advanced Programing\\OceanView\\FakeSMS\\";

    // --- 1. SEND EMAIL ---
    public void sendBookingConfirmation(Reservation res, double totalAmount, String roomNumber) {
        String fileName = "Booking_" + res.getRoomId() + "_" + System.currentTimeMillis() + ".txt";

        StringBuilder content = new StringBuilder();
        content.append("To: ").append(res.getCustomerEmail()).append("\n");
        content.append("Subject: Booking Confirmation - Ocean View Resort\n");
        content.append("--------------------------------------------------\n");
        content.append("       OCEAN VIEW RESORT - OFFICIAL RECEIPT       \n");
        content.append("--------------------------------------------------\n\n");

        content.append("Dear ").append(res.getCustomerName()).append(",\n\n");
        content.append("Your reservation is confirmed.\n\n");
        content.append("   Room Number:  ").append(roomNumber).append("\n");
        content.append("   Check-In:     ").append(res.getCheckIn()).append("\n");
        content.append("   Check-Out:    ").append(res.getCheckOut()).append("\n");
        content.append("--------------------------------------------------\n");
        content.append("   TOTAL PAID:   $").append(String.format("%.2f", totalAmount)).append("\n");
        content.append("--------------------------------------------------\n\n");

        content.append("Thank you!\nOcean View Management");

        saveToFile(EMAIL_PATH, fileName, content.toString());
    }

    public void sendAccountCreationEmail(String name, String email, String plainPassword) {
        String fileName = "Welcome_" + name.replace(" ", "_") + "_" + System.currentTimeMillis() + ".txt";
        String content = "Subject: Welcome " + name + "\n\nLogin: " + email + "\nPassword: " + plainPassword;
        saveToFile(EMAIL_PATH, fileName, content);
    }

    // --- 2. SEND SMS (New Feature) ---
    public void sendSMS(String phoneNumber, String message) {
        String fileName = "SMS_" + phoneNumber + "_" + System.currentTimeMillis() + ".txt";
        String content = "To: " + phoneNumber + "\n-----------------------\n" + message;

        saveToFile(SMS_PATH, fileName, content);
    }

    // --- 3. HELPER METHOD TO SAVE FILES ---
    private void saveToFile(String folderPath, String fileName, String content) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs(); // Automatically creates the folder if it doesn't exist
        }

        try (FileWriter writer = new FileWriter(new File(folder, fileName))) {
            writer.write(content);
            System.out.println("✅ Notification Saved: " + folderPath + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}