package com.restaurant.model;

import java.io.Serializable;
import java.util.Arrays;

public class Dish implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String[] CATEGORY_OPTIONS = {
            "Sichuan Cuisine", "Cantonese Cuisine", "Hunan Cuisine",
            "Shandong Cuisine", "Jiangsu Cuisine", "Zhejiang Cuisine"
    };

    private int id;
    private String name;
    private String category;
    private double price;

    public Dish(int id, String name, String category, double price) {
        setId(id);
        setName(name);
        setCategory(category);
        setPrice(price);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID must be positive.");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Name cannot be empty.");
        this.name = name.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if (!Arrays.asList(CATEGORY_OPTIONS).contains(category))
            throw new IllegalArgumentException("Invalid category. Valid options: " + Arrays.toString(CATEGORY_OPTIONS));
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) throw new IllegalArgumentException("Price must be positive.");
        this.price = Math.round(price * 100.0) / 100.0; // 保留两位小数
    }

    //The override of the toString() method was learned on the website bilibili.
    @Override
    public String toString() {
        return String.format("Dish{id=%d, name='%s', category='%s', price=%.2f}",
                id, name, category, price);
    }
}
