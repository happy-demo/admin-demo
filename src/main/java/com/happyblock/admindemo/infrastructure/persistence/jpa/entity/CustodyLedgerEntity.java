package com.happyblock.admindemo.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
@Entity
@Table(name = "custody_ledgers")
public class CustodyLedgerEntity {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private TransactionEntity transaction;
    
    @Column(name = "created_by", nullable = false, length = 255)
    private String createdBy;
    
    @Column(name = "updated_by", nullable = false, length = 255)
    private String updatedBy;
    
    @Column(name = "address", nullable = false, length = 255)
    private String address;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_idx", nullable = false)
    private WalletEntity wallet;
    
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
    
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
    
    // 기본 생성자
    public CustodyLedgerEntity() {
    }
    
    // 생성자
    public CustodyLedgerEntity(UserEntity user, TransactionEntity transaction, 
                               String createdBy, String updatedBy, 
                               String address, WalletEntity wallet) {
        this.user = user;
        this.transaction = transaction;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.address = address;
        this.wallet = wallet;
    }

}
