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
    public ResponseEntity<List<ProductWithCategoryDTO>> getPaginatedProducts(@PathVariable(required = true) Integer page){
        if(page == null){
            page = 1;
        }
        return ResponseEntity.ok(productService.getPaginatedProducts(page));
    }

    @GetMapping("/metrics")
    public ResponseEntity<Map<String, Object>> getProductMetrics(){
        Map<String, Object> metrics = productService.getInventoryMetrics();
        return ResponseEntity.ok(metrics);
    }

    @PostMapping("/products/{id}/outofstock")
    public ResponseEntity<Product> setProductOutOfStock(@PathVariable Long id){
        return ResponseEntity.ok(productService.outOfStock(id));
    }

    @PostMapping("/products")
    public ResponseEntity<ProductWithCategoryDTO> create(@RequestBody Product product) {
        ProductWithCategoryDTO createdProduct = productService.create(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}/instock")
    public ResponseEntity<Product> setProductInStock(@PathVariable Long id, @RequestBody UpdateStockDTO updateStockDTO){
        Product updatedProduct = productService.inStock(id, updateStockDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable(value = "id") Long id, @RequestBody Product product){
        ProductWithCategoryDTO updatedProduct =  productService.updateProduct(product, id);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        String response = productService.deleteProduct(id);
        return ResponseEntity.ok(response);
    }
}
