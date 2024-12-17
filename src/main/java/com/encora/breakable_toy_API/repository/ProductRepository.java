package com.encora.breakable_toy_API.repository;
import com.encora.breakable_toy_API.models.Product;
import java.util.List;

public interface ProductRepository {
    // Definition of operations
    List<Product> findAll(int page); // Get products w/pagination of 10 items per page
    Product create(Product product); // Create new product
    Product outOfStock(Long id); // Make it unavailable and set stock to 0
    Product inStock(Long id, int stock); // Update stock
    Product update(Product product); // Update information of the product
}
