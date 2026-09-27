package com.example.restaurantcache.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "menu_items")
public class MenuItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "dish_name")
    private String dishName;

    private Double price;

    public MenuItem() {}

    public MenuItem(Long id, Long restaurantId, String dishName, Double price) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.dishName = dishName;
        this.price = price;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }

    public String getDishName() { return dishName; }
    public void setDishName(String dishName) { this.dishName = dishName; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
