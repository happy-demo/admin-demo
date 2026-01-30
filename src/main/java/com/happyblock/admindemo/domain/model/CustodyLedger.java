package com.happyblock.admindemo.domain.model;

import com.happyblock.admindemo.domain.vo.CustodyLedgerId;
import com.happyblock.admindemo.domain.vo.TransactionId;
import com.happyblock.admindemo.domain.vo.UserId;
import com.happyblock.admindemo.domain.vo.WalletId;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CustodyLedger {

    private final CustodyLedgerId id;
    private final UserId userId;
    private final TransactionId transactionId;
    private final WalletId walletId;
    private final BigDecimal amount;

    private CustodyLedger(
            CustodyLedgerId id,
            UserId userId,
            TransactionId transactionId,
            WalletId walletId,
            BigDecimal amount
    ) {
        this.id = id;
        this.userId = userId;
        this.transactionId = transactionId;
        this.walletId = walletId;
        this.amount = amount;
    }

    private CustodyLedger(
            UserId userId,
            Transaction transaction,
            Wallet wallet
    ) {
        transaction.assertCompleted();

        if (!wallet.userId().equals(userId)) {
            throw new IllegalStateException("Wallet owner mismatch");
        }

        wallet.withdraw(transaction.amount());

        this.id = CustodyLedgerId.newId();
        this.userId = userId;
        this.transactionId = transaction.id();
        this.walletId = wallet.id();
        this.amount = transaction.amount();
    }

    public static CustodyLedger create(
            Transaction transaction,
            Wallet wallet
    ) {
        return new CustodyLedger(transaction.userId(), transaction, wallet);
    }
}
