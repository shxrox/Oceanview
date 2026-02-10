package com.oceanview.service;

import com.oceanview.model.Reservation;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmailService {

    // YOUR SPECIFIC FOLDER PATH
    // Note: We use double backslashes (\\) because Java needs them escaped.
    private static final String FOLDER_PATH = "C:\\Users\\sharo\\OneDrive\\Pictures\\Documents\\ICBT-TOP\\semester-1\\Assessment\\ICBT_CIS6003_Advanced Programing\\OceanView\\FakeEmails\\";

    public void sendBookingConfirmation(Reservation res) {
        // 1. Create a unique file name (e.g., Booking_101_20260210.txt)
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "Booking_" + res.getRoomId() + "_" + timeStamp + ".txt";

        File folder = new File(FOLDER_PATH);
        if (!folder.exists()) {
            folder.mkdirs(); // Create folder if it doesn't exist
        }

        File file = new File(folder, fileName);

        // 2. Write the Email Content
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("To: " + res.getCustomerEmail() + "\n");
            writer.write("Subject: Booking Confirmation - Ocean View Resort\n");
            writer.write("--------------------------------------------------\n");
            writer.write("Dear " + res.getCustomerName() + ",\n\n");
            writer.write("Thank you for choosing Ocean View Resort!\n");
            writer.write("Your reservation has been confirmed.\n\n");
            writer.write("DETAILS:\n");
            writer.write("Room ID: " + res.getRoomId() + "\n");
            writer.write("Check-In: " + res.getCheckIn() + "\n");
            writer.write("Check-Out: " + res.getCheckOut() + "\n");
            writer.write("Phone: " + res.getCustomerPhone() + "\n\n");
            writer.write("We look forward to seeing you!\n");
            writer.write("--------------------------------------------------\n");
            writer.write("Ocean View Management System\n");

            System.out.println("Fake Email Saved: " + file.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save fake email.");
        }
    }
}