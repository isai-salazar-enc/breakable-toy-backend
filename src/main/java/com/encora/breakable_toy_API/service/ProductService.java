package com.encora.breakable_toy_API.service;

import com.encora.breakable_toy_API.models.Product;
import com.encora.breakable_toy_API.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(int page){
        return productRepository.findAll(page);
    }

}
