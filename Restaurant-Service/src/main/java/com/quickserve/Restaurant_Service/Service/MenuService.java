package com.quickserve.Restaurant_Service.Service;


import com.quickserve.Restaurant_Service.DTO.CategoryDto;
import com.quickserve.Restaurant_Service.DTO.ProductDto;
import com.quickserve.Restaurant_Service.Entity.Category;
import com.quickserve.Restaurant_Service.Entity.Product;
import com.quickserve.Restaurant_Service.Repository.CategoryRepository;
import com.quickserve.Restaurant_Service.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    // 1. Saari Categories aur unke Available Products ko fetch karne ka method
    public List<CategoryDto> getAllCategoriesWithAvailableProducts() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(category -> {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());

            // Sirf wahi products map karo jo available hain
            List<ProductDto> productDtos = category.getProducts().stream()
                    .filter(Product::getAvailable) // Sirf available items
                    .map(product -> new ProductDto(
                            product.getId(),
                            product.getName(),
                            product.getDescription(),
                            product.getPrice(),
                            product.getImageUrl(),
                            product.getAvailable(),
                            category.getId()
                    )).collect(Collectors.toList());

            categoryDto.setProducts(productDtos);
            return categoryDto;
        }).collect(Collectors.toList());
    }

    // ID se product dhoondne ke liye
    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    // 2. Naya Category add karne ka method
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    // 3. Naya Product add karne ka method
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
}
