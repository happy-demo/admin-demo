package com.happyblock.admindemo.infrastructure.persistence.jpa.repository;

import com.happyblock.admindemo.infrastructure.persistence.jpa.entity.CustodyLedgerEntity;
import com.happyblock.admindemo.infrastructure.persistence.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustodyLedgerJpaRepository extends JpaRepository<CustodyLedgerEntity, Long> {
    
    List<CustodyLedgerEntity> findByUser(UserEntity user);
    
    List<CustodyLedgerEntity> findByTransactionId(Long transactionId);
    
    List<CustodyLedgerEntity> findByWalletId(Long walletId);

    boolean existsByTransactionId(Long transactionId);
}
