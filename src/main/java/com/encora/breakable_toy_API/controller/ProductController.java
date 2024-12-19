package com.encora.breakable_toy_API.controller;

import com.encora.breakable_toy_API.models.Product;
import com.encora.breakable_toy_API.models.UpdateStockDTO;
import com.encora.breakable_toy_API.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // TODO: change to /products (1 to 10) and manage pagination under the water
    @GetMapping("/products/{page}")
    public List<Product> getPaginatedProducts(@PathVariable int page){
        return productService.getProducts(page);
    }

    // Todo: change to PostMapping
    @PostMapping("/products/{id}/outofstock")
    public Product setProductOutOfStock(@PathVariable Long id){
        return productService.outOfStock(id);
    }

    @PostMapping("/products")
    public ResponseEntity<?> create(@RequestBody Product product) {
        try {
            Product createdProduct = productService.create(product);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("products/{id}/instock")
    public ResponseEntity<?> setProductInStock(@PathVariable Long id, @RequestBody UpdateStockDTO updateStockDTO){
        return productService.inStock(id, updateStockDTO);   
    }
}
