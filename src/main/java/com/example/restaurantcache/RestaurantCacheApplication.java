package com.example.restaurantcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RestaurantCacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestaurantCacheApplication.class, args);
    }
}
