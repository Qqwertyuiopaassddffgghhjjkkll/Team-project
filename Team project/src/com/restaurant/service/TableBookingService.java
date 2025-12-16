package com.restaurant.service;

import com.restaurant.dao.TableBookingDAO;
import com.restaurant.model.TableBooking;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TableBookingService {
    private final TableBookingDAO bookingDAO;

    public TableBookingService(TableBookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }
    public void addBooking(String customerName, String phone, String dateStr,
                           String timeStr, int guestCount, int tableNumber) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            LocalTime time = LocalTime.parse(timeStr);
            TableBooking booking = new TableBooking(0, customerName, phone,
                    date, time, guestCount, tableNumber);
            bookingDAO.addBooking(booking);
        } catch (DateTimeParseException e) {
            System.err.println("Error: Invalid date/time format! Use yyyy-MM-dd (date) and HH:mm (time)");
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    public List<TableBooking> searchBookingsByCustomer(String query) {
        return bookingDAO.searchByCustomer(query);
    }
    public List<TableBooking> filterBookingsByDate(String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            return bookingDAO.filterByDate(date);
        } catch (DateTimeParseException e) {
            System.err.println("Error: Invalid date format! Use yyyy-MM-dd");
            return List.of();
        }
    }
    public List<TableBooking> getAllBookings() {
        return bookingDAO.getAllBookings();
    }
    public boolean cancelBooking(int bookingId) {
        return bookingDAO.cancelBooking(bookingId);
    }
}