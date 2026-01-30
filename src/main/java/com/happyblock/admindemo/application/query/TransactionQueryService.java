package com.happyblock.admindemo.application.query;

import com.happyblock.admindemo.domain.model.Transaction;
import com.happyblock.admindemo.domain.repository.TransactionRepository;
import com.happyblock.admindemo.domain.vo.TransactionId;
import com.happyblock.admindemo.domain.vo.TxHash;
import com.happyblock.admindemo.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * application 계층은 entity를 몰라야한다.
 * application은 “도메인을 어떻게 쓰는지”만 알아야 한다
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TransactionQueryService {

    private final TransactionRepository transactionRepository;

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
        return transactionRepository.findByTxHash(txHash.value())
                .orElseThrow(() -> new IllegalArgumentException(txHash.value()));
    }
}
