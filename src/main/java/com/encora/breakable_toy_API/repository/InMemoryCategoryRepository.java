package com.encora.breakable_toy_API.repository;

import com.encora.breakable_toy_API.models.Category;
import com.encora.breakable_toy_API.models.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryCategoryRepository {
    private final Map<Long, Category> categories = new HashMap<>();
    private static long currentId = 1;

    // Load data in HashMap when API is initialized
    @PostConstruct
    public void loadInitialData(){
        for(int i = 1; i<=5; i++){
            categories.put(currentId++, new Category( (long) i, "Category " + 1));
        }
    }

    public Category findById(Long id) {
        return categories.get(id);
    }
}
