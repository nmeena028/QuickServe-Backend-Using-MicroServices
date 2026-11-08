package com.quickserve.Restaurant_Service.Service;

import org.springframework.stereotype.Service;

import com.netflix.discovery.converters.Auto;
import com.quickserve.Restaurant_Service.Entity.Product;
import com.quickserve.Restaurant_Service.Entity.Category;
import com.quickserve.Restaurant_Service.Repository.CategoryRepository;
import com.quickserve.Restaurant_Service.Repository.ProductRepository;

import com.quickserve.Restaurant_Service.DTO.ProductDto;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
  
@Service 
@RequiredArgsConstructor
public class ProductService {

    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
  
    // Method to update the availability of a product
    public void updateAvailability(Long id, boolean available) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setAvailable(available);
        productRepository.save(product);
    }

    public List<Product> getProductListByCategory(String categoryName) {
        Category category = categoryRepository.findByName(categoryName); 
        return  productRepository.findByCategory(category);
        // productList.stream().map(product->{
        //     ProductDto p1 = new ProductDto();
        //     p1.setId(product.getId());
        //     p1.setAvailable(product.getAvailable());
        //     p1.setCategoryId(product.getId());
        //     p1.setDescription(product.getDescription());
        //     p1.setImageUrl(product.getImageUrl());
        //     p1.setName(product.getName());
        //     p1.setPrice(product.getPrice());
        // }).toList();
    }
}
