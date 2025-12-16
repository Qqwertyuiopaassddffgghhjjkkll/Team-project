package com.restaurant.dao;
import com.restaurant.model.Dish;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DishDAO {
    private static final String FILE = "dishes.ser";
    private static final int INIT_CAPACITY = 20;
    private Dish[] dishes;
    private int nextId;

    public DishDAO() {
        loadFromFile();
        if (dishes == null) {
            dishes = new Dish[INIT_CAPACITY];
            nextId = 1;
            initDefaultDishes();
            saveToFile();
        }
    }


    private void initDefaultDishes() {
        addDish(new Dish(nextId, "Kung Pao Chicken", "Sichuan Cuisine", 28.0));
        addDish(new Dish(nextId, "Mapo Tofu", "Sichuan Cuisine", 22.0));
        addDish(new Dish(nextId, "Sweet and Sour Pork", "Cantonese Cuisine", 30.0));
        addDish(new Dish(nextId, "Dim Sum Platter", "Cantonese Cuisine", 25.0));
        addDish(new Dish(nextId, "Xiangxi Beef", "Hunan Cuisine", 35.0));
        addDish(new Dish(nextId, "Dong'an Chicken", "Hunan Cuisine", 32.0));
        addDish(new Dish(nextId, "Braised Pork Belly", "Shandong Cuisine", 28.0));
        addDish(new Dish(nextId, "Dezhou Braised Chicken", "Shandong Cuisine", 30.0));
        addDish(new Dish(nextId, "Lion's Head Meatball", "Jiangsu Cuisine", 32.0));
        addDish(new Dish(nextId, "West Lake Vinegar Fish", "Zhejiang Cuisine", 29.0));
    }


    public void addDish(Dish dish) {
        if (nextId - 1 == dishes.length) expandArray();
        dishes[nextId - 1] = dish;
        nextId++;
        saveToFile();
    }


    private void expandArray() {
        Dish[] newDishes = new Dish[dishes.length * 2];
        System.arraycopy(dishes, 0, newDishes, 0, dishes.length);
        dishes = newDishes;
    }


    public List<Dish> getAllDishes() {
        List<Dish> result = new ArrayList<>();
        for (int i = 0; i < nextId - 1; i++) {
            if (dishes[i] != null) result.add(dishes[i]);
        }
        return result;
    }


    public List<Dish> searchByPartialName(String query) {
        List<Dish> result = new ArrayList<>();
        for (Dish dish : getAllDishes()) {
            if (dish.getName().toLowerCase().contains(query.toLowerCase()))
                result.add(dish);
        }
        return result;
    }


    public List<Dish> filterByCategory(String category) {
        List<Dish> result = new ArrayList<>();
        for (Dish dish : getAllDishes()) {
            if (dish.getCategory().equals(category))
                result.add(dish);
        }
        return result;
    }

    //  The data loading from the file was learned from AI and Xiao Bai.
    private void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE))) {
            dishes = (Dish[]) ois.readObject();
            nextId = ois.readInt();
        } catch (IOException | ClassNotFoundException e) {
            // 首次运行时忽略文件不存在错误
        }
    }

    //  Saving data to a file was learned from Ai Wenxiao.
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(dishes);
            oos.writeInt(nextId);
        } catch (IOException e) {
            System.err.println("Error saving dishes: " + e.getMessage());
        }
    }


    public int getNextId() { return nextId; }


    public boolean deleteDish(int dishId) {
        for (int i = 0; i < nextId - 1; i++) {
            if (dishes[i] != null && dishes[i].getId() == dishId) {
                dishes[i] = null;
                saveToFile();
                return true;
            }
        }
        return false;
    }
}