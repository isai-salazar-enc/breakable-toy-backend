package com.encora.breakable_toy_API.models;

/**
 * Data Transfer Object (DTO) for updating the stock of a product.
 * This class encapsulates the data required to update the stock.
 */
public class UpdateStockDTO {
    private Integer stock;

    public UpdateStockDTO(Integer stock){
        this.stock = stock;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
