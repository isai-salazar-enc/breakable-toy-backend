package com.encora.breakable_toy_API.controller;

import com.encora.breakable_toy_API.models.Product;
import com.encora.breakable_toy_API.models.ProductWithCategoryDTO;
import com.encora.breakable_toy_API.models.UpdateStockDTO;
import com.encora.breakable_toy_API.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductWithCategoryDTO>> getProducts(){
        List<ProductWithCategoryDTO> products = productService.getProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products/{page}")
    public List<ProductWithCategoryDTO> getPaginatedProducts(@PathVariable(required = true) Integer page){
        if(page == null){
            page = 1;
        }
        return productService.getPaginatedProducts(page);
    }

    // Todo: change to PostMapping
    @PostMapping("/products/{id}/outofstock")
    public Product setProductOutOfStock(@PathVariable Long id){
        return productService.outOfStock(id);
    }

    @PostMapping("/products")
    public ResponseEntity<?> create(@RequestBody Product product) {
        try {
            ProductWithCategoryDTO createdProduct = productService.create(product);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("products/{id}/instock")
    public ResponseEntity<?> setProductInStock(@PathVariable Long id, @RequestBody UpdateStockDTO updateStockDTO){
        return productService.inStock(id, updateStockDTO);   
    }

    @PutMapping("products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable(value = "id") Long id, @RequestBody Product product){
        return productService.updateProduct(product, id);
    }
}
