package com.happyblock.admindemo.domain.repository;

import com.happyblock.admindemo.domain.model.Wallet;
import com.happyblock.admindemo.domain.vo.WalletId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository {
    Optional<Wallet> findById(WalletId id);
    void save(Wallet wallet);
}
