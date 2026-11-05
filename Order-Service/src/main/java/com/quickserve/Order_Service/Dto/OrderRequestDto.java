package com.quickserve.Order_Service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private String customerName;
    private String phoneNumber;
    private Long tableId;
    private List<OrderItemDto> items;
} {
  
}
