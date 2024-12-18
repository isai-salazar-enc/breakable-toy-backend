package com.encora.breakable_toy_API.repository;

import com.encora.breakable_toy_API.models.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

// Repository that manages in memory items instead of a database
@Repository
public class InMemoryProductRepository implements ProductRepository{

    private final Map<Long, Product> products = new HashMap<>();
    private static long currentId = 1;

    // Load data in HashMap when API is initialized
    @PostConstruct
    public void loadInitialData(){
        for(int i = 1; i<=30; i++){
            products.put(currentId++, new Product( (long) i, "Product " + i, 100F, LocalDate.now(), 10, LocalDateTime.now(), LocalDateTime.now()));
        }
    }

    // Return every product
    @Override
    public List<Product> findAll(int page) {
        if(page<1){
            throw new IllegalAccessError("Page value not allowed");
        }

        int size = 10;
        List<Product> productList = new ArrayList<Product>(products.values());
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, productList.size());
        if (fromIndex >= productList.size()) {
            return Collections.emptyList();
        }

        return productList.subList(fromIndex, toIndex); // Pagination
    }

    @Override
    public Product create(Product product) {
        product.setId(currentId);
        products.put(currentId++, product);
        return product;
    }

    @Override
    public Product outOfStock(Long id) {
        Product item = products.get(id);
        item.setStock(0);
        item.setUpdatedAt(LocalDateTime.now());
        return item;
    }

    @Override
    public Product inStock(Long id, int stock) {
        return null;
    }

    @Override
    public Product update(Product product) {
        return null;
    }
}
