package com.happyblock.admindemo.presentation.controller;

import com.happyblock.admindemo.infrastructure.persistence.jpa.entity.CustodyLedgerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/custody-ledgers")
@RequiredArgsConstructor
public class CustodyLedgerController {

    private final CustodyLedgerService custodyLedgerService;
    
    // 모든 custody ledger 조회
    @GetMapping
    public ResponseEntity<List<CustodyLedgerEntity>> getAllCustodyLedgers() {
        List<CustodyLedgerEntity> custodyLedgers = custodyLedgerService.getAllCustodyLedgers();
        return ResponseEntity.ok(custodyLedgers);
    }
    
    // id로 custody ledger 조회
    @GetMapping("/{id}")
    public ResponseEntity<CustodyLedgerEntity> getCustodyLedgerById(@PathVariable Long id) {
        return custodyLedgerService.getCustodyLedgerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // user_id로 custody ledger 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CustodyLedgerEntity>> getCustodyLedgersByUserId(@PathVariable Long userId) {
        List<CustodyLedgerEntity> custodyLedgers = custodyLedgerService.getCustodyLedgersByUserId(userId);
        return ResponseEntity.ok(custodyLedgers);
    }
    
    // transaction_id로 custody ledger 조회
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<List<CustodyLedgerEntity>> getCustodyLedgersByTransactionId(@PathVariable Long transactionId) {
        List<CustodyLedgerEntity> custodyLedgers = custodyLedgerService.getCustodyLedgersByTransactionId(transactionId);
        return ResponseEntity.ok(custodyLedgers);
    }
    
    // wallet_id로 custody ledger 조회
    @GetMapping("/wallet/{walletId}")
    public ResponseEntity<List<CustodyLedgerEntity>> getCustodyLedgersByWalletId(@PathVariable Long walletId) {
        List<CustodyLedgerEntity> custodyLedgers = custodyLedgerService.getCustodyLedgersByWalletId(walletId);
        return ResponseEntity.ok(custodyLedgers);
    }
    
    // custody ledger 생성
    @PostMapping
    public ResponseEntity<?> createCustodyLedger(@RequestBody CustodyLedgerEntity custodyLedgerEntity) {
        try {
            CustodyLedgerEntity savedCustodyLedger = custodyLedgerService.createCustodyLedger(custodyLedgerEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCustodyLedger);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request: " + e.getMessage());
        }
    }
    
    // custody ledger 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustodyLedger(@PathVariable Long id, @RequestBody CustodyLedgerEntity custodyLedgerEntity) {
        try {
            CustodyLedgerEntity updatedCustodyLedger = custodyLedgerService.updateCustodyLedger(id, custodyLedgerEntity);
            return ResponseEntity.ok(updatedCustodyLedger);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request: " + e.getMessage());
        }
    }
    
    // custody ledger 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustodyLedger(@PathVariable Long id) {
        try {
            custodyLedgerService.deleteCustodyLedger(id);
            return ResponseEntity.ok().body("CustodyLedger deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
