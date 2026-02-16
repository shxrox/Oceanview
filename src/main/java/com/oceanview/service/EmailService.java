package com.oceanview.service;

import com.oceanview.model.Reservation;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EmailService {

    // UPDATE YOUR PATH HERE IF NEEDED
    private static final String FOLDER_PATH = "C:\\Users\\sharo\\OneDrive\\Pictures\\Documents\\ICBT-TOP\\semester-1\\Assessment\\ICBT_CIS6003_Advanced Programing\\OceanView\\FakeEmails\\";

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
        // THIS IS THE LINE THAT PRINTS THE MONEY
        content.append("   TOTAL PAID:   $").append(String.format("%.2f", totalAmount)).append("\n");
        content.append("--------------------------------------------------\n\n");

        content.append("Thank you!\nOcean View Management");

        saveToFile(fileName, content.toString());
    }

    public void sendAccountCreationEmail(String name, String email, String plainPassword) {
        String fileName = "Welcome_" + name.replace(" ", "_") + "_" + System.currentTimeMillis() + ".txt";
        String content = "Subject: Welcome " + name + "\n\nLogin: " + email + "\nPassword: " + plainPassword;
        saveToFile(fileName, content);
    }

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


    public void sendSMS(String phoneNumber, String message) {
        String fileName = "SMS_" + phoneNumber + "_" + System.currentTimeMillis() + ".txt";
        String content = "To: " + phoneNumber + "\nMessage: " + message;

        // Save to a "FakeSMS" folder
        String smsPath = "C:\\Users\\sharo\\OneDrive\\...\\FakeSMS\\"; // Update this path!
        File folder = new File(smsPath);
        if (!folder.exists()) folder.mkdirs();

        try (FileWriter writer = new FileWriter(new File(folder, fileName))) {
            writer.write(content);
            System.out.println("📱 Fake SMS Sent: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}