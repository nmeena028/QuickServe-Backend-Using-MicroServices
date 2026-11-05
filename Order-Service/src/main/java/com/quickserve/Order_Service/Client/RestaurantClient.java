package com.quickserve.Order_Service.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "restaurant-service") // Eureka par registered naam
public interface RestaurantClient {

    // Restaurant service se product details laane ke liye API call
    @GetMapping("/api/restaurant/product/{id}")
    Object getProductById(@PathVariable("id") Long id);
}
