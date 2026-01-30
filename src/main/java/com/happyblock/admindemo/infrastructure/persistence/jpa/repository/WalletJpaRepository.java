package com.happyblock.admindemo.infrastructure.persistence.jpa.repository;

import com.happyblock.admindemo.infrastructure.persistence.jpa.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletJpaRepository extends JpaRepository<WalletEntity, Long> {
    
    List<WalletEntity> findByUserId(Long userId);
}
