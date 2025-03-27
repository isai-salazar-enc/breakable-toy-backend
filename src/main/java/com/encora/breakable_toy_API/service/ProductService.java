package com.encora.breakable_toy_API.service;

import com.encora.breakable_toy_API.models.Category;
import com.encora.breakable_toy_API.models.Product;
import com.encora.breakable_toy_API.models.ProductWithCategoryDTO;
import com.encora.breakable_toy_API.models.UpdateStockDTO;
import com.encora.breakable_toy_API.repository.InMemoryCategoryRepository;
import com.encora.breakable_toy_API.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    public List<ProductWithCategoryDTO> getProducts(){
        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map( product -> new ProductWithCategoryDTO(product,resolveCategoryName(product.getIdCategory())) )
                .collect(Collectors.toList());
    }

    // Get all products with pagination
    public List<ProductWithCategoryDTO> getPaginatedProducts(Integer page){
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
                .map( product -> new ProductWithCategoryDTO(product,resolveCategoryName(product.getIdCategory())) )
                .collect(Collectors.toList());
    }

    // Set stock to 0
    public Product outOfStock(Long id){
        return productRepository.outOfStock(id);
    }

    // Create a new product
    public ProductWithCategoryDTO create(Product product){
        // Validate name
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("The name of the product cannot be null or empty");
        }
        if (product.getName().length() > 120) {
            throw new IllegalArgumentException("The name of the product cannot exceed 120 characters");
        }

        // Validate price
        if (product.getUnitPrice() == null || product.getUnitPrice() <= 0) {
            throw new IllegalArgumentException("The unit price must be greater than 0");
        }

        if (product.getIdCategory() == null) {
            throw new IllegalArgumentException("The category cannot be null");
        }

        // Validate stock
        if (product.getStock() == null || product.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        Product newProduct =  productRepository.create(product);
        return new ProductWithCategoryDTO(newProduct,resolveCategoryName(product.getIdCategory()));
    }


    /**
     * * Updates the stock of a product identified by the given ID.
     * 
     * @param id Product ID.
     * @param updateStockDTO The data transfer object containing the new stock value.
     * @return {@link Product}
     */
    public Product inStock(Long id, UpdateStockDTO updateStockDTO){
        if (updateStockDTO.getStock() == null || updateStockDTO.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be a negative integer.");
        }
    
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new IllegalArgumentException("Product not found.");
        }
    
        // Update stock
        Product product = productOptional.get();
        product.setStock(updateStockDTO.getStock());
    
        // Save updated product in repository
        productRepository.update(product);
    
        return product;
    }

    public ProductWithCategoryDTO updateProduct(Product product, Long id){
        if(product.getId() == null){
            throw new IllegalArgumentException("ID cannot be null");
        }
        if(!product.getId().equals(id)){
            throw new IllegalArgumentException("The ID on the request does not match the ID in the body.");
        }
        Optional<Product> productOptional = productRepository.findById(product.getId());
        if(productOptional.isEmpty()){
            throw new IllegalArgumentException("Product not found.");
        }

        resolveCategoryName(product.getIdCategory()); // Check if it is a valid ID
        product.setCreatedAt(productOptional.get().getCreatedAt()); // Incoming product does not change the created at date
        product.setUpdatedAt(LocalDateTime.now());
        Product newProduct = productRepository.update(product);
        return new ProductWithCategoryDTO(newProduct,resolveCategoryName(product.getIdCategory()));
    }

    // Resolve name of category based on ID
    private String resolveCategoryName(Long categoryId) {
        Category category = categoryRepository.findById(categoryId);
        if (category != null){
            return category.getName();
        }
        throw new IllegalArgumentException("Category not found.");
    }

    public Map<String, Object> getInventoryMetrics() {
        List<Product> productList = productRepository.findAll();

        // Overall metrics
        long totalProducts = productList.stream().filter(product -> product.getStock() > 0).mapToLong(Product::getStock).sum();
        double totalValue = productList.stream().filter(product -> product.getStock() > 0).mapToDouble(p -> p.getStock() * p.getUnitPrice()).sum();
        double averagePrice = productList.stream().filter(product -> product.getStock() > 0).mapToDouble(Product::getUnitPrice).average().orElse(0);

        Map<String, Map<String, ?>> categoryMetrics = productList.stream()
                .filter(product -> product.getStock() > 0)
                .collect(Collectors.groupingBy(
                        Product::getIdCategory,
                        Collectors.collectingAndThen(
                                Collectors.toList(), // Agrupamos en listas de productos
                                (List<Product> products) -> { // Especificamos explícitamente el tipo de lista
                                    long categoryTotalProducts = products.stream().mapToLong(Product::getStock).sum();
                                    double categoryTotalValue = products.stream().mapToDouble(p -> p.getStock() * p.getUnitPrice()).sum();
                                    double categoryAveragePrice = products.stream().mapToDouble(Product::getUnitPrice).average().orElse(0);
                                    return Map.of(
                                            "totalProducts", categoryTotalProducts,
                                            "totalValue", categoryTotalValue,
                                            "averagePrice", categoryAveragePrice
                                    );
                                }
                        )
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> this.resolveCategoryName(entry.getKey()),
                        Map.Entry::getValue
                ));


        return Map.of(
                "overall", Map.of(
                        "totalProducts", totalProducts,
                        "totalValue", totalValue,
                        "averagePrice", averagePrice
                ),
                "byCategory", categoryMetrics
        );
    }

    public String deleteProduct(Long id){
        if(productRepository.delete(id)){
            return "Producto eliminado correctamente.";
        }
        else{
            throw new IllegalArgumentException("Product not found.");
        }
    }
}
