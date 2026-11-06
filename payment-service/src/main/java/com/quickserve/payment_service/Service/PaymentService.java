package com.quickserve.payment_service.Service;

import com.quickserve.payment_service.Client.OrderClient;
import com.quickserve.payment_service.Dto.PaymentRequest;
import com.quickserve.payment_service.Entity.Payment;
import com.quickserve.payment_service.Entity.PaymentStatus;
import com.quickserve.payment_service.Repository.PaymentRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepo paymentRepository;

    @Autowired
    private OrderClient orderClient; // Order status update karne ke liye

    @Transactional 
    public Payment processPayment(PaymentRequest paymentRequest) {
        // Check if a successful payment already exists for this order
        Optional<Payment> existingPayment = paymentRepository.findByOrderId(paymentRequest.getOrderId());
        if (existingPayment.isPresent() && PaymentStatus.SUCCESS.equals(existingPayment.get().getPaymentStatus())) {
            throw new RuntimeException("Payment already completed for Order ID: " + paymentRequest.getOrderId());
        }

        // Payment Process
        Payment payment = new Payment();
        payment.setOrderId(paymentRequest.getOrderId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setCreatedAt(LocalDateTime.now());
        
        Payment savedPayment = paymentRepository.save(payment);

        // --- BUG 3 FIX: ORDER STATUS SYNC ---
        try {
            orderClient.updateOrderStatus(paymentRequest.getOrderId(), "PAID");
        } catch (Exception e) {
            System.err.println("Failed to update order status: " + e.getMessage());
        }
        // ------------------------------------

        return savedPayment;
    }

    public Payment getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId).orElse(null);
    }
}
