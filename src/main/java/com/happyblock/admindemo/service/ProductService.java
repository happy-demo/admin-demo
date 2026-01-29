package com.happyblock.admindemo.service;

import com.happyblock.admindemo.entity.ProductEntity;
import com.happyblock.admindemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Optional<ProductEntity> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    public ProductEntity createProduct(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }
    
    public ProductEntity updateProduct(Long id, ProductEntity productEntityDetails) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        
        if (productEntityDetails.getName() != null) {
            productEntity.setName(productEntityDetails.getName());
        }
        if (productEntityDetails.getPrice() != null) {
            productEntity.setPrice(productEntityDetails.getPrice());
        }
        if (productEntityDetails.getDescription() != null) {
            productEntity.setDescription(productEntityDetails.getDescription());
        }
        
        return productRepository.save(productEntity);
    }
    
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
