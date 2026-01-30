package com.happyblock.admindemo.domain.model;

import com.happyblock.admindemo.domain.vo.UserId;
import com.happyblock.admindemo.domain.vo.WalletId;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class Wallet {

    private final WalletId id;
    private final UserId userId;
    private BigDecimal balance;

    public void withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient balance");
        }
        balance = balance.subtract(amount);
    }

    public UserId userId() {
        return userId;
    }
}
