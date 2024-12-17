package com.encora.breakable_toy_API.controller;

import com.encora.breakable_toy_API.models.Product;
import com.encora.breakable_toy_API.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{page}")
    public List<Product> getPaginatedProducts(@PathVariable int page){
        return productService.getProducts(page);
    }
}
