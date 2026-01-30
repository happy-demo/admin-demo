package com.happyblock.admindemo.application.query;

import com.happyblock.admindemo.domain.model.Transaction;
import com.happyblock.admindemo.domain.repository.TransactionRepository;
import com.happyblock.admindemo.domain.vo.TransactionId;
import com.happyblock.admindemo.domain.vo.TxHash;
import com.happyblock.admindemo.domain.vo.UserId;
import com.happyblock.admindemo.infrastructure.persistence.jpa.entity.TransactionEntity;
import com.happyblock.admindemo.infrastructure.persistence.jpa.mapper.TransactionMapper;
import com.happyblock.admindemo.presentation.controller.dto.TransactionCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * application 계층은 entity를 몰라야한다.
 * application은 "도메인을 어떻게 쓰는지"만 알아야 한다
 * jpaRepository를 직접 사용하지 말아야한다.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TransactionQueryService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper mapper;

    // 도메인 모델 조회 메서드들
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Transaction findById(TransactionId id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id.value().toString()));
    }

    public List<Transaction> findByUserId(UserId userId) {
        return transactionRepository.findByUserId(userId);
    }

    public Transaction findByTxHash(TxHash txHash) {
        return transactionRepository.findByTxHash(txHash)
                .orElseThrow(() -> new IllegalArgumentException(txHash.value()));
    }

    // Entity 조회 메서드들 (Controller에서 사용)
    public List<TransactionEntity> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<TransactionEntity> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public List<TransactionEntity> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    public Optional<TransactionEntity> getTransactionByTxHash(String txHash) {
        return transactionRepository.findByTxHash(new TxHash(txHash));
    }

    public Optional<TransactionEntity> getTransactionBySequenceId(String sequenceId) {
        return transactionRepository.findAll()
                .stream()
                .filter(tx -> sequenceId.equals(tx.getSequenceId()))
                .findFirst();
    }

    public List<TransactionEntity> getTransactionsByStatus(TransactionEntity.Status status) {
        return transactionRepository.findAll()
                .stream()
                .filter(tx -> status.equals(tx.getStatus()))
                .collect(Collectors.toList());
    }

    // CUD 메서드들 (Entity 기반)
    @Transactional
    public TransactionEntity createTransaction(TransactionCreateDTO requestDTO) {
        return transactionRepository.save(requestDTO);
    }

    @Transactional
    public TransactionEntity updateTransaction(Long id, TransactionEntity transactionDetails) {
        TransactionEntity transactionEntity = transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found with id: " + id));

        if (transactionDetails.getCreatedBy() != null) {
            transactionEntity.setCreatedBy(transactionDetails.getCreatedBy());
        }
        if (transactionDetails.getUpdatedBy() != null) {
            transactionEntity.setUpdatedBy(transactionDetails.getUpdatedBy());
        }
        if (transactionDetails.getServiceType() != null) {
            transactionEntity.setServiceType(transactionDetails.getServiceType());
        }
        if (transactionDetails.getSequenceId() != null) {
            transactionEntity.setSequenceId(transactionDetails.getSequenceId());
        }
        if (transactionDetails.getTxHash() != null) {
            transactionEntity.setTxHash(transactionDetails.getTxHash());
        }
        if (transactionDetails.getStatus() != null) {
            transactionEntity.setStatus(transactionDetails.getStatus());
        }
        if (transactionDetails.getSourceAddress() != null) {
            transactionEntity.setSourceAddress(transactionDetails.getSourceAddress());
        }
        if (transactionDetails.getDestinationAddress() != null) {
            transactionEntity.setDestinationAddress(transactionDetails.getDestinationAddress());
        }
        if (transactionDetails.getTransferType() != null) {
            transactionEntity.setTransferType(transactionDetails.getTransferType());
        }

        return transactionRepository.save(transactionEntity);
    }

    @Transactional
    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new IllegalArgumentException("Transaction not found with id: " + id);
        }
        transactionRepository.deleteById(id);
    }
}
