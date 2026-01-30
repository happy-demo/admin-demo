package com.happyblock.admindemo.infrastructure.persistence.jpa.repository;

import com.happyblock.admindemo.domain.model.Transaction;
import com.happyblock.admindemo.domain.repository.TransactionRepository;
import com.happyblock.admindemo.domain.vo.TransactionId;
import com.happyblock.admindemo.domain.vo.TxHash;
import com.happyblock.admindemo.domain.vo.UserId;
import com.happyblock.admindemo.infrastructure.persistence.jpa.entity.TransactionEntity;
import com.happyblock.admindemo.infrastructure.persistence.jpa.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionJpaRepository jpaRepository;
    private final TransactionMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<Transaction> findById(TransactionId id) {
        return jpaRepository.findById(id.value())
                .map(mapper::toDomain);
    }

    @Override
    public List<Transaction> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findByUserId(UserId userId) {
        return jpaRepository.findByUserId(userId.value())
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Transaction> findByTxHash(TxHash txHash) {
        return jpaRepository.findByTxHash(txHash)
                .map(mapper::toDomain);
    }

    @Override
    public void save(Transaction transaction) {
        TransactionEntity entity =
                jpaRepository.findById(transaction.getId().value())
                        .orElseGet(() -> mapper.toEntity(transaction));

        mapper.apply(transaction, entity);
        jpaRepository.save(entity);
    }
}
