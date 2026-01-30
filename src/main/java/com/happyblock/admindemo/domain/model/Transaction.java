package com.happyblock.admindemo.domain.model;

import com.happyblock.admindemo.domain.vo.TransactionId;
import com.happyblock.admindemo.domain.vo.TxHash;
import com.happyblock.admindemo.domain.vo.UserId;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class Transaction {

    private final TransactionId id;
    private final UserId userId;
    private final BigDecimal amount;
    private final ServiceType serviceType;
    private final TransferType transferType;
    private final TxHash txhash;
    private Status status;

    public Transaction(TransactionId id, UserId userId, BigDecimal amount, ServiceType serviceType, TransferType transferType, TxHash txhash) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.serviceType = serviceType;
        this.transferType = transferType;
        this.txhash = txhash;
    }

    public void assertCompleted() {
        if (status != Status.COMPLETED) {
            throw new IllegalStateException("Transaction not completed");
        }
    }

    public void approve() {
        if (status != Status.PENDING_AUTHORIZATION) {
            throw new IllegalStateException("승인 불가");
        }
        this.status = Status.BOWMAN_APPROVE;
    }

    public void approveBy(User approver) {

        if (!status.isApprovable()) {
            throw new IllegalArgumentException(status.toString());
        }

        this.status = Status.BOWMAN_APPROVE;
    }

    public BigDecimal amount() {
        return amount;
    }

    public UserId userId() {
        return userId;
    }

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

        public boolean isApprovable() {
            return this == PENDING_AUTHORIZATION;
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
}
