package com.oceanview.model;

public class Room {
    private int id;
    private String roomNumber;
    private String roomType;
    private double pricePerNight;
    private boolean isAvailable;

    // NEW FIELDS
    private String imageUrl;
    private String description;

    public Room() {}

    // Updated Constructor
    public Room(int id, String roomNumber, String roomType, double pricePerNight, boolean isAvailable, String imageUrl, String description) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.isAvailable = isAvailable;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    // NEW GETTERS/SETTERS
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}