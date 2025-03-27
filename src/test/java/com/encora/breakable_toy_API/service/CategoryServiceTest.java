package com.encora.breakable_toy_API.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.encora.breakable_toy_API.models.Category;
import com.encora.breakable_toy_API.repository.InMemoryCategoryRepository;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private InMemoryCategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    // Test getCategories() in CategoryService
    public void testGetCategories_ReturnsAllCategories() {
        //  Mock repository
        List<Category> mockCategories = Arrays.asList(
                new Category(1L, "Electronics"),
                new Category(2L, "Books")
        );
        when(categoryRepository.findAll()).thenReturn(mockCategories);

        List<Category> result = categoryService.getCategories();

        assertEquals(mockCategories, result); // Verify the result
        verify(categoryRepository, times(1)).findAll(); // Verify the interaction with the mock repository
    }

    @Test
    // Test getCategories() when no categories are found
    public void testGetCategories_ReturnsEmptyList() {
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        List<Category> result = categoryService.getCategories();

        assertEquals(Collections.emptyList(), result);
        verify(categoryRepository, times(1)).findAll();
    }
}
