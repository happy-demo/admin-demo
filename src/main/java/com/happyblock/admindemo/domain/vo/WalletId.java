package com.happyblock.admindemo.domain.vo;

public record WalletId(Long value) {
    public static WalletId of(Long value) {
        if (value == null) {
            throw new IllegalArgumentException("WalletId cannot be null");
        }
        return new WalletId(value);
    }
}
