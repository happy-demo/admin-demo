package com.happyblock.admindemo.controller;

import com.happyblock.admindemo.entity.OrderEntity;
import com.happyblock.admindemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    // 모든 주문 조회
    @GetMapping
    public ResponseEntity<List<OrderEntity>> getAllOrders() {
        List<OrderEntity> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    
    // ID로 주문 조회
    @GetMapping("/{id}")
    public ResponseEntity<OrderEntity> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // 사용자별 주문 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderEntity>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderEntity> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
    
    // 제품별 주문 조회
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<OrderEntity>> getOrdersByProductId(@PathVariable Long productId) {
        List<OrderEntity> orders = orderService.getOrdersByProductId(productId);
        return ResponseEntity.ok(orders);
    }
    
    // 주문 생성
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> request) {
        try {
            Long userId = Long.valueOf(request.get("userId").toString());
            Long productId = Long.valueOf(request.get("productId").toString());
            Integer quantity = request.get("quantity") != null ? 
                    Integer.valueOf(request.get("quantity").toString()) : 1;
            
            OrderEntity savedOrder = orderService.createOrder(userId, productId, quantity);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request: " + e.getMessage());
        }
    }
    
    // 주문 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            Long userId = request.get("userId") != null ? 
                    Long.valueOf(request.get("userId").toString()) : null;
            Long productId = request.get("productId") != null ? 
                    Long.valueOf(request.get("productId").toString()) : null;
            Integer quantity = request.get("quantity") != null ? 
                    Integer.valueOf(request.get("quantity").toString()) : null;
            
            OrderEntity updatedOrder = orderService.updateOrder(id, userId, productId, quantity);
            return ResponseEntity.ok(updatedOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request: " + e.getMessage());
        }
    }
    
    // 주문 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok().body("Order deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
