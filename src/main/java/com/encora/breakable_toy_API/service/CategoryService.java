package com.encora.breakable_toy_API.service;

import com.encora.breakable_toy_API.models.Category;
import com.encora.breakable_toy_API.repository.InMemoryCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final InMemoryCategoryRepository categoryRepository;

    @Autowired
    public CategoryService(InMemoryCategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }
}
