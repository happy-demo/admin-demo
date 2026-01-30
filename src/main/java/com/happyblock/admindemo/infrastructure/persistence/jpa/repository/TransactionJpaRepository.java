package com.happyblock.admindemo.infrastructure.persistence.jpa.repository;

import com.happyblock.admindemo.domain.model.Transaction;
import com.happyblock.admindemo.domain.vo.TransactionId;
import com.happyblock.admindemo.domain.vo.UserId;
import com.happyblock.admindemo.infrastructure.persistence.jpa.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, Long> {

    Optional<Transaction> findById(TransactionId id);

    List<Transaction> findByUserId(UserId userId);

    Optional<Transaction> findByTxHash(TxHash txHash);

    void save(Transaction transaction);
}
