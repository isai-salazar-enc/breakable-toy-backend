package com.encora.breakable_toy_API.repository;
import com.encora.breakable_toy_API.models.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    // Definition of operations
    Optional<Product> findById(Long id);
    List<Product> findAll(); // Get products w/pagination of 10 items per page
    Product create(Product product); // Create new product
    Product outOfStock(Long id); // Make it unavailable and set stock to 0
    Product update(Product product); // Update information of the product
    Boolean delete(Long id);
}
