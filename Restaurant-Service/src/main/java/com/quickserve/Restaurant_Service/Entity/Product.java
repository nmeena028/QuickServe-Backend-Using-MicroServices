package com.quickserve.Restaurant_Service.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name cannot be blank")
    private String name;

    private String description;

    @NotNull(message = "Price is mandatory")
    private Double price;

    private String imageUrl;

    // Yeh flag batayega ki item abhi available hai ya out of stock
    private Boolean available = true;

    // Kai saare products ek category se jud sakte hain
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}