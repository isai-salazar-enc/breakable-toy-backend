package com.encora.breakable_toy_API.repository;

import com.encora.breakable_toy_API.models.Category;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryCategoryRepository {
    private final Map<Long, Category> categories = new HashMap<>();
    private static long currentId = 1;

    // Load data in HashMap when API is initialized
    @PostConstruct
    public void loadInitialData(){
        for(int i = 1; i<=5; i++){
            categories.put(currentId++, new Category( (long) i, "Category " + i));
        }
    }

    public Category findById(Long id) {
        return categories.get(id);
    }

    public List<Category> findAll(){
        return new ArrayList<>(categories.values());
    }
}
