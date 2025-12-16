package com.restaurant.dao;

import com.restaurant.model.TableBooking;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TableBookingDAO {
    private List<TableBooking> bookings = new ArrayList<>();
    private int nextId = 1;
    private static final String FILE_NAME = "bookings.ser";

    public TableBookingDAO() {
        loadFromFile();
        if (bookings.isEmpty()) {
            initDefaultBookings();
        }
    }

    private void initDefaultBookings() {
        try {
            addBooking(new TableBooking(0, "Zhang San", "13812345678",
                    LocalDate.now().plusDays(1), LocalTime.of(18, 0), 2, 101));
            addBooking(new TableBooking(0, "Li Si", "13987654321",
                    LocalDate.now().plusDays(1), LocalTime.of(19, 0), 4, 102));
        } catch (IllegalArgumentException e) {
            System.err.println("Failed to add default booking: " + e.getMessage());
        }
    }

    public void addBooking(TableBooking booking) {
        if (booking == null) {
            System.err.println("Error: Cannot add a null booking!");
            return;
        }
        if (isTableAvailable(booking.getTableNumber(), booking.getDate(), booking.getTime())) {
            booking.setId(nextId++);
            bookings.add(booking);
            saveToFile();
        } else {
            System.err.println("Error: Table " + booking.getTableNumber()
                    + " is not available on " + booking.getDate() + " " + booking.getTime() + "!");
        }
    }

    private boolean isTableAvailable(int tableNumber, LocalDate date, LocalTime time) {
        return bookings.stream()
                .noneMatch(b -> b.getTableNumber() == tableNumber
                        && b.getDate().equals(date)
                        && b.getTime().equals(time));
    }

    /*按顾客姓名模糊搜索 The fuzzy search by customer name was learned and obtained from AI Xiaobai.

    bookings.stream() 将列表转换为流
     b -> 对每个预订对象b
     b.getCustomerName()获取顾客姓名
     .toLowerCase()转为小写
     .contains(lowerCaseQuery)检查是否包含搜索词
     .collect(Collectors.toList());将流重新收集为列表


     Step 1: Input validation
Step 2: Convert to lowercase (case-insensitive)
Step 3: Stream processing filtering
Step 4: Collect results and return
The bookings.stream() converts the list into a stream
b -> For each booking object b
b.getCustomerName() retrieves the customer name
.toLowerCase() converts to lowercase
.contains(lowerCaseQuery) checks if it contains the search term .collect(Collectors.toList()); Recollect the flow as a list

   * */
    public List<TableBooking> searchByCustomer(String query) {
        if (query == null || query.isBlank()) {
            return new ArrayList<>();
        }
        String lowerCaseQuery = query.toLowerCase();
        return bookings.stream()
                .filter(b -> b.getCustomerName().toLowerCase().contains(lowerCaseQuery))
                .collect(Collectors.toList());
    }

    public List<TableBooking> filterByDate(LocalDate date) {
        if (date == null) {
            return new ArrayList<>();
        }
        return bookings.stream()
                .filter(b -> b.getDate().equals(date))
                .collect(Collectors.toList());
    }

    public List<TableBooking> getAllBookings() {
        return new ArrayList<>(bookings);
    }
    public boolean cancelBooking(int bookingId) {
        boolean removed = bookings.removeIf(b -> b.getId() == bookingId);
        if (removed) saveToFile();
        return removed;
    }
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(bookings);
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
    //  The data loading from the file was learned from AI and Xiao Bai.
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                bookings = (List<TableBooking>) ois.readObject();
                nextId = bookings.stream()
                        .mapToInt(TableBooking::getId)
                        .max()
                        .orElse(0) + 1;
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading data: " + e.getMessage());
            }
        }
    }
}