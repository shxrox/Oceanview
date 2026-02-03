package com.oceanview.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmailUtility {

    // UPDATE: Your specific folder path
    // We use double backslashes (\\) because single backslash is a special character in Java
    private static final String EMAIL_FOLDER = "C:\\Users\\sharo\\OneDrive\\Pictures\\Documents\\ICBT-TOP\\semester-1\\Assessment\\ICBT_CIS6003_Advanced Programing\\OceanView\\FakeEmails";

    public static void sendEmail(String recipient, String subject, String content) {
        // 1. Create the directory if it doesn't exist
        File directory = new File(EMAIL_FOLDER);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Folder created: " + EMAIL_FOLDER);
            }
        }

        // 2. Generate a unique filename
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String safeRecipient = recipient.replaceAll("[^a-zA-Z0-9.-]", "_");
        String filename = "email_to_" + safeRecipient + "_" + timestamp + ".txt";

        File emailFile = new File(directory, filename);

        // 3. Write the "Email" content to the file
        try (FileWriter writer = new FileWriter(emailFile)) {
            writer.write("To: " + recipient + "\n");
            writer.write("Subject: " + subject + "\n");
            writer.write("Date: " + LocalDateTime.now() + "\n");
            writer.write("--------------------------------------------------\n");
            writer.write(content);
            writer.write("\n--------------------------------------------------\n");
            writer.write("[End of Message]");

            System.out.println("✅ Email simulated! Check file: " + emailFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("❌ Failed to save email file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}