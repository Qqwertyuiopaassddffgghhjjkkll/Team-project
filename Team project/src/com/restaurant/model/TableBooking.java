package com.restaurant.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
//The Serializable interface and serialVersionUID were learned from the source "ai asked Xiao Bai".
public class TableBooking implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String customerName;
    private String phone;
    private LocalDate date;
    private LocalTime time;
    private int guestCount;
    private int tableNumber;

    public TableBooking(int id, String customerName, String phone,
                        LocalDate date, LocalTime time,
                        int guestCount, int tableNumber) {
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be empty!");
        }
        //The verification condition for the mobile phone number was learned from AI's interaction with Xiaobai.
        if (phone == null || !phone.matches("1[3-9]\\d{9}")) {
            throw new IllegalArgumentException("Invalid phone number format (e.g., 13812345678)");
        }
        if (date == null || date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Booking date must be in the future!");
        }
        if (time == null) {
            throw new IllegalArgumentException("Booking time cannot be null!");
        }
        if (guestCount < 1) {
            throw new IllegalArgumentException("Guest count must be at least 1!");
        }
        if (tableNumber < 101) {
            throw new IllegalArgumentException("Table number must be ≥101!");
        }

        this.id = id;
        this.customerName = customerName;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.guestCount = guestCount;
        this.tableNumber = tableNumber;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }
    public int getGuestCount() { return guestCount; }
    public void setGuestCount(int guestCount) { this.guestCount = guestCount; }
    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }

//The override of the toString() method was learned on the website bilibili.
    @Override
    public String toString() {
        return String.format("Booking[ID=%d, Customer=%s, Phone=%s, Date=%s, Time=%s, Guests=%d, Table=%d]",
                id, customerName, phone, date, time, guestCount, tableNumber);
    }
}