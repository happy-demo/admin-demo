package com.happyblock.admindemo.infrastructure.persistence.jpa.mapper;

import com.happyblock.admindemo.domain.model.Transaction;
import com.happyblock.admindemo.domain.vo.TransactionId;
import com.happyblock.admindemo.domain.vo.TxHash;
import com.happyblock.admindemo.infrastructure.persistence.jpa.entity.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction toDomain(TransactionEntity entity) {
        return Transaction.builder()
                .id(new TransactionId(entity.getId()))
                .user(entity.getUser())
                .txHash(new TxHash(entity.getTxHash()))
                .status(Transaction.Status(entity.getStatus()))
                .lots(entity.getLots())
                .sequenceId(entity.getSequenceId())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public TransactionEntity toEntity(Transaction transaction) {
        return new TransactionEntity(
                null
                , transaction.getUser()
                , transaction.
        );
    }

    /**
     * 기존 Entity에 Domain 상태를 반영
     */
    public void apply(Transaction transaction, TransactionEntity entity) {
        entity.setId(transaction.getId().value());
        entity.setUser(transaction.getUser());
        entity.setTxHash(transaction.getTxHash().value());
        entity.setStatus(transaction.getStatus());
        entity.setLots(transaction.getLots());
    }
}
