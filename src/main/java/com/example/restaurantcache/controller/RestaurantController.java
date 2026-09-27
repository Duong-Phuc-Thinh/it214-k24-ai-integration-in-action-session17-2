package com.example.restaurantcache.controller;

import com.example.restaurantcache.entity.MenuItem;
import com.example.restaurantcache.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<List<MenuItem>> getMenuByRestaurantId(@PathVariable("restaurantId") Long restaurantId) {
        List<MenuItem> menu = restaurantService.getMenuByRestaurantId(restaurantId);
        return ResponseEntity.ok(menu);
    }
}
