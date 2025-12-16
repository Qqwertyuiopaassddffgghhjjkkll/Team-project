package com.restaurant.service;

import com.restaurant.dao.DishDAO;
import com.restaurant.model.Dish;
import java.util.List;

public class DishService {
    private final DishDAO dishDAO;

    public DishService() {
        this.dishDAO = new DishDAO();
    }
    public void addDish(String name, String category, double price) {
        for (Dish d : dishDAO.getAllDishes()) {
            if (d.getName().equalsIgnoreCase(name))
                throw new IllegalArgumentException("Dish already exists");
        }
        Dish newDish = new Dish(dishDAO.getNextId(), name, category, price);
        dishDAO.addDish(newDish);
    }
    public List<Dish> getAllDishes() {
        return dishDAO.getAllDishes();
    }
    public List<Dish> searchDishes(String query) {
        return dishDAO.searchByPartialName(query);
    }
    public List<Dish> filterDishes(String category) {
        return dishDAO.filterByCategory(category);
    }
    public boolean deleteDish(int dishId) {
        return dishDAO.deleteDish(dishId);
    }


}