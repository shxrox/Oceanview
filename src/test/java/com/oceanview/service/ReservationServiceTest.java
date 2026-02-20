package com.oceanview.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceTest {

    // Instantiating the Service to be tested
    ReservationService service = new ReservationService();
    double pricePerNight = 150.0; // Standard room rate for testing

    // Test 1: Valid Lower Boundary (1 Night)
    @Test
    public void testCalculateTotal_ValidLowerBoundary() {
        double total = service.calculateTotalBill(1, pricePerNight);
        assertEquals(150.0, total, "1 night should cost 150.0");
    }

    // Test 2: Valid Upper Boundary (30 Nights)
    @Test
    public void testCalculateTotal_ValidUpperBoundary() {
        double total = service.calculateTotalBill(30, pricePerNight);
        assertEquals(4500.0, total, "30 nights should cost 4500.0");
    }

    // Test 3: Invalid Lower Boundary (0 Nights) - Should throw Exception
    @Test
    public void testCalculateTotal_InvalidZeroNights() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.calculateTotalBill(0, pricePerNight);
        });
        assertEquals("Booking duration must be between 1 and 30 nights.", exception.getMessage());
    }

    // Test 4: Invalid Upper Boundary (31 Nights) - Should throw Exception
    @Test
    public void testCalculateTotal_InvalidOverMaxNights() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.calculateTotalBill(31, pricePerNight);
        });
        assertEquals("Booking duration must be between 1 and 30 nights.", exception.getMessage());
    }
}