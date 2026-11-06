package com.quickserve.Restaurant_Service.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.quickserve.Restaurant_Service.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class RestaurantController {

  @Autowired
  private ProductService productService;

  @PutMapping("/products/{id}/availability")
public ResponseEntity<String> updateAvailability(@PathVariable Long id, @RequestParam boolean available) {
    productService.updateAvailability(id, available);
    return ResponseEntity.ok("Product availability updated successfully!");
}
  
}
