package com.happyblock.admindemo.domain.vo;

public record UserId(Long value) {
    public static UserId of(Long value) {
        if (value == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        return new UserId(value);
    }
}
