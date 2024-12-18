package com.encora.breakable_toy_API.service;

import com.encora.breakable_toy_API.models.Product;
import com.encora.breakable_toy_API.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.EntityResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    // Get all products
    public List<Product> getProducts(int page){
        return productRepository.findAll(page);
    }

    // Set stock to 0
    public Product outOfStock(Long id){
        return productRepository.outOfStock(id);
    }

    // Create a new product
    public Product create(Product product){
        // Validate name
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("The product name cannot be null or empty");
        }
        if (product.getName().length() > 120) {
            throw new IllegalArgumentException("The product name cannot exceed 120 characters");
        }

        // Validate price
        if (product.getUnitPrice() == null || product.getUnitPrice() <= 0) {
            throw new IllegalArgumentException("The unit price must be greater than 0");
        }

        // Validate expiration date
        if (product.getExpirationDate() == null) {
            throw new IllegalArgumentException("The expiration date cannot be null");
        }

        // Validate stock
        if (product.getStock() == null || product.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.create(product);
    }

}
