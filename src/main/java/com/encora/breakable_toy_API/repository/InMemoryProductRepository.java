package com.encora.breakable_toy_API.repository;

import com.encora.breakable_toy_API.models.Product;
import com.encora.breakable_toy_API.models.ProductWithCategoryDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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
            if(i%5==0){
                products.put(currentId++, new Product( (long) i, (long) (i%5 + 1), "Product " + i, 25.70F, LocalDate.now(), 6, LocalDateTime.now(), LocalDateTime.now()));
                continue;
            }
            if(i%2==0){
                products.put(currentId++, new Product( (long) i, (long) (i%5 + 1), "Product " + i, 25.70F, LocalDate.of(2025, Month.AUGUST, 25), 6, LocalDateTime.now(), LocalDateTime.now()));
                continue;
            }
            products.put(currentId++, new Product( (long) i, (long) (i%5 + 1), "Product " + i, 100F, LocalDate.now(), 1, LocalDateTime.now(), LocalDateTime.now()));
        }
    }

    /*
     * Find products by Id or return a null object.
     */
    @Override
    public Optional<Product> findById(Long id){
        return Optional.ofNullable(products.get(id));
    }

    // Return every product
    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
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
    public Product update(Product product) {
        products.put(product.getId(), product);
        return product;
    }
}
