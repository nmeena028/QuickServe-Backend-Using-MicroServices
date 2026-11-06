package com.quickserve.Restaurant_Service.Service;

import org.springframework.stereotype.Service;

import com.netflix.discovery.converters.Auto;
import com.quickserve.Restaurant_Service.Entity.Product;
import com.quickserve.Restaurant_Service.Repository.ProductRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
  
@Service 
@RequiredArgsConstructor
public class ProductService {

    
    private final ProductRepository productRepository;
  
    // Method to update the availability of a product
    public void updateAvailability(Long id, boolean available) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setAvailable(available);
        productRepository.save(product);
    }
}
