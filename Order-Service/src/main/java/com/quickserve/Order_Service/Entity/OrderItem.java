package com.quickserve.Order_Service.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId; // Restaurant service se product ki ID

    private Integer quantity;

    private Double price; // Jis price par item order kiya gaya

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;
}
