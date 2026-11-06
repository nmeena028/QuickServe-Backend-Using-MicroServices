package com.quickserve.payment_service.Repository;

import com.quickserve.payment_service.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
  
  Payment findByOrderId(Long orderId);
}
