package com.happyblock.admindemo.application.query;

import com.happyblock.admindemo.domain.model.CustodyLedger;
import com.happyblock.admindemo.domain.vo.CustodyLedgerId;
import com.happyblock.admindemo.domain.vo.TransactionId;
import com.happyblock.admindemo.infrastructure.persistence.jpa.entity.CustodyLedgerEntity;
import com.happyblock.admindemo.infrastructure.persistence.jpa.mapper.CustodyLedgerMapper;
import com.happyblock.admindemo.infrastructure.persistence.jpa.repository.CustodyLedgerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustodyLedgerQueryService {

    private final CustodyLedgerJpaRepository jpaRepository;
    private final CustodyLedgerMapper mapper;

    public List<CustodyLedgerEntity> getAllCustodyLedgers() {
        return jpaRepository.findAll();
    }

    public Optional<CustodyLedgerEntity> getCustodyLedgerById(Long id) {
        return jpaRepository.findById(id);
    }

    public List<CustodyLedgerEntity> getCustodyLedgersByUserId(Long userId) {
        // UserEntity를 통해 조회
        return jpaRepository.findAll()
                .stream()
                .filter(ledger -> ledger.getUser() != null && ledger.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<CustodyLedgerEntity> getCustodyLedgersByTransactionId(Long transactionId) {
        return jpaRepository.findByTransactionId(transactionId);
    }

    public List<CustodyLedgerEntity> getCustodyLedgersByWalletId(Long walletId) {
        return jpaRepository.findByWalletId(walletId);
    }

    // 도메인 모델 조회 메서드들
    public CustodyLedger findDomainById(CustodyLedgerId id) {
        return jpaRepository.findById(id.value())
                .map(mapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("CustodyLedger not found with id: " + id.value()));
    }

    public List<CustodyLedger> findAllDomain() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    public boolean existsByTransactionId(TransactionId transactionId) {
        return jpaRepository.existsByTransactionId(transactionId.value());
    }
}
