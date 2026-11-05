package com.quickserve.Restaurant_Service.Repository;

import com.quickserve.Restaurant_Service.Entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuItem, Long> {
    
    // Yeh method sirf wahi items layega jo available hain (Out of stock items hide karne ke liye)
    List<MenuItem> findByIsAvailableTrue();
}
