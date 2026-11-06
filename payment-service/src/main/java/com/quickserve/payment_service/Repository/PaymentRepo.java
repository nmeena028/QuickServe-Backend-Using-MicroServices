package com.quickserve.payment_service.Repository;

import com.quickserve.payment_service.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
  
  Optional<Payment> findByOrderId(Long orderId);
}
