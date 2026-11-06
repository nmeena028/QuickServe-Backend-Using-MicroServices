package com.quickserve.Restaurant_Service.Controller;

import com.quickserve.Restaurant_Service.DTO.CategoryDto;
import com.quickserve.Restaurant_Service.Entity.Category;
import com.quickserve.Restaurant_Service.Entity.Product;
import com.quickserve.Restaurant_Service.Service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
public class MenuController {

    @Autowired
    private MenuService menuService;

    // 1. Customer ke liye Menu fetch karne ki API (QR scan hone par yehi API chalegi)
    @GetMapping("/menu")
    public ResponseEntity<List<CategoryDto>> getMenu() {
        List<CategoryDto> menu = menuService.getAllCategoriesWithAvailableProducts();
        return ResponseEntity.ok(menu);
    }

    // 2. Admin ke liye Category add karne ki API
    @PostMapping("/category")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        Category savedCategory = menuService.addCategory(category);
        return ResponseEntity.ok(savedCategory);
    }

    // 3. Admin ke liye Product/Item add karne ki API
    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = menuService.addProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    // Order Service ke liye product details dene ki API
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Product product = menuService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    
}