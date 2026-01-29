package com.happyblock.admindemo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class OrderEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;
    
    @Column(nullable = false)
    private Integer quantity = 1;
    
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    
    @PrePersist
    protected void onCreate() {
        orderDate = LocalDateTime.now();
    }
    
    // 기본 생성자
    public OrderEntity() {
    }
    
    // 생성자
    public OrderEntity(UserEntity user, ProductEntity product, Integer quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public UserEntity getUser() {
        return user;
    }
    
    public void setUser(UserEntity user) {
        this.user = user;
    }
    
    public ProductEntity getProduct() {
        return product;
    }
    
    public void setProduct(ProductEntity product) {
        this.product = product;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
