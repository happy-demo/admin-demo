package com.happyblock.admindemo.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
@Entity
@Table(name = "transactions")
public class TransactionEntity {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    
    @Column(name = "created_by", nullable = false, length = 255)
    private String createdBy;
    
    @Column(name = "updated_by", nullable = false, length = 255)
    private String updatedBy;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", nullable = false, columnDefinition = "service_type_enum")
    private ServiceType serviceType;
    
    @Column(name = "sequence_id", length = 255)
    private String sequenceId;
    
    @Column(name = "tx_hash", length = 255)
    private String txHash;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "transaction_status_enum")
    private Status status;
    
    @Column(name = "source_address", length = 255)
    private String sourceAddress;
    
    @Column(name = "destination_address", length = 255)
    private String destinationAddress;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "transfer_type", nullable = false, columnDefinition = "transfer_type_enum")
    private TransferType transferType;
    
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
    
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    // ENUM 타입 정의
    public enum ServiceType {
        CUSTODY,
        CONSOLIDATE,
        PROPRIETARY_TRANSFER,
        OTC,
        BROKERAGE,
        SWAP,
        INTERNAL_TRANSFER,
        SERVICE_FEE
    }

    public enum Status {
        BOWMAN_CANCELLED_BY_CUSTOMER,
        BOWMAN_CONFIRMED,
        BOWMAN_APPROVE,
        BOWMAN_REJECTED,
        SIGNER_NOT_FOUND,
        BLOCKED,
        FAILED,
        SUBMITTED,
        INSUFFICIENT_FUNDS,
        INSUFFICIENT_FUNDS_FOR_FEE,
        PENDING_ENRICHMENT,
        PENDING_AUTHORIZATION,
        QUEUED,
        PENDING_SIGNATURE,
        PENDING_BLOCKCHAIN_CONFIRMATIONS,
        BROADCASTING,
        CONFIRMING,
        CONFIRMED,
        COMPLETED,
        CANCELLED,
        CANCELLED_BY_USER,
        AUTO_FREEZE,
        FROZEN_MANUALLY,
        AMOUNT_TOO_SMALL,
        ACTUAL_FEE_TOO_HIGH,
        FAIL_ON_LOW_FEE,
        GAS_LIMIT_TOO_LOW,
        GAS_PRICE_TOO_LOW_FOR_RBF,
        GAS_PRICE_TOO_LOW,
        BENEFICIARY_PENDING_ENRICHMENT,
        BENEFICIARY_CHECK_ADDRESS,
        BENEFICIARY_APPLIED_OFF_CHAIN,
        BENEFICIARY_APPLIED,
        BENEFICIARY_APPLIED_REQUEST,
        BENEFICIARY_CANCELED,
        BENEFICIARY_REJECTED,
        BENEFICIARY_RETURN
        ;

        public boolean canApprove() {
            return this == CONFIRMED;
        }

        public boolean canComplete() {
            return this == BROADCASTING;
        }
    }

    public enum TransferType {
        SELL,
        DEPOSIT,
        WITHDRAWAL,
        SWAP_DEPOSIT,
        SWAP_WITHDRAWAL,
        BROKERAGE_DEPOSIT,
        BROKERAGE_WITHDRAWAL,
        INTERNAL_TRANSFER_DEPOSIT,
        INTERNAL_TRANSFER_WITHDRAWAL,
        PROPRIETARY_TRANSFER_DEPOSIT,
        PROPRIETARY_TRANSFER_WITHDRAWAL,
        SERVICE_FEE
    }

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
