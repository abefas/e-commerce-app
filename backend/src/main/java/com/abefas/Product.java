package com.abefas;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity  // Marks this class as a JPA entity mapped to a database table
@Table(name = "products")
public class Product {

    @Id  // Primary key field
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generated value by database
    private Long id;

    @Column(nullable = false, length = 100)  // Non-null, max 100 characters
    private String name;

    @Column  // Defaults, can be null
    private String description;

    @Column(nullable = false)  // Non-null price
    private Double price;

    @Column(nullable = false)  // Non-null stock quantity
    private Integer stockQuantity;

    // Default constructor required by JPA
    public Product() {
    }

    // Getters and setters for JPA and Spring to access properties

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
