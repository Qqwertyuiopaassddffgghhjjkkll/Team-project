package com.restaurant.view;

import com.restaurant.dao.TableBookingDAO;
import com.restaurant.model.Dish;
import com.restaurant.model.TableBooking;
import com.restaurant.service.DishService;
import com.restaurant.service.TableBookingService;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeParseException;


public class View {

private final Scanner scanner = new Scanner(System.in);
    private final DishService dishService = new DishService();
    private final TableBookingDAO bookingDAO = new TableBookingDAO();

    private final TableBookingService bookingService = new TableBookingService(bookingDAO);

    public void start() {
        System.out.println("=== Restaurant Management System ===");
        while (true) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            handleMenuChoice(choice);
        }
    }

    private void displayMainMenu() {
        clearScreen();
        System.out.println("\n===== Main Menu =====");
        System.out.println("1. Manage Dishes");
        System.out.println("2. Manage Reservations");
        System.out.println("0. Exit System");
        System.out.println("Select an option(enter 0-2" + ")");

        System.out.println("=====================");
    }

    private void handleMenuChoice(int choice) {
        switch (choice) {
            case 1: manageDishes(); break;
            case 2: manageReservations(); break;
            case 0: exitSystem(); break;
            default:
                System.out.println(" Error: Invalid choice! Enter 0-5");
                pressEnterToContinue();
        }
    }

    private void manageDishes() {
        while (true) {
            clearScreen();
            System.out.println("=== Dish Management ===");
            System.out.println("1. Add New Dish");
            System.out.println("2. Update Dish Price");
            System.out.println("3. Delete Dish");
            System.out.println("4. View All Dishes");
            System.out.println("5. searchDishes(by dishes's name)");
            System.out.println("6. filterDishes(by dishes's categories)");
            System.out.println("0. Back to Main Menu");
            int choice = getIntInput("Enter choice: ");
            switch (choice) {
                case 1: addDish(); break;
                case 2: updateDish(); break;
                case 3: deleteDish(); break;
                case 4: viewAllDishes(); break;
                case 5: searchDishes(); break;
                case 6: filterDishes(); break;
                case 0: return;
                default: System.out.println("Invalid choice! Try again");
            }
            pressEnterToContinue();
        }
    }
    private void searchDishes() {
        clearScreen();
        System.out.println("\n--- Search Dishes by Name ---");
        String query = getStringInput("Enter dish name (partial match): ");

        List<Dish> results = dishService.searchDishes(query);

        if (results.isEmpty()) {
            System.out.println("No dishes found with name containing: " + query);
        } else {
            System.out.println("\n=== Search Results (" + results.size() + ") ===");
            results.forEach(System.out::println);
        }
    }
    private void filterDishes() {
        clearScreen();
        System.out.println("\n--- Filter Dishes by Category ---");
        System.out.println("Available categories:");
        for (int i = 0; i < Dish.CATEGORY_OPTIONS.length; i++) {
            System.out.printf("%d. %s\n", i + 1, Dish.CATEGORY_OPTIONS[i]);
        }

        int choice = getIntInput("Select category (1-" + Dish.CATEGORY_OPTIONS.length + "): ");
        if (choice < 1 || choice > Dish.CATEGORY_OPTIONS.length) {
            System.out.println("Invalid category selection!");
            return;
        }

        String category = Dish.CATEGORY_OPTIONS[choice - 1];
        List<Dish> results = dishService.filterDishes(category);

        if (results.isEmpty()) {
            System.out.println("No dishes found in category: " + category);
        } else {
            System.out.println("\n=== " + category + " Dishes (" + results.size() + ") ===");
            results.forEach(System.out::println);
        }
    }





    private void manageReservations() {
        while (true) {
            clearScreen();
            System.out.println("=== Reservation Management ===");
            System.out.println("1. Add New Booking");
            System.out.println("2. View All Bookings");
            System.out.println("3. Cancel Booking");
            System.out.println("4. searchBookingsByCustomer");
            System.out.println("5. filterBookingsByDate");
            System.out.println("0. Back to Main Menu");

            int choice = getIntInput("Enter choice: ");
            switch (choice) {
                case 1: addTableBooking(); break;
                case 2: viewAllBookings(); break;
                case 3: cancelBooking(); break;
                case 4: searchBookings(); break;//searchBookingsByCustomer
                case 5: viewBookingsByDate();break;//filterBookingsByDate
                case 0: return;
                default: System.out.println("Invalid choice! Try again");
            }
            pressEnterToContinue();
        }
    }
    private void addTableBooking() {
        clearScreen();
        System.out.println("=== Add New Booking ===");
        String customerName = getStringInput("Customer Name: ");
        String phone = getStringInput("Phone (e.g., 13812345678): ");
        String date = getStringInput("Date (YYYY-MM-DD): ");
        String time = getStringInput("Time (HH:MM): ");
        int guests = getIntInput("Number of Guests: ");
        int table = getIntInput("Table Number (≥101): ");
        bookingService.addBooking(customerName, phone, date, time, guests, table);
    }

    private void viewAllBookings() {
        clearScreen();
        System.out.println("=== All Bookings ===");
        List<TableBooking> allBookings = bookingService.getAllBookings();
        displayBookings(allBookings, "Total Bookings: " + allBookings.size());
    }

    private void cancelBooking() {
        clearScreen();
        System.out.println("=== Cancel Booking ===");
        viewAllBookings();
        if (bookingService.getAllBookings().isEmpty()) return;

        int bookingId = getIntInput("Enter booking ID to cancel: ");
        boolean success = bookingService.cancelBooking(bookingId);
        if (success) {
            System.out.println(" Booking #" + bookingId + " cancelled successfully");
        } else {
            System.out.println(" Booking #" + bookingId + " not found");
        }
    }
    private void addDish() {
        clearScreen();
        System.out.println("=== Add New Dish ===");
        String name = getStringInput("Dish Name: ");
        double price = getDoubleInput("Price: ");
        String category = getStringInput("Category(\"Sichuan Cuisine\", \"Cantonese Cuisine\", \"Hunan Cuisine\",\n" +
                "            \"Shandong Cuisine\", \"Jiangsu Cuisine\", \"Zhejiang Cuisine\"): ");
        System.out.println("Category should be chosen from Cuisine/Shandong Cuisine/Jiangsu Cuisine/Zhejiang Cuisine");
        dishService.addDish(name, category, price);
        System.out.println("Dish '" + name + "' added successfully");
    }

    private void updateDish() {
        clearScreen();
        System.out.println("=== Update Dish Price ===");
        int dishId = getIntInput("Enter dish ID: ");
        double newPrice = getDoubleInput("Enter new price: ");
        System.out.println("Dish #" + dishId + " price updated successfully");
    }

    private void deleteDish() {
        clearScreen();
        System.out.println("=== Delete Dish ===");
        int dishId = getIntInput("Enter dish ID to delete: ");
        boolean success = dishService.deleteDish(dishId);
        if (success) {
            System.out.println("Dish #" + dishId + " deleted successfully");
        } else {
            System.out.println("Dish #" + dishId + " not found");
        }
    }

    private void viewAllDishes() {
        clearScreen();
        System.out.println("=== All Dishes ===");
        List<Dish> dishes = dishService.getAllDishes();
        if (dishes.isEmpty()) {
            System.out.println("No dishes available");
            return;
        }
        System.out.printf("%-4s %-20s %-20s %s%n", "ID", "Name", "Category", "Price");
        //The formatting output template of Java used AI to help the novice.
        System.out.println("-----------------------------------------------");
        for (Dish dish : dishes) {
            //The formatting output template of Java used AI to help the novice.
            System.out.printf("%-4d %-20s %-20s ¥%.2f%n",
                    dish.getId(), dish.getName(), dish.getCategory(), dish.getPrice());
        }
    }
    private void displayBookings(List<TableBooking> bookings, String title) {
        System.out.println("\n===== " + title + " =====");
        if (bookings.isEmpty()) {
            System.out.println("No bookings found");
            return;
        }
        //The formatting output template of Java used AI to help the novice.
        System.out.printf("%-4s %-15s %-12s %-12s %-8s %-6s %s%n",
                "ID", "Customer", "Phone", "Date", "Time", "Guests", "Table");
        System.out.println("-----------------------------------------------------");
        for (TableBooking booking : bookings) {
            //The formatting output template of Java used AI to help the novice.
            System.out.printf("%-4d %-15s %-12s %-12s %-8s %-6d %d%n",
                    booking.getId(),
                    truncate(booking.getCustomerName(), 14),
                    booking.getPhone(),
                    booking.getDate(),
                    booking.getTime(),
                    booking.getGuestCount(),
                    booking.getTableNumber());
        }
    }
    private void searchBookings() {
        clearScreen();
        System.out.println("\n--- Search Bookings by Customer Name ---");
        String query = getStringInput("Enter customer name (partial match): ");

        List<TableBooking> results = bookingService.searchBookingsByCustomer(query);

        if (results.isEmpty()) {
            System.out.println("\nNo bookings found for customers containing: " + query);
        } else {
            System.out.println("\n=== Search Results (" + results.size() + ") ===");
            results.forEach(System.out::println);
        }
    }
    private void viewBookingsByDate() {
        clearScreen();
        System.out.println("\n--- View Bookings by Date ---");
        String dateStr = getStringInput("Enter date (yyyy-MM-dd): ");

        try {
            List<TableBooking> results = bookingService.filterBookingsByDate(dateStr);

            if (results.isEmpty()) {
                System.out.println("\nNo bookings found on " + dateStr);
            } else {
                System.out.println("\n=== Bookings on " + dateStr + " (" + results.size() + ") ===");
                results.forEach(System.out::println);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date format! Please use yyyy-MM-dd format.");
        }
    }
/*
Using AI to ask Xiaobai solved the problem of null value checks:
if the passed string is null, an empty string is directly returned;
conditional judgment and truncation processing: if the string length exceeds the maximum limit,
the first (maxLength - 1) characters are truncated and an ellipsis "..." is added;
if the string length does not exceed the limit, it is returned as is.
* */
    private String truncate(String str, int maxLength) {
        if (str == null) return "";
        return str.length() > maxLength ? str.substring(0, maxLength - 1) + "…" : str;
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Enter a valid number");
            }
        }
    }

    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Enter a valid number");
            }
        }
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();//The trim() method was learned independently.
    }

/*clearScreen() Method
This is a cross-platform method for clearing the screen, used to erase the displayed content on the console.
 This step is learned and completed by asking AI to teach Xiao Bai.
* */
    private void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
            }
            System.out.flush();
        } catch (Exception e) {
            System.out.println("\n".repeat(50));
        }
    }

    private void pressEnterToContinue() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void exitSystem() {
        System.out.println("\nThank you for using Restaurant Management System!");
        System.out.println("Exiting...");
        System.exit(0);
    }


}