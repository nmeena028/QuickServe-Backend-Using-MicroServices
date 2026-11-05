package com.quickserve.Restaurant_Service.Repository;

import com.quickserve.Restaurant_Service.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 1. Sirf wahi products layega jo available hain (Out of stock items hide karne ke liye)
    List<Product> findByAvailableTrue();

    // 2. Kisi specific category ke saare products laane ke liye
    List<Product> findByCategoryId(Long categoryId);
}
