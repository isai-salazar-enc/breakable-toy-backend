package com.encora.breakable_toy_API.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProductWithCategoryDTO {
    private Long id;
    private Long idCategory;
    private String category;
    private String name; // Max 120 characters
    private Float unitPrice;
    private LocalDate expirationDate;
    private Integer stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductWithCategoryDTO(Long id, Long idCategory, String category, String name, Float unitPrice, LocalDate expirationDate, Integer stock, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.idCategory = idCategory;
        this.category = category;
        this.name = name;
        this.unitPrice = unitPrice;
        this.expirationDate = expirationDate;
        this.stock = stock;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Constructor to create a ProductWithCategoryDTO from a Product and a category name
    public ProductWithCategoryDTO(Product product, String categoryName) {
        this.id = product.getId();
        this.idCategory = product.getIdCategory();
        this.category = categoryName;
        this.name = product.getName();
        this.unitPrice = product.getUnitPrice();
        this.expirationDate = product.getExpirationDate();
        this.stock = product.getStock();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
