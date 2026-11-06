package com.quickserve.Order_Service.Repository;

import com.quickserve.Order_Service.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Long> {
   boolean existsByTableIdAndStatus(Long tableId, String status);
}
