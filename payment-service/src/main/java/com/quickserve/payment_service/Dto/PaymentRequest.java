package com.quickserve.payment_service.Dto;

import lombok.Data;
import com.quickserve.payment_service.Entity.PaymentStatus;

@Data
public class PaymentRequest {
    private Long orderId;
    private Double amount;
    private PaymentStatus paymentStatus;
}
