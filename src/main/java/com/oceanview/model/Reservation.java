package com.oceanview.model;

import java.sql.Date; // IMPORT THIS!
import java.sql.Timestamp;

public class Reservation {
    private int id;
    private int roomId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private Date checkIn;
    private Date checkOut;
    private Timestamp bookingDate;

    public Reservation() {}

    // Update Constructor to include dates
    public Reservation(int roomId, String customerName, String customerEmail, String customerPhone, Date checkIn, Date checkOut) {
        this.roomId = roomId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.checkIn = checkIn;   // NEW
        this.checkOut = checkOut; // NEW
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    // NEW GETTERS AND SETTERS
    public Date getCheckIn() { return checkIn; }
    public void setCheckIn(Date checkIn) { this.checkIn = checkIn; }

    public Date getCheckOut() { return checkOut; }
    public void setCheckOut(Date checkOut) { this.checkOut = checkOut; }

    public Timestamp getBookingDate() { return bookingDate; }
    public void setBookingDate(Timestamp bookingDate) { this.bookingDate = bookingDate; }
}