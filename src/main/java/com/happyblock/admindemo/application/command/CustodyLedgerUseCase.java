package com.happyblock.admindemo.application.command;

import com.happyblock.admindemo.domain.model.CustodyLedger;
import com.happyblock.admindemo.domain.model.Transaction;
import com.happyblock.admindemo.domain.model.Wallet;
import com.happyblock.admindemo.domain.repository.CustodyLedgerRepository;
import com.happyblock.admindemo.domain.repository.TransactionRepository;
import com.happyblock.admindemo.domain.repository.WalletRepository;
import com.happyblock.admindemo.domain.vo.TransactionId;
import com.happyblock.admindemo.domain.vo.WalletId;
import com.happyblock.admindemo.infrastructure.persistence.jpa.entity.CustodyLedgerEntity;
import com.happyblock.admindemo.infrastructure.persistence.jpa.mapper.CustodyLedgerMapper;
import com.happyblock.admindemo.infrastructure.persistence.jpa.repository.CustodyLedgerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * application 계층은 entity를 몰라야한다.
 * application은 "도메인을 어떻게 쓰는지"만 알아야 한다
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CustodyLedgerUseCase {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final CustodyLedgerRepository ledgerRepository;
    private final CustodyLedgerJpaRepository jpaRepository;
    private final CustodyLedgerMapper mapper;

    public CustodyLedger createLedger(Long transactionId, Long walletId) {
        Optional<Transaction> tx = transactionRepository.findById(TransactionId.of(transactionId));
        Optional<Wallet> wallet = walletRepository.findById(WalletId.of(walletId));

        if (tx.isEmpty()) {
            throw new IllegalArgumentException("Transaction not found with id: " + transactionId);
        }
        if (wallet.isEmpty()) {
            throw new IllegalArgumentException("Wallet not found with id: " + walletId);
        }

        CustodyLedger ledger = CustodyLedger.create(tx.get(), wallet.get());

        ledgerRepository.save(ledger);
        walletRepository.save(wallet.get());
        return ledger;
    }

    // Entity 기반 메서드들 (Controller에서 사용)
    public CustodyLedgerEntity createCustodyLedger(CustodyLedgerEntity custodyLedgerEntity) {
        return jpaRepository.save(custodyLedgerEntity);
    }

    public CustodyLedgerEntity updateCustodyLedger(Long id, CustodyLedgerEntity custodyLedgerDetails) {
        CustodyLedgerEntity custodyLedgerEntity = jpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CustodyLedger not found with id: " + id));

        if (custodyLedgerDetails.getCreatedBy() != null) {
            custodyLedgerEntity.setCreatedBy(custodyLedgerDetails.getCreatedBy());
        }
        if (custodyLedgerDetails.getUpdatedBy() != null) {
            custodyLedgerEntity.setUpdatedBy(custodyLedgerDetails.getUpdatedBy());
        }
        if (custodyLedgerDetails.getAddress() != null) {
            custodyLedgerEntity.setAddress(custodyLedgerDetails.getAddress());
        }
        if (custodyLedgerDetails.getUser() != null) {
            custodyLedgerEntity.setUser(custodyLedgerDetails.getUser());
        }
        if (custodyLedgerDetails.getTransaction() != null) {
            custodyLedgerEntity.setTransaction(custodyLedgerDetails.getTransaction());
        }
        if (custodyLedgerDetails.getWallet() != null) {
            custodyLedgerEntity.setWallet(custodyLedgerDetails.getWallet());
        }

        return jpaRepository.save(custodyLedgerEntity);
    }

    public void deleteCustodyLedger(Long id) {
        if (!jpaRepository.existsById(id)) {
            throw new IllegalArgumentException("CustodyLedger not found with id: " + id);
        }
        jpaRepository.deleteById(id);
    }
}
