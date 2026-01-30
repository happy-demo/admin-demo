package com.happyblock.admindemo.application.command;

import com.happyblock.admindemo.domain.model.CustodyLedger;
import com.happyblock.admindemo.domain.model.Transaction;
import com.happyblock.admindemo.domain.model.Wallet;
import com.happyblock.admindemo.domain.repository.TransactionRepository;
import com.happyblock.admindemo.domain.repository.WalletRepository;
import com.happyblock.admindemo.domain.vo.TransactionId;
import com.happyblock.admindemo.domain.vo.WalletId;
import com.happyblock.admindemo.infrastructure.persistence.jpa.repository.CustodyLedgerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * application 계층은 entity를 몰라야한다.
 * application은 “도메인을 어떻게 쓰는지”만 알아야 한다
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CustodyLedgerUseCase {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final CustodyLedgerJpaRepository ledgerRepository;

    public CustodyLedger createLedger(Long transactionId, Long walletId) {

        Transaction tx = transactionRepository.find(TransactionId.of(transactionId));
        Wallet wallet = walletRepository.find(WalletId.of(walletId));

        CustodyLedger ledger = CustodyLedger.create(tx, wallet);

        ledgerRepository.save(ledger);
        walletRepository.save(wallet);
        return ledger;
    }
}
