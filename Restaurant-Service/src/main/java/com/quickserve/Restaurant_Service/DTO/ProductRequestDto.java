package com.quickserve.Restaurant_Service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor 
public class ProductRequestDto {
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Boolean available;
    private Long categoryId;
}
