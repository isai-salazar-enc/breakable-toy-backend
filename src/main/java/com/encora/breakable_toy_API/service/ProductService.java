package com.encora.breakable_toy_API.service;

import com.encora.breakable_toy_API.models.Category;
import com.encora.breakable_toy_API.models.Product;
import com.encora.breakable_toy_API.models.ProductWithCategoryDTO;
import com.encora.breakable_toy_API.models.UpdateStockDTO;
import com.encora.breakable_toy_API.repository.InMemoryCategoryRepository;
import com.encora.breakable_toy_API.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final InMemoryCategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, InMemoryCategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // Get all products
    public List<ProductWithCategoryDTO> getProducts(Integer page){
        if(page<1){
            throw new IllegalAccessError("Page value not allowed");
        }
        int size = 10;
        List<Product> productList;
        productList = productRepository.findAll();

        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, productList.size());
        if (fromIndex >= productList.size()) {
            return Collections.emptyList();
        }

        return productList.subList(fromIndex, toIndex).stream()
                .map(product -> new ProductWithCategoryDTO(
                        product.getId(),
                        product.getIdCategory(),
                        resolveCategoryName(product.getIdCategory()),
                        product.getName(),
                        product.getUnitPrice(),
                        product.getExpirationDate(),
                        product.getStock(),
                        product.getCreatedAt(),
                        product.getUpdatedAt()
                ))
                .collect(Collectors.toList());
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

        if (product.getIdCategory() == null) {
            throw new IllegalArgumentException("The category cannot be null");
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


    /**
     * * Updates the stock of a product identified by the given ID.
     * 
     * @param id Product ID.
     * @param updateStockDTO The data transfer object containing the new stock value.
     * @return {@link ResponseEntity}
     */
    public ResponseEntity<?> inStock(Long id, UpdateStockDTO updateStockDTO){
        if(updateStockDTO.getStock() == null || updateStockDTO.getStock() < 0){
            return ResponseEntity.badRequest().body("Stock cannot be a non-negative integer.");
        }

        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()){
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }

        // Update stock
        Product product = productOptional.get();
        product.setStock(updateStockDTO.getStock());

        // Save updated product in repository
        productRepository.update(product);

        return ResponseEntity.ok(product);
    }

    public ResponseEntity<?> updateProduct(Product product, Long id){
        if(product.getId() == null){
            return new ResponseEntity<>("ID cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if(!product.getId().equals(id)){
            return new ResponseEntity<>("The ID on the request does not match the ID in the body.", HttpStatus.BAD_REQUEST);
        }
        Optional<Product> productOptional = productRepository.findById(product.getId());
        if(productOptional.isEmpty()){
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }

        product.setCreatedAt(productOptional.get().getCreatedAt()); // Incoming product does not change the created at date
        product.setUpdatedAt(LocalDateTime.now());
        return new ResponseEntity<>(productRepository.update(product), HttpStatus.OK);
    }

    // Método para resolver el nombre de una categoría dado su ID
    private String resolveCategoryName(Long categoryId) {
        Category category = categoryRepository.findById(categoryId);
        return (category != null) ? category.getName() : "Unknown";
    }

}
