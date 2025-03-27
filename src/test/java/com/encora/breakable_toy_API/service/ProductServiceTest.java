package com.encora.breakable_toy_API.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.encora.breakable_toy_API.models.Category;
import com.encora.breakable_toy_API.models.Product;
import com.encora.breakable_toy_API.models.ProductWithCategoryDTO;
import com.encora.breakable_toy_API.models.UpdateStockDTO;
import com.encora.breakable_toy_API.repository.InMemoryCategoryRepository;
import com.encora.breakable_toy_API.repository.InMemoryProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private InMemoryProductRepository productRepository;

    @Mock
    private InMemoryCategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    // Test getProducts
    public void testGetProducts_ReturnsAllProducts() {
        // Arrange: Mock the product repository and category repository with sample data
        Product product1 = new Product(1L, 1L, "Product 1", 10.0f, null, 5, null, null);
        Product product2 = new Product(2L, 2L, "Product 2", 20.0f, null, 10, null, null);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        when(categoryRepository.findById(1L)).thenReturn(new Category(1L, "Category 1"));
        when(categoryRepository.findById(2L)).thenReturn(new Category(2L, "Category 2"));

        // Act
        List<ProductWithCategoryDTO> result = productService.getProducts();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Category 1", result.get(0).getCategory());
        assertEquals("Category 2", result.get(1).getCategory());
        verify(productRepository, times(1)).findAll();
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).findById(2L);
    }

    @Test
    // Obtain exception when page is less than 1
    public void testGetPaginatedProducts_InvalidPage_ThrowsException() {
        // Act & Assert
        assertThrows(IllegalAccessError.class, () -> productService.getPaginatedProducts(0));
    }

    @Test
    // Test getPaginatedProducts to return the correct amount of products
    public void testGetPaginatedProducts_ValidPage_ReturnsPaginatedProducts() {
        // Arrange: Mock the product repository and category repository with sample data
        Product product1 = new Product(1L, 1L, "Product 1", 10.0f, null, 5, null, null);
        Product product2 = new Product(2L, 2L, "Product 2", 20.0f, null, 10, null, null);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        when(categoryRepository.findById(1L)).thenReturn(new Category(1L, "Category 1"));
        when(categoryRepository.findById(2L)).thenReturn(new Category(2L, "Category 2"));

        // Act
        List<ProductWithCategoryDTO> result = productService.getPaginatedProducts(1);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Category 1", result.get(0).getCategory());
        assertEquals("Category 2", result.get(1).getCategory());
    }

    @Test
    // test create product
    public void testCreate_ValidProduct_ReturnsCreatedProduct() {
        // Arrange
        Product product = new Product(null, 1L, "New Product", 15.0f, null, 10, null, null);
        when(categoryRepository.findById(1L)).thenReturn(new Category(1L, "Category 1"));
        when(productRepository.create(any(Product.class))).thenAnswer(invocation -> {
            Product createdProduct = invocation.getArgument(0);
            createdProduct.setId(1L);
            return createdProduct;
        });

        // Act
        ProductWithCategoryDTO result = productService.create(product);

        // Assert
        assertNotNull(result.getId());
        assertEquals("New Product", result.getName());
        assertEquals("Category 1", result.getCategory());
        verify(productRepository, times(1)).create(any(Product.class));
    }

    @Test
    // Test update product stock to 20
    public void testInStock_ValidStock_UpdatesProduct() {
        // Arrange
        Product product = new Product(1L, 1L, "Product 1", 10.0f, null, 5, null, null);
        UpdateStockDTO updateStockDTO = new UpdateStockDTO(20);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        Product result = productService.inStock(1L, updateStockDTO);

        // Assert
        assertEquals(20, result.getStock());
        verify(productRepository, times(1)).update(product);
    }
}
