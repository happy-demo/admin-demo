package com.happyblock.admindemo.service;

import com.happyblock.admindemo.entity.OrderEntity;
import com.happyblock.admindemo.entity.ProductEntity;
import com.happyblock.admindemo.entity.UserEntity;
import com.happyblock.admindemo.repository.OrderRepository;
import com.happyblock.admindemo.repository.ProductRepository;
import com.happyblock.admindemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public Optional<OrderEntity> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    
    public List<OrderEntity> getOrdersByUserId(Long userId) {
        return orderRepository.findByUser_Id(userId);
    }
    
    public List<OrderEntity> getOrdersByProductId(Long productId) {
        return orderRepository.findByProduct_Id(productId);
    }
    
    public OrderEntity createOrder(Long userId, Long productId, Integer quantity) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));
        
        OrderEntity orderEntity = new OrderEntity(user, product, quantity);
        return orderRepository.save(orderEntity);
    }
    
    public OrderEntity updateOrder(Long id, Long userId, Long productId, Integer quantity) {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
        
        if (userId != null) {
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
            orderEntity.setUser(user);
        }
        
        if (productId != null) {
            ProductEntity product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));
            orderEntity.setProduct(product);
        }
        
        if (quantity != null) {
            orderEntity.setQuantity(quantity);
        }
        
        return orderRepository.save(orderEntity);
    }
    
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }
}
