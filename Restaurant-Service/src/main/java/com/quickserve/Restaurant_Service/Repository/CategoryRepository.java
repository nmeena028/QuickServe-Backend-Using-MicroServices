package com.quickserve.Restaurant_Service.Repository;

import com.quickserve.Restaurant_Service.Entity.Category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  Category findByName(String name);
}
