package com.oceanview.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailUtility {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = EmailUtility.class.getClassLoader().getResourceAsStream("mail.properties")) {
            if (input == null) {
                System.out.println("Error: mail.properties not found");
            } else {
                properties.load(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendEmail(String recipient, String subject, String content) {
        // 1. Get the Session object with Authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        properties.getProperty("mail.username"),
                        properties.getProperty("mail.password")
                );
            }
        });

        try {
            // 2. Create the Message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.getProperty("mail.username")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(content); // Plain text email. Use setContent() for HTML.

            // 3. Send the Message
            Transport.send(message);
            System.out.println("Email sent successfully to: " + recipient);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}