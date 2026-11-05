package com.quickserve.Restaurant_Service.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "menu_items")
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private String category;
    
    // Yeh sabse important field hai jo check karegi item available hai ya nahi
    private boolean isAvailable; 
}
