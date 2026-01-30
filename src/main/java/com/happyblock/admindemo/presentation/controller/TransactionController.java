package com.happyblock.admindemo.presentation.controller;

import com.happyblock.admindemo.application.command.ApproveTransactionUseCase;
import com.happyblock.admindemo.application.query.TransactionQueryService;
import com.happyblock.admindemo.domain.model.Transaction;
import com.happyblock.admindemo.infrastructure.persistence.jpa.entity.TransactionEntity;
import com.happyblock.admindemo.presentation.controller.dto.TransactionCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionQueryService transactionQueryService;
    private final ApproveTransactionUseCase approveTransactionUseCase;

    // 모든 transaction 조회
    @GetMapping
    public ResponseEntity<List<TransactionEntity>> getAllTransactions() {
        List<TransactionEntity> transactions = transactionQueryService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
    
    // id로 transaction 조회
    @GetMapping("/{id}")
    public ResponseEntity<TransactionEntity> getTransactionById(@PathVariable Long id) {
        return transactionQueryService.getTransactionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // user_id로 transaction 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TransactionEntity>> getTransactionsByUserId(@PathVariable Long userId) {
        List<TransactionEntity> transactions = transactionQueryService.getTransactionsByUserId(userId);
        return ResponseEntity.ok(transactions);
    }
    
    // txHash로 transaction 조회
    @GetMapping("/txhash/{txHash}")
    public ResponseEntity<TransactionEntity> getTransactionByTxHash(@PathVariable String txHash) {
        return transactionQueryService.getTransactionByTxHash(txHash)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // sequenceId로 transaction 조회
    @GetMapping("/sequence/{sequenceId}")
    public ResponseEntity<TransactionEntity> getTransactionBySequenceId(@PathVariable String sequenceId) {
        return transactionQueryService.getTransactionBySequenceId(sequenceId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // status로 transaction 조회
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TransactionEntity>> getTransactionsByStatus(@PathVariable String status) {
        try {
            Transaction.Status transactionStatus = Transaction.Status.valueOf(status);
            List<TransactionEntity> transactions = transactionQueryService.getTransactionsByStatus(transactionStatus);
            return ResponseEntity.ok(transactions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // transaction 생성
    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody TransactionCreateDTO createDTO) {
        try {
            TransactionEntity savedTransaction = transactionQueryService.createTransaction(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request: " + e.getMessage());
        }
    }
    
    // transaction 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable Long id, @RequestBody TransactionEntity transactionEntity) {
        try {
            TransactionEntity updatedTransaction = transactionQueryService.updateTransaction(id, transactionEntity);
            return ResponseEntity.ok(updatedTransaction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request: " + e.getMessage());
        }
    }
    
    // transaction 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        try {
            transactionQueryService.deleteTransaction(id);
            return ResponseEntity.ok().body("Transaction deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    // transaction 승인
    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approveTransaction(@PathVariable Long id, @RequestBody Map<String, Long> request) {
        try {
            Long approverId = request.get("approverId");
            if (approverId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("approverId is required");
            }
            
            approveTransactionUseCase.approve(
                    com.happyblock.admindemo.domain.vo.TransactionId.of(id),
                    com.happyblock.admindemo.domain.vo.UserId.of(approverId)
            );
            
            return ResponseEntity.ok().body("Transaction approved successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request: " + e.getMessage());
        }
    }
}
