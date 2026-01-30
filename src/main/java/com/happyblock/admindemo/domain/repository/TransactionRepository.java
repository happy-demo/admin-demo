package com.happyblock.admindemo.domain.repository;

import com.happyblock.admindemo.domain.model.Transaction;
import com.happyblock.admindemo.domain.vo.TransactionId;
import com.happyblock.admindemo.domain.vo.UserId;
import com.happyblock.admindemo.infrastructure.persistence.jpa.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository {

    Optional<Transaction> findById(TransactionId id);

    public List<Transaction> findAll();

    List<Transaction> findByUserId(UserId userId);

    public Optional<Transaction> findByTxHash(String txHash);

    void save(Transaction transaction);
}
