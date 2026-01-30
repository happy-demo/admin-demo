package com.happyblock.admindemo.infrastructure.persistence.jpa.mapper;

import com.happyblock.admindemo.domain.model.CustodyLedger;
import com.happyblock.admindemo.domain.model.Transaction;
import com.happyblock.admindemo.domain.repository.TransactionRepository;
import com.happyblock.admindemo.domain.vo.CustodyLedgerId;
import com.happyblock.admindemo.domain.vo.TransactionId;
import com.happyblock.admindemo.domain.vo.UserId;
import com.happyblock.admindemo.domain.vo.WalletId;
import com.happyblock.admindemo.infrastructure.persistence.jpa.entity.CustodyLedgerEntity;
import com.happyblock.admindemo.infrastructure.persistence.jpa.entity.TransactionEntity;
import com.happyblock.admindemo.infrastructure.persistence.jpa.entity.UserEntity;
import com.happyblock.admindemo.infrastructure.persistence.jpa.entity.WalletEntity;
import com.happyblock.admindemo.infrastructure.persistence.jpa.repository.TransactionJpaRepository;
import com.happyblock.admindemo.infrastructure.persistence.jpa.repository.UserJpaRepository;
import com.happyblock.admindemo.infrastructure.persistence.jpa.repository.WalletJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustodyLedgerMapper {

    private final UserJpaRepository userJpaRepository;
    private final TransactionJpaRepository transactionJpaRepository;
    private final WalletJpaRepository walletJpaRepository;
    private final TransactionRepository transactionRepository;

    public CustodyLedger toDomain(CustodyLedgerEntity entity) {
        // Transaction 도메인을 가져와서 amount 추출
        TransactionId transactionId = new TransactionId(entity.getTransaction().getId());
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found with id: " + transactionId.value()));
        
        return CustodyLedger.builder()
                .id(new CustodyLedgerId(entity.getId()))
                .userId(new UserId(entity.getUser().getId()))
                .transactionId(new TransactionId(entity.getTransaction().getId()))
                .walletId(new WalletId(entity.getWallet().getId()))
                .amount(transaction.getAmount())
                .build();
    }

    public CustodyLedgerEntity toEntity(CustodyLedger ledger) {
        CustodyLedgerEntity entity = new CustodyLedgerEntity();
        apply(ledger, entity);
        return entity;
    }

    /**
     * 기존 Entity에 Domain 상태를 반영
     */
    public void apply(CustodyLedger ledger, CustodyLedgerEntity entity) {
        if (ledger.getId() != null && ledger.getId().value() != null) {
            entity.setId(ledger.getId().value());
        }
        
        // User 엔티티 로드
        UserEntity user = userJpaRepository.findById(ledger.getUserId().value())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + ledger.getUserId().value()));
        entity.setUser(user);
        
        // Transaction 엔티티 로드
        TransactionEntity transaction = transactionJpaRepository.findById(ledger.getTransactionId().value())
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found with id: " + ledger.getTransactionId().value()));
        entity.setTransaction(transaction);
        
        // Wallet 엔티티 로드
        WalletEntity wallet = walletJpaRepository.findById(ledger.getWalletId().value())
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found with id: " + ledger.getWalletId().value()));
        entity.setWallet(wallet);
        
        // address는 transaction의 destination_address를 사용
        if (transaction.getDestinationAddress() != null) {
            entity.setAddress(transaction.getDestinationAddress());
        }
        
        // createdBy와 updatedBy는 기본값 설정 (필요시 ledger에 필드 추가)
        if (entity.getCreatedBy() == null) {
            entity.setCreatedBy("system");
        }
        if (entity.getUpdatedBy() == null) {
            entity.setUpdatedBy("system");
        }
    }
}
