package com.quickserve.payment_service.Service;

import com.quickserve.payment_service.Entity.Payment;
import com.quickserve.payment_service.Repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.quickserve.payment_service.Entity.PaymentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepo paymentRepository;

    public Payment processPayment(Payment payment) {
      
        payment.setTransactionId("TXN_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setCreatedAt(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    public Payment getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }
}
