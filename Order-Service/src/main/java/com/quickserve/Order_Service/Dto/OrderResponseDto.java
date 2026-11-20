package com.quickserve.Order_Service.Dto;

import java.time.LocalDateTime;

public class OrderResponseDto {
  private Long id;
  private String customerName;
  private String phoneNumber;

    private Double totalAmount;

    private String status; // PENDING, PAID, PREPARING, COMPLETED

    private Long tableId; // Kis table par order diya gaya hai

    private LocalDateTime createdAt;
}
