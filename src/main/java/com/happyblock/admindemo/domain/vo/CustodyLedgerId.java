package com.happyblock.admindemo.domain.vo;

public record CustodyLedgerId(Long value) {
    
    public static CustodyLedgerId newId() {
        return new CustodyLedgerId(null); // persistence layer에서 생성
    }
}
