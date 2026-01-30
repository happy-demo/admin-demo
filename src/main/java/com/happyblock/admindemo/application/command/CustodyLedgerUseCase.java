package com.happyblock.admindemo.application.command;

import com.happyblock.admindemo.domain.model.CustodyLedger;
import com.happyblock.admindemo.domain.model.Transaction;
import com.happyblock.admindemo.domain.model.Wallet;
import com.happyblock.admindemo.domain.repository.CustodyLedgerRepository;
import com.happyblock.admindemo.domain.repository.TransactionRepository;
import com.happyblock.admindemo.domain.repository.WalletRepository;
import com.happyblock.admindemo.domain.vo.TransactionId;
import com.happyblock.admindemo.domain.vo.WalletId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    private final CustodyLedgerRepository ledgerRepository;

    public CustodyLedger createLedger(Long transactionId, Long walletId) {

        Optional<Transaction> tx = transactionRepository.findById(TransactionId.of(transactionId));
        Optional<Wallet> wallet = walletRepository.findById(WalletId.of(walletId));

        CustodyLedger ledger = CustodyLedger.create(tx.get(), wallet.get());

        ledgerRepository.save(ledger);
        walletRepository.save(wallet.get());
        return ledger;
    }
}
