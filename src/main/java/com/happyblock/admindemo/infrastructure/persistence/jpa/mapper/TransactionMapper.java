package com.happyblock.admindemo.infrastructure.persistence.jpa.mapper;

import com.happyblock.admindemo.domain.model.Transaction;
import com.happyblock.admindemo.domain.vo.TransactionId;
import com.happyblock.admindemo.domain.vo.TxHash;
import com.happyblock.admindemo.domain.vo.UserId;
import com.happyblock.admindemo.infrastructure.persistence.jpa.entity.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction toDomain(TransactionEntity entity) {
        return Transaction.builder()
                .id(new TransactionId(entity.getId()))
                .userId(new UserId(entity.getUser()))
                .txHash(new TxHash(entity.getTxHash()))
                .status(entity.getStatus())
                .amount(entity.getAmount())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public TransactionEntity toEntity(Transaction transaction) {
        TransactionEntity entity = new TransactionEntity();
        apply(transaction, entity);
        return entity;
    }

    /**
     * 기존 Entity에 Domain 상태를 반영
     */
    public void apply(Transaction transaction, TransactionEntity entity) {
        entity.setId(transaction.getId().value());
        entity.setUserId(transaction.getUserId().value());
        entity.setTxHash(transaction.getTxHash().value());
        entity.setStatus(transaction.getStatus());
        entity.setAmount(transaction.getAmount());
    }
}
