package com.happyblock.admindemo.domain.vo;

public record TransactionId(Long value) {

    public static TransactionId of(Long value) {
        if (value == null) {
            throw new IllegalArgumentException("TransactionId cannot be null");
        }
        return new TransactionId(value);
    }

    public static TransactionId newId() {
        return new TransactionId(null); // persistence layer에서 생성
    }
}
